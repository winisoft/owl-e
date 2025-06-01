from typing import List
from models.owle.packages import PackageTag


def insert_package_tags(cursor, package_tags: List[PackageTag]) -> None:
    """
    Insert multiple package tag records into the `package_tags` table.
    """
    insert_sql = (
        "INSERT INTO package_tags (distribution, versionId, tagId) "
        "VALUES (%s, %s, %s)"
    )
    data = [(pt.distribution, pt.version_id, pt.tag_id) for pt in package_tags]
    cursor.executemany(insert_sql, data)


def insert_package_tag(cursor, package_tag: PackageTag) -> None:
    """
    Insert a new package tag record into the `package_tags` table.
    """
    insert_sql = (
        "INSERT INTO package_tags (distribution, versionId, tagId) "
        "VALUES (%s, %s, %s)"
    )
    cursor.execute(insert_sql, (
        package_tag.distribution,
        package_tag.version_id,
        package_tag.tag_id
    ))

def get_package_tag_exists(cursor, package_tag: PackageTag) -> bool:
    """
    Check if a package tag record exists in the `package_tags` table.
    Returns True if it exists, False otherwise.
    """
    cursor.execute(
        """
        SELECT 1 FROM package_tags
        WHERE distribution = %s AND versionId = %s AND tagId = %s
        LIMIT 1
        """,
        (package_tag.distribution, package_tag.version_id, package_tag.tag_id)
    )

    return cursor.fetchone() is not None


def associate_tag_with_package(cursor, package_tag: PackageTag) -> None:
    """
    Ensure that a package_tags record associates distribution and version_id with a given tag_id.
    If not, insert it.
    """
    if not get_package_tag_exists(cursor, package_tag):

        insert_package_tag(cursor, package_tag)