from typing import Optional





def get_or_create_tag(cursor, name: str, source: str, user_id: Optional[int]) -> int:
    """
    Get the ID of a tag by name, source, and user_id.
    If it doesn't exist, create it and return the new ID.
    """
    cursor.execute(
        """
        SELECT id FROM tags
        WHERE name = %s AND source = %s AND user_id IS %s
        LIMIT 1
        """,
        (name, source, user_id)
    )
    row = cursor.fetchone()
    if row:
        return row[0]

    cursor.execute(
        """
        INSERT INTO tags (name, source, user_id)
        VALUES (%s, %s, %s)
        """,
        (name, source, user_id)
    )
    return cursor.lastrowid