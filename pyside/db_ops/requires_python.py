from models.RequiresPythonSpecifier import RequiresPythonSpecifier
import logging

logger = logging.getLogger(__name__)


def insert_one_requires_python_specifier(
        cursor,
        distribution: str,
        version_id: int,
        operator: str,
        python_version_id: int,
        wildcard_segment: str
) -> bool:
    """
    Insert a single requires_python_specifier record into the database.
    
    Returns:
        True if the insert was successful, False otherwise.
    """
    try:
        cursor.execute(
            """INSERT INTO required_python_specifiers (distribution, versionId, operator, pythonVersionId, wildcardSegment) VALUES (%s, %s, %s, %s, %s)""",
            (distribution, version_id, operator, python_version_id, wildcard_segment)
        )
        return True
    except Exception as e:
        logger.error(f"Failed to insert requires_python_specifier: {e}")
        return False


def insert_requires_python_specifiers(cursor, requires_python_specifiers: list[RequiresPythonSpecifier]) -> None:
    if not requires_python_specifiers:
        return
    #`package_id` bigint unsigned NOT NULL,

    sql = """ 
    INSERT INTO package_python_requirements (distribution, package_id, version_id, operator, wildcard)
    VALUES (%s, %s, %s, %s, %s)
    """
    values = [
        (
            spec.distribution,
            spec.package_id,
            spec.py_version_id,
            spec.operator,
            spec.wildcard
        )
        for spec in requires_python_specifiers
    ]

    cursor.executemany(sql, values)


