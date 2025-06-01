from ..models.owle.versions import OverflowVersion

def get_overflow_version_exists(cursor, overflow_version: OverflowVersion) -> bool:
    """
    Check if an overflow version record exists in the `overflow_versions` table.
    Returns True if it exists, False otherwise.
    """
    cursor.execute(
        """
        SELECT 1 FROM overflow_versions
        WHERE version_id = %s AND component_type = %s AND string_value = %s
        LIMIT 1
        """,
        (overflow_version.version_id, overflow_version.component_type, overflow_version.string_value)
    )
    
    return cursor.fetchone() is not None


def insert_overflow_version(cursor, overflow_version: OverflowVersion):
    """
    Insert a new overflow version record into the `overflow_versions` table.
    """
    insert_sql = (
        "INSERT INTO overflow_versions (version_id, component_type, string_value) "
        "VALUES (%s, %s, %s)"
    )
    cursor.execute(insert_sql, (
        overflow_version.version_id,
        overflow_version.component_type,
        overflow_version.string_value
    ))
    
    return cursor.lastrowid

