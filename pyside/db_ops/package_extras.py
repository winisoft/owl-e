from typing import List, Dict, Any
from models.owle.specifiers import PackageExtras


def insert_package_extras(cursor, package_extras: List[PackageExtras]):
    """
    Insert multiple package extras records into the database.
    Args:
        cursor: Database cursor for executing SQL commands.
        package_extras: List of PackageExtras objects to insert.    
    """

    if not package_extras:
        return

    values = [
        (pe.distribution, pe.version_id, pe.extra_name, pe.extra_description)
        for pe in package_extras
    ]

    cursor.executemany(
        "INSERT INTO `owle_db`.`package_extras` (distribution, versionId, extraName, extraDescription) VALUES (%s, %s, %s, %s)",
        values
    )
