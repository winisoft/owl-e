from packaging.version import Version, InvalidVersion

from db import sql_escape
from helpers.pypi_fetcher import fetch_pypi_metadata
from helpers.version_overflows import determine_version_overflows, has_overflow
from ..config import MAX_UINT64, OVERFLOW_COMPONENTS, _OVERFLOW_REGEX
from typing import Any, List, Optional, Dict, Tuple, Union
import os
import csv


def write_per_file_outputs(name, output_dir, versions, package_versions, overflows, failures):
    prefix = os.path.splitext(name)[0]

    def write_lines(lines, suffix):
        path = os.path.join(output_dir, f"{prefix}_{suffix}.sql")
        with open(path, 'w', encoding='utf-8') as f:
            f.write('\n'.join(lines) + '\n')

    write_lines(versions, "versions")
    write_lines(package_versions, "package_versions")
    write_lines(overflows, "overflow")

    if failures:
        fail_path = os.path.join(output_dir, f"{prefix}_failed.csv")
        with open(fail_path, 'w', encoding='utf-8', newline='') as f:
            writer = csv.writer(f)
            writer.writerow(["distribution", "version_string"])
            writer.writerows(failures)


def parse_version(version_str: str) -> Optional[dict]:
    try:
        v = Version(version_str)
        return {
            "major": v.major,
            "minor": v.minor,
            "micro": v.micro,
            "phase": v.pre[0] if v.pre else None,
            "phase_number": v.pre[1] if v.pre else None,
            "is_post_release": v.post is not None,
            "post_number": v.post,
            "is_dev_release": v.dev is not None,
            "dev_number": v.dev,
        }
    except InvalidVersion:
        return None


def parse_version_fields(version_str):
    """
    Parse a version string using packaging.version.Version.
    Return a dict of components, or None if invalid.
    Components:
      - major, minor, micro (ints or None)
      - phase (string: 'alpha','beta','candidate','final')
      - phase_number (int or None)
      - is_post_release (bool), post_number (int or None)
      - is_dev_release (bool), dev_number (int or None)
      - version_string: normalized str(Version())
    """
    try:
        v = Version(version_str)
    except InvalidVersion:
        return None

    # Extract release tuple (pad to length 3 with zeros/None)
    release = list(v.release)
    while len(release) < 3:
        release.append(0)
    major, minor, micro = release[:3]

    phase = (
        "alpha" if v.pre and v.pre[0] == "a"
        else "beta" if v.pre and v.pre[0] == "b"
        else "candidate" if v.pre and v.pre[0] in ("rc","c")
        else "final"
    )
    phase_number = v.pre[1] if v.pre else None

    is_post = v.post is not None
    post_number = v.post

    is_dev = v.dev is not None
    dev_number = v.dev

    return {
        "major": major,
        "minor": minor,
        "micro": micro,
        "phase": phase,
        "phase_number": phase_number,
        "is_post_release": is_post,
        "post_number": post_number,
        "is_dev_release": is_dev,
        "dev_number": dev_number,
        "version_string": str(v),  # normalized
    }


def get_or_create_version(cursor, fields):
    """
    Given a parsed `fields` dict (see parse_version_fields), do the following:
      1. Check if a row with the same version_string exists in `versions`. If yes, return its id.
      2. Otherwise, insert a new row into `versions`:
         - For each numeric component (major, minor, micro, phase_number,
           post_number, dev_number):
             • If it overflows (via is_overflow_numeric), record that component for overflow and store 0 in the table.
             • Else, store the actual int.
         - Set `has_overflow = TRUE` if any overflow occurred, else FALSE.
      3. For each overflowed component, insert a row in `overflow_versions(version_id, component_type, string_value)`.
      4. Return the new version_id.
    """
    overflowed = []
    normal_values = {}

    # 1. Determine which components overflow
    for key in OVERFLOW_COMPONENTS:
        val = fields.get(key)
        if val is None:
            normal_values[key] = None
            continue
        if is_overflow_numeric(val):
            # Record the raw string and clip to None (we'll store 0 or whatever in versions)
            overflowed.append((key, str(val)))
            normal_values[key] = 0
        else:
            # Safe to convert to int
            normal_values[key] = safe_int(val)

    # 2. Check if version_string already exists (relying on UNIQUE(version_string))
    version_string = fields["version_string"]
    cursor.execute(
        "SELECT id FROM versions WHERE version_string = %s LIMIT 1", 
        (version_string,)
    )
    row = cursor.fetchone()
    if row:
        return row[0]

    # 3. Insert new version row
    insert_sql = """
      INSERT INTO versions 
        (major, minor, micro, phase, phase_number,
         is_post_release, post_number,
         is_dev_release, dev_number,
         version_string, has_overflow)
      VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
    """
    cursor.execute(insert_sql, (
        normal_values["major"],
        normal_values["minor"],
        normal_values["micro"],
        fields["phase"],
        normal_values["phase_number"],
        int(fields["is_post_release"]),
        normal_values["post_number"],
        int(fields["is_dev_release"]),
        normal_values["dev_number"],
        version_string,
        int(bool(overflowed)),
    ))
    version_id = cursor.lastrowid

    # 4. Insert overflow components
    for component_type, raw_val in overflowed:
        cursor.execute(
            "INSERT INTO overflow_versions (version_id, component_type, string_value) VALUES (%s,%s,%s)",
            (version_id, component_type, raw_val)
        )

    return version_id


def process_version_entry(distribution: str, version_str: str, version_id: int) -> Tuple[Optional[Dict[str, Any]], Optional[List[str]]]:
    """
    Handles all logic associated with a single (distribution, version) pair:
      - Normalizes the version string,
      - Extracts major/minor/micro/phase info,
      - Detects overflow fields,
      - Optionally fetches metadata from PyPI,
      - Builds SQL inserts for versions, package_versions, and overflow_versions.

    Args:
        distribution: Name of the distribution (e.g. 'requests').
        version_str: Raw version string from CSV.
        version_id: Unique database ID to assign.

    Returns:
        A dictionary with keys: version_sql, package_sql, overflow_sqls.
        Or a failure entry for bad input.
    """
    parsed = parse_version(version_str)
    if parsed is None:
        return None, [distribution, version_str]

    has_over = has_overflow(parsed)
    version_values = (
        version_id,
        sql_escape(parsed['major']),
        sql_escape(parsed['minor']),
        sql_escape(parsed['micro']),
        sql_escape(parsed['phase']),
        sql_escape(parsed['phase_number']),
        sql_escape(parsed['is_post_release']),
        sql_escape(parsed['post_number']),
        sql_escape(parsed['is_dev_release']),
        sql_escape(parsed['dev_number']),
        sql_escape(version_str),
        'TRUE' if has_over else 'FALSE',
    )

    version_sql = (
        "INSERT INTO versions "
        "(id, major, minor, micro, phase, phase_number, is_post_release, "
        "post_number, is_dev_release, dev_number, version_string, has_overflow) "
        f"VALUES ({', '.join(map(str, version_values))});"
    )

    # Metadata fetch
    metadata = fetch_pypi_metadata(distribution, version_str)
    required_python = sql_escape(metadata.get("requires_python") if metadata else None)

    package_sql = (
        f"INSERT INTO package_versions (id, distribution, required_python) "
        f"VALUES ({version_id}, {sql_escape(distribution)}, {required_python});"
    )

    overflow_sqls = determine_version_overflows(version_id, parsed) if has_over else []

    return {
        "version_sql": version_sql,
        "package_sql": package_sql,
        "overflow_sqls": overflow_sqls,
    }, None
