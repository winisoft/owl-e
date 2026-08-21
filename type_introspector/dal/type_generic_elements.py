import mysql
import mysql.connector

from typing import Tuple


def insert_type_instantiation_arg(
    cursor,
    instantiation_id: int,
    arg_node_id: int,
    position: int
) -> Tuple[int, bool]:
    """
    `instantiation_id` is the `type_instantiations` row id returned by
    [insert_type_instantiation] - NOT the instantiation's `type_nodes` id, which is what the
    parameter used to be called. Passing the node id satisfied no foreign key, and because the
    insert below is an INSERT IGNORE the violation was reported as "no rows inserted" rather than
    raised, leaving `type_instantiation_args` empty.
    """
    """Returns (id, was_inserted) where was_inserted indicates if a new row was created."""
    try:
        cursor.execute(
            """
            INSERT IGNORE INTO type_instantiation_args
                (instantiation_id, param_node_id, param_position)
            VALUES (%s, %s, %s)
            """,
            (instantiation_id, arg_node_id, position)
        )
        if cursor.rowcount == 0:
            pass  # per-row logging removed: one line per insert is millions of lines
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
                (instantiation_id, position)
            )
            row = cursor.fetchone()
            if row:
                pass  # per-row logging removed: one line per insert is millions of lines
                return row[0], False
            raise RuntimeError(f"Duplicate entry for (instantiation_id={instantiation_id}, position={position}) but SELECT found nothing")
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
            pass  # per-row logging removed: one line per insert is millions of lines
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
            pass  # per-row logging removed: one line per insert is millions of lines
            return row[0], False
        else:
            pass  # per-row logging removed: one line per insert is millions of lines
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
