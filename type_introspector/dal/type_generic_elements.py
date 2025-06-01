import mysql
import mysql.connector

from typing import Tuple


def insert_type_instantiation_arg(
    cursor,
    instantiation_node_id: int,
    arg_node_id: int,
    position: int
) -> Tuple[int, bool]:
    """Returns (id, was_inserted) where was_inserted indicates if a new row was created."""
    try:
        cursor.execute(
            """
            INSERT IGNORE INTO type_instantiation_args
                (instantiation_id, param_node_id, param_position)
            VALUES (%s, %s, %s)
            """,
            (instantiation_node_id, arg_node_id, position)
        )
        if cursor.rowcount == 0:
            print(f"No rows inserted for instantiation arg: inst_id={instantiation_node_id}, arg_id={arg_node_id}, pos={position}")
            return cursor.lastrowid, False
        return cursor.lastrowid, True
    except mysql.connector.errors.IntegrityError as e:
        if e.errno == 1062:  # Duplicate key
            # Already exists — get its ID
            cursor.execute(
                """
                SELECT id FROM type_instantiation_args
                WHERE instantiation_id = %s AND param_position = %s
                """,
                (instantiation_node_id, position)
            )
            row = cursor.fetchone()
            if row:
                print(f"Found existing instantiation arg: inst_id={instantiation_node_id}, arg_id={arg_node_id}, pos={position}")
                return row[0], False
            raise RuntimeError(f"Duplicate entry for (instantiation_id={instantiation_node_id}, position={position}) but SELECT found nothing")
        else:
            print(f"IntegrityError inserting instantiation arg: {e}")
            raise


def insert_type_instantiation(cursor, instantiation_node_id: int, origin_node_id: int) -> Tuple[int, bool]:
    """Returns (id, was_inserted) where was_inserted indicates if a new row was created."""
    try:
        cursor.execute(
            """
            INSERT IGNORE INTO type_instantiations (instantiation_node_id, origin_node_id)
            VALUES (%s, %s)
            """,
            (instantiation_node_id, origin_node_id)
        )

        if cursor.rowcount == 0:
            print(f"No rows inserted for instantiation: inst_node={instantiation_node_id}, origin_node={origin_node_id}")
            # Must already exist, fetch it
            cursor.execute(
                """
                SELECT id FROM type_instantiations
                WHERE instantiation_node_id = %s AND origin_node_id = %s
                """,
                (instantiation_node_id, origin_node_id)
            )
            row = cursor.fetchone()
            if not row:
                raise RuntimeError(f"Failed to locate type_instantiation after ignored insert: ({instantiation_node_id}, {origin_node_id})")
            print(f"Found existing instantiation: inst_node={instantiation_node_id}, origin_node={origin_node_id}")
            return row[0], False
        else:
            print(f"Successfully inserted new instantiation: inst_node={instantiation_node_id}, origin_node={origin_node_id}")
            return cursor.lastrowid, True
    except mysql.connector.errors.IntegrityError as e:
        print(f"IntegrityError inserting instantiation: {e}")
        raise


def insert_class_generic_params(cursor, node_id: int, typevar_node_id: int, idx: int):
    """
    """
    cursor.execute(
        """
        INSERT IGNORE INTO type_generic_params (class_node_id, typevar_node_id, position)
        VALUES (%s, %s, %s)
        """,
        (node_id, typevar_node_id, idx)
    )
