from db_connection import server_bound


from typing import Optional


def insert_callable_signatures(
    cursor,
    type_node_id: int,
    return_type_node_id: int,
    docstring: str,
    is_static: bool = False,
    is_classmethod: bool = False
) -> int:
    # bound server-side: `docstring` is arbitrary prose and mangles the statement otherwise
    with server_bound(cursor) as bound:
        bound.execute(
            """
            INSERT INTO callable_signatures
                (type_node_id, return_type_node_id, docstring, is_static, is_classmethod)
            VALUES (%s, %s, %s, %s, %s)
            """,
            (type_node_id, return_type_node_id, docstring, is_static, is_classmethod)
        )
        # `lastrowid` is not populated on a prepared cursor by every connector release - 9.0.0,
        # which is the newest available for 3.8, returns nothing usable and the id then fails the
        # foreign key on `callable_parameters`. `LAST_INSERT_ID()` is connection-scoped and the
        # prepared cursor shares this connection, so it answers for the insert just made.
        new_id = bound.lastrowid
        if not new_id:
            bound.execute("SELECT LAST_INSERT_ID()")
            row = bound.fetchone()
            new_id = row[0] if row else None
        return new_id


def insert_callable_parameter(cursor, signature_id: int, position: int,
                              param_name: str, param_type_node_id: int,
                              is_vararg: bool = False, is_kwarg: bool = False, default_value_text: Optional[str]=None):
    cursor.execute(
        """
        INSERT INTO callable_parameters
            (signature_id, position, param_name, param_type_node_id, is_vararg, is_kwarg, default_value_text)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (
            signature_id,
            position,
            param_name,
            param_type_node_id,
            is_vararg,
            is_kwarg,
            default_value_text
        )
    )


def insert_callable_owners(cursor, owner_id: int, callable_node_id: int):
    cursor.execute(
        "INSERT IGNORE INTO callable_owners (owner_node_id, callable_node_id) VALUES (%s, %s)",
        (owner_id, callable_node_id)
    )


def insert_callable_ownership(cursor, owner_node_id: int, callable_node_id: int):
    cursor.execute(
        """
        INSERT IGNORE INTO callable_owners (owner_node_id, callable_node_id)
        VALUES (%s, %s)
        """,
        (owner_node_id, callable_node_id)
    )


def select_callable_type_node(
    cursor,
    version_id: int,
    namespace: str,
    name: str
) -> Optional[int]:
    """Returns the ID of the type_nodes entry for (namespace, name, version_id) if it exists, else None."""
    select_sql = """
        SELECT id FROM type_nodes 
         WHERE version_id = %s AND namespace = %s AND name = %s AND kind = 'callable'
    """
    cursor.execute(select_sql, (version_id, namespace, name) )
    row = cursor.fetchone()
    return row[0] if row else None