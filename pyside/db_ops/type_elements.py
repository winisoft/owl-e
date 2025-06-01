from typing import Dict, Literal, Optional, Tuple


def insert_callable_signatures(cursor, name: str, docstring: str) -> int:
    cursor.execute(
        """
        INSERT INTO callable_signatures
            (parent_schema_ref_id, name, return_type_node_id, docstring, is_static, is_classmethod)
        VALUES (%s, %s, %s, %s, %s, %s)
        """,
        (None, name, None, docstring, False, False)
    )

    return cursor.lastrowid


def insert_callable_parameter(cursor, signature_id: int, position: int, param_name: str, param_type_node_id: int, is_vararg: bool, is_kwarg: bool):
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
            is_kwarg
        )
    )


def insert_class_generic_params(cursor, node_id: int, typevar_node_id: int, idx: int):
    cursor.execute(
        """
        INSERT IGNORE INTO type_generic_params (class_node_id, typevar_node_id, position)
        VALUES (%s, %s, %s)
        """,
        (node_id, typevar_node_id, idx)
    )


def insert_type_schema_ref(cursor, class_node_id: int, name: str, member_kind: Literal['method', 'attribute'],
                           type_node_id: int, position: int):
    cursor.execute(
        """
        INSERT IGNORE INTO type_schema_refs
            (parent_ref_id, class_node_id, name, member_kind, type_node_id, description, position)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (None, class_node_id, name, member_kind, type_node_id, None, position)
    )


def insert_typevar_bounds(cursor, node_id: int, bound_node_id: int):
    cursor.execute(
        "INSERT INTO typevar_bounds (typevar_node_id, bound_node_id) VALUES (%s, %s)",
        (node_id, bound_node_id)
    )


def insert_typevar_constraints(cursor, node_id: int, constr_node_id: int):
    cursor.execute(
        "INSERT INTO typevar_constraints (typevar_node_id, constraint_node_id) VALUES (%s, %s)",
        (node_id, constr_node_id)
    )


def insert_typevar_variance(cursor, node_id: int, variance: Literal['invariant', 'covariant', 'contravariant']):
    cursor.execute(
        "INSERT INTO typevar_variances (typevar_node_id, variance) VALUES (%s, %s)",
        (node_id, variance)
    )


def insert_union_members(cursor, node_id: int, member_node_id: int):
    cursor.execute(
        "INSERT IGNORE INTO type_union_members (union_node_id, member_node_id) VALUES (%s, %s)",
        (node_id, member_node_id)
    )


def insert_type_node(cursor,
                     namespace: Optional[str],
                     name: Optional[str],
                     alias: Optional[str],
                     kind: str,
                     is_generic: bool,
                     python_version_id: int
                     ) -> int:
    """
    Inserts a row into type_nodes and returns the newly generated ID.
    """
    insert_sql = """
        INSERT INTO type_nodes
            (namespace, name, alias, kind, is_generic, version_id)
        VALUES (%s, %s, %s, %s, %s, %s)
    """
    cursor.execute(insert_sql, (
        namespace, name, alias, kind, is_generic, python_version_id
    ))
    return cursor.lastrowid

def insert_type_node_relation(cursor, parent_node_id: int, child_node_id: int):
    insert_sql = """
        INSERT IGNORE INTO type_node_relations
            (parent_node_id, child_node_id)
        VALUES (%s, %s)
    """
    cursor.execute(insert_sql, (
        parent_node_id, child_node_id
    ))


def get_or_create_type_node(cursor,
                            cache: Dict[Tuple, int],
                            namespace: Optional[str],
                            name: Optional[str],
                            alias: Optional[str],
                            kind: str,
                            is_generic: bool,
                            py_version_id: int
                            ) -> int:
    """
    Checks cache first; if not present, inserts a new type_nodes row.
    Cache key is (namespace, name, alias, kind, is_generic, base_node_id).
    """
    key = (namespace, name, alias, kind, is_generic)
    if key in cache:
        return cache[key]
    new_id = insert_type_node(cursor, namespace=namespace, name=name, alias=alias, kind=kind, is_generic=is_generic, python_version_id=py_version_id)
    cache[key] = new_id
    return new_id

# def update_type_node_set_base_node_id(cursor, base_node_id: int, target_node_id: int):
#     cursor.execute(
#         "UPDATE type_nodes SET base_node_id = %s WHERE id = %s",
#         (base_node_id, target_node_id)
#     )


def insert_instatiation_parameters(cursor, node_id: int, position: int, argument_node_id: int):
    cursor.execute(
        """
        INSERT INTO instantiation_parameters (instantiation_node_id, position, argument_node_id)
        VALUES (%s, %s, %s)
        """,
        (node_id, position, argument_node_id)
    )