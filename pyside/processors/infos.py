"""
Phase B: turning banked PyPI metadata into the structures the application queries.

Phase A (`processors/api_data.py`) spent months hitting pypi.org and banking each raw response in
`packages.info`. That schema is now an archive and is read from, never written to. This reads those
payloads and builds, in the working database, everything they describe:

  1. the version the package was published under, decomposed into PEP 440 components
  2. the package row itself, keyed on (version, distribution)
  3. the dependency requirements it declares, with their environment markers

Each package is processed on its own. Versions dedupe naturally - `ensure_package_version` returns
an existing id when the components have been seen - but every package still gets its own row and
its own metadata parsed, because the whole point of holding 454,669 payloads is what each one
individually says.

Driven by `info_derivation_worklist` in the archive, which carries a state per row, so a run that
stops resumes where it left off and a failure is recorded next to the package that caused it.
"""

import logging
import os
from typing import List, Optional, Tuple

from db_ops.packages import insert_extras
from db_ops.requires_dist import insert_requires_dist
from db_ops.requires_python import insert_requires_python_specifiers
from db_ops.versions import ensure_package_version
from helpers.specifiers import process_requires_python
from helpers.requires_dist import RequiresDistInsertItem, process_requires_dist_specifiers
from utils.db_connection import get_db_connection, server_bound
from utils.packages import build_package_info_model

logger = logging.getLogger(__name__)

ARCHIVE = os.getenv("INFO_SOURCE_SCHEMA", "owle_test_archive")
BATCH_SIZE = 500


def _claim_batch(cursor, limit: int) -> List[Tuple[int, str, str]]:
    cursor.execute(
        f"""
        SELECT version_id, distribution, version_str
        FROM {ARCHIVE}.info_derivation_worklist
        WHERE state = 'pending'
        ORDER BY version_id
        LIMIT %s
        """,
        (limit,),
    )
    return cursor.fetchall()


def _mark(cursor, surrogate_id: int, distribution: str, state: str, note: Optional[str] = None) -> None:
    cursor.execute(
        f"""
        UPDATE {ARCHIVE}.info_derivation_worklist
        SET state = %s, note = %s
        WHERE version_id = %s AND distribution = %s
        """,
        (state, (note or "")[:2000] or None, surrogate_id, distribution),
    )


def ensure_package(cursor, version_id: int, distribution: str, version_str: str, info) -> None:
    """
    Create the package row in the working schema if it is not already there.

    A package *is* a distribution at a version, so `version_id` is the resolved `versions.id` - not
    the archive's surrogate. The descriptive columns come straight off the payload.

    Bound server-side rather than interpolated: `description` is arbitrary prose, and a client-side
    statement carrying it can be mangled before it is sent. See [server_bound].
    """
    sql = """
        INSERT IGNORE INTO packages
            (version_id, distribution, version_str, summary, keywords, description,
             description_content_type, license, home_page, project_url, download_url,
             docs_url, yanked)
        VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
    """
    params = (
        version_id,
        distribution,
        version_str,
        getattr(info, "summary", None),
        getattr(info, "keywords", None),
        getattr(info, "description", None),
        getattr(info, "descriptionContentType", None),
        getattr(info, "license", None),
        getattr(info, "homePage", None),
        getattr(info, "projectUrl", None),
        getattr(info, "downloadUrl", None),
        getattr(info, "docsUrl", None),
        getattr(info, "yanked", None),
    )
    with server_bound(cursor) as bound:
        bound.execute(sql, params)

def process_package_info(cursor, distribution: str, version_str: str) -> Optional[int]:
    """
    Build everything one package's payload describes. Returns the number of requirement rows
    written, or None when there was no usable payload.
    """
    package_info_model = build_package_info_model(cursor, distribution, version_str)
    if package_info_model is None:
        return None

    (_surrogate_id, package_info) = package_info_model
    if package_info is None:
        return None

    # the version this package was published under, as a real row we can key on
    version_id = ensure_package_version(cursor, version_str)
    if version_id is None:
        raise ValueError(f"could not decompose version string {version_str!r}")

    ensure_package(cursor, version_id, distribution, version_str, package_info)

    written = 0

    # Requires-Python: which interpreters this release will run on. Each clause of a set such as
    # `>=3.9,<3.13` becomes its own row, and the compound `ppr_fk_spec` constrains the
    # (version, operator, wildcard) triple to a combination `python_version_specifiers` whitelists.
    py_specs = process_requires_python(
        cursor, distribution, version_id, package_info.requiresPython
    )
    if py_specs:
        insert_requires_python_specifiers(cursor, py_specs)
        written += len(py_specs)

    # the optional feature sets this release offers - the `[socks]` in `requests[socks]`
    if package_info.providesExtra:
        insert_extras(cursor, distribution, version_id, package_info.providesExtra)
        written += len(package_info.providesExtra)

    inserts_item: Optional[List[RequiresDistInsertItem]] = process_requires_dist_specifiers(
        cursor, distribution, version_id, package_info.requiresDist
    )
    if inserts_item:
        insert_requires_dist(cursor, inserts_item)
        written += len(inserts_item)

    return written


def process_info_data(limit: Optional[int] = None) -> None:
    processed = succeeded = failed = skipped = 0
    written = 0

    with get_db_connection() as conn:
        while True:
            if limit is not None and processed >= limit:
                break

            take = BATCH_SIZE if limit is None else min(BATCH_SIZE, limit - processed)
            cursor = conn.cursor()
            batch = _claim_batch(cursor, take)

            if not batch:
                cursor.close()
                break

            for surrogate_id, distribution, version_str in batch:
                processed += 1
                # A package is all-or-nothing. Without this, a package that wrote its
                # Requires-Python rows and then failed on its dependencies left those rows behind,
                # and the retry hit a duplicate key on work the first attempt had already done -
                # a failure that looks like a schema problem and is really a half-finished write.
                cursor.execute("SAVEPOINT pkg")
                try:
                    outcome = process_package_info(cursor, distribution, version_str)
                    if outcome is None:
                        skipped += 1
                        _mark(cursor, surrogate_id, distribution, "skipped", "no usable info")
                    else:
                        succeeded += 1
                        written += outcome
                        _mark(cursor, surrogate_id, distribution, "done", f"{outcome} requirement(s)")
                except Exception as e:
                    # PyPI metadata is user-supplied and 454,669 payloads of it contain malformed
                    # requirement strings and worse. One bad package does not stop the run.
                    cursor.execute("ROLLBACK TO SAVEPOINT pkg")
                    failed += 1
                    reason = f"{type(e).__name__}: {e}"
                    logger.warning("%s==%s failed: %s", distribution, version_str, reason)
                    _mark(cursor, surrogate_id, distribution, "failed", reason)

            conn.commit()
            cursor.close()
            logger.info(
                "processed=%d done=%d skipped=%d failed=%d requirements=%d",
                processed, succeeded, skipped, failed, written,
            )

    print(
        f"finished: processed={processed} done={succeeded} skipped={skipped} "
        f"failed={failed} requirements_written={written}"
    )


# ---------------------------------------------------------------------------------------------
# Requires-Python and extras backfill
# ---------------------------------------------------------------------------------------------


def process_python_and_extras(cursor, distribution: str, version_str: str) -> Optional[int]:
    """
    Requires-Python and extras for one package, without touching its dependency rows.

    The dependency pass wrote `package_dist_requirements` for every package before these two steps
    existed, and that table carries no unique key beyond its surrogate primary key - so re-running
    the whole derivation would duplicate all 5.2 million of those rows rather than skip them. This
    repeats only the two steps that were missing. Both of its inserts ignore duplicates, which
    makes the backfill safe to re-run and safe to interrupt.
    """
    model = build_package_info_model(cursor, distribution, version_str)
    if model is None:
        return None
    (_surrogate_id, package_info) = model
    if package_info is None:
        return None

    version_id = ensure_package_version(cursor, version_str)
    if version_id is None:
        raise ValueError(f"could not decompose version string {version_str!r}")

    written = 0

    py_specs = process_requires_python(
        cursor, distribution, version_id, package_info.requiresPython
    )
    if py_specs:
        insert_requires_python_specifiers(cursor, py_specs)
        written += len(py_specs)

    if package_info.providesExtra:
        insert_extras(cursor, distribution, version_id, package_info.providesExtra)
        written += len(package_info.providesExtra)

    return written


def _claim_py_batch(cursor, limit: int) -> List[Tuple[int, str, str]]:
    """
    Only packages the dependency pass finished: a skipped one had no usable payload to read, and
    a package with no `packages` row has nothing for these rows to key on.
    """
    cursor.execute(
        f"""
        SELECT version_id, distribution, version_str
        FROM {ARCHIVE}.info_derivation_worklist
        WHERE py_state = 'pending' AND state = 'done'
        ORDER BY version_id
        LIMIT %s
        """,
        (limit,),
    )
    return cursor.fetchall()


def _mark_py(cursor, surrogate_id: int, distribution: str, state: str, note: Optional[str] = None) -> None:
    cursor.execute(
        f"""
        UPDATE {ARCHIVE}.info_derivation_worklist
        SET py_state = %s, py_note = %s
        WHERE version_id = %s AND distribution = %s
        """,
        (state, (note or "")[:2000] or None, surrogate_id, distribution),
    )


def backfill_python_and_extras(limit: Optional[int] = None) -> None:
    processed = succeeded = failed = skipped = 0
    written = 0

    with get_db_connection() as conn:
        while True:
            if limit is not None and processed >= limit:
                break

            take = BATCH_SIZE if limit is None else min(BATCH_SIZE, limit - processed)
            cursor = conn.cursor()
            batch = _claim_py_batch(cursor, take)

            if not batch:
                cursor.close()
                break

            for surrogate_id, distribution, version_str in batch:
                processed += 1
                cursor.execute("SAVEPOINT pkg")
                try:
                    outcome = process_python_and_extras(cursor, distribution, version_str)
                    if outcome is None:
                        skipped += 1
                        _mark_py(cursor, surrogate_id, distribution, "skipped", "no usable info")
                    else:
                        succeeded += 1
                        written += outcome
                        _mark_py(cursor, surrogate_id, distribution, "done", f"{outcome} row(s)")
                except Exception as e:
                    cursor.execute("ROLLBACK TO SAVEPOINT pkg")
                    failed += 1
                    reason = f"{type(e).__name__}: {e}"
                    logger.warning("%s==%s failed: %s", distribution, version_str, reason)
                    _mark_py(cursor, surrogate_id, distribution, "failed", reason)

            conn.commit()
            cursor.close()
            logger.info(
                "processed=%d done=%d skipped=%d failed=%d rows=%d",
                processed, succeeded, skipped, failed, written,
            )

    print(
        f"finished: processed={processed} done={succeeded} skipped={skipped} "
        f"failed={failed} rows_written={written}"
    )
