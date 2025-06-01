from models.owle.versions import PythonVersion
from typing import Optional, List, Tuple

def get_supported_python_versions(cursor) -> Optional[List[Tuple[int, int, int, int]]]:
    """Get list of supported Python versions as (id, major, minor, micro) tuples."""
    cursor.execute(
        "SELECT v.id, v.major, v.minor, v.micro FROM versions v "
        "JOIN python_versions pv ON v.id = pv.id "
        "WHERE pv.is_supported is True"
    )
    rows = cursor.fetchall()
    return rows

def insert_python_version(cursor, python_version: PythonVersion) -> int:
    """
    Insert a new Python version record into the `python_versions` table.
    """
    insert_sql = (
        "INSERT INTO python_versions (id, is_supported) "
        "VALUES (%s, %s)"
    )
    cursor.execute(insert_sql, (
        python_version.version_id,
        python_version.python_version_string
    ))
    return cursor.lastrowid


def get_python_version_id(cursor, version_str: str) -> int:
    """
    Get the id
    """
    cursor.execute(
        "SELECT v.id FROM versions v"
        "JOIN python_versions pv ON v.id = pv.id"
        "WHERE v.version_string = %s",
        (version_str)
    )
    row = cursor.fetchone()
    if row:
        return (row[0])

    return None