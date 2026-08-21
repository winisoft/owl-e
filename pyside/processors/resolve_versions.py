"""
Stage 1: resolving each package's version.

A package is identified by its distribution *and its version* - that pairing is what a package is.
`packages.version_id` is the foreign key carrying it, and for all 6,651,103 rows in the archive it
holds nothing meaningful: inserts never supplied a value, so AUTO_INCREMENT filled the column with
a running counter. Zero packages currently resolve to a `versions` row.

That is why derivation stalled at 3,761 Requires-Python rows and no dependency requirements at all.
Every downstream query joins `packages` to `versions`, and that join has never had anything to
match on.

This stage does the missing work: takes the version string a package was published under, decomposes
it into the PEP 440 components `versions` stores, finds or creates that row, and records the link.
`ensure_package_version` already knew how - it was simply never called for packages.

Writes go to `version_link`, a side table, rather than updating `packages.version_id` in place:
that column is half of the primary key, so rewriting it row by row would be an expensive key
churn on a 6.6M-row table, and doing it non-destructively means the resolution can be inspected
and re-run before anything depends on it.
"""

import logging
from typing import List, Optional, Tuple

from db_ops.versions import ensure_package_version
from utils.db_connection import get_db_connection

logger = logging.getLogger(__name__)

BATCH_SIZE = 500


DDL = """
CREATE TABLE IF NOT EXISTS version_link (
  surrogate_id   BIGINT UNSIGNED NOT NULL,
  distribution   VARCHAR(255)    NOT NULL,
  version_str    VARCHAR(255)    NOT NULL,
  resolved_version_id BIGINT UNSIGNED NULL,
  state          ENUM('pending','done','failed') NOT NULL DEFAULT 'pending',
  note           TEXT NULL,
  PRIMARY KEY (surrogate_id, distribution),
  KEY idx_state (state),
  KEY idx_resolved (resolved_version_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
"""


def prepare(cursor) -> None:
    """Create the link table and seed it from the derivation worklist."""
    cursor.execute(DDL)
    cursor.execute(
        """
        INSERT IGNORE INTO version_link (surrogate_id, distribution, version_str)
        SELECT version_id, distribution, version_str FROM info_derivation_worklist
        """
    )


def _claim_batch(cursor, limit: int) -> List[Tuple[int, str, str]]:
    cursor.execute(
        """
        SELECT surrogate_id, distribution, version_str
        FROM version_link
        WHERE state = 'pending'
        ORDER BY surrogate_id
        LIMIT %s
        """,
        (limit,),
    )
    return cursor.fetchall()


def resolve_versions(limit: Optional[int] = None) -> None:
    """
    Give every package with banked metadata a real `versions` row to point at.

    Idempotent by construction: `ensure_package_version` returns the existing id when the version
    has already been seen, so the 454,669 packages collapse onto far fewer distinct versions and a
    re-run neither duplicates rows nor redoes settled work.
    """
    processed = resolved = failed = 0

    with get_db_connection() as conn:
        cursor = conn.cursor()
        prepare(cursor)
        conn.commit()
        cursor.close()

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
                try:
                    version_id = ensure_package_version(cursor, version_str)
                    if version_id is None:
                        failed += 1
                        cursor.execute(
                            "UPDATE version_link SET state='failed', note=%s "
                            "WHERE surrogate_id=%s AND distribution=%s",
                            ("version string could not be decomposed", surrogate_id, distribution),
                        )
                    else:
                        resolved += 1
                        cursor.execute(
                            "UPDATE version_link SET state='done', resolved_version_id=%s "
                            "WHERE surrogate_id=%s AND distribution=%s",
                            (version_id, surrogate_id, distribution),
                        )
                except Exception as e:
                    # PyPI accepts version strings that PEP 440 does not; a run over 454,669 of
                    # them will meet several. The row is recorded and the batch continues.
                    failed += 1
                    reason = f"{type(e).__name__}: {e}"
                    logger.warning("%s==%s: %s", distribution, version_str, reason)
                    cursor.execute(
                        "UPDATE version_link SET state='failed', note=%s "
                        "WHERE surrogate_id=%s AND distribution=%s",
                        (reason[:2000], surrogate_id, distribution),
                    )

            conn.commit()
            cursor.close()
            logger.info("processed=%d resolved=%d failed=%d", processed, resolved, failed)

    print(f"finished: processed={processed} resolved={resolved} failed={failed}")
