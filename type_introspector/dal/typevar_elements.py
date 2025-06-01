from typing import Literal




def insert_typevar_bounds(cursor, node_id: int, bound_node_id: int):
    cursor.execute(
        "INSERT INTO typevar_bounds (typevar_node_id, bound_node_id) VALUES (%s, %s)",
        (node_id, bound_node_id)
    )



def insert_typevar_constraints(cursor, node_id: int, constraint_node_id: int):
    cursor.execute(
        "INSERT INTO typevar_constraints (typevar_node_id, constraint_node_id) VALUES (%s, %s)",
        (node_id, constraint_node_id)
    )



def insert_typevar_variance(cursor, node_id: int, variance: Literal['invariant', 'covariant', 'contravariant']):
    cursor.execute(
        "INSERT INTO typevar_variances (typevar_node_id, variance) VALUES (%s, %s)",
        (node_id, variance)
    )
