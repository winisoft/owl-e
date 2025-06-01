from typing import Optional
from models.owle.tags import TagRelations


def insert_tag_relation(cursor, parent_id: int, child_id: int) -> None:
    """
    Ensure a tag_relations record associates a parent tag with a child tag.
    If not, insert that relation.
    """
    cursor.execute(
        """
        SELECT 1 FROM tag_relations
        WHERE parentTagId = %s AND childTagId = %s
        LIMIT 1
        """,
        (parent_id, child_id)
    )
    if cursor.fetchone():
        return

    cursor.execute(
        """
        INSERT INTO tag_relations (parentTagId, childTagId)
        VALUES (%s, %s)
        """,
        (parent_id, child_id)
    )