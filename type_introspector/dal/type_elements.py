from typing import Literal, Set

import logging

from bll.type_node_models import TypeNode

logger = logging.getLogger(__name__)


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


def insert_type_union(cursor, node_id: int, member_node_ids: Set[int]):
    for member_node_id in member_node_ids:
        cursor.execute(
        "INSERT IGNORE INTO type_union_members (union_node_id, member_node_id) VALUES (%s, %s)",
        (node_id, member_node_id)
    )


def insert_type_alias(cursor, alias_id: int, target_id: int):
    cursor.execute(
        "INSERT IGNORE INTO type_aliases (alias_id, target_id) VALUES (%s, %s)",
        (alias_id, target_id)
    )


def insert_type_node_relation(
        cursor,
        parent_node_id: int,
        child_node_id: int,
        relation_kind: str = "inheritance"
    ):
    """
    Two distinct facts share this table, so they are discriminated explicitly:
      'inheritance'   — child_node subclasses parent_node (from __bases__)
      'instantiation' — child_node is parent_node parameterised, e.g. list -> list[str]
    """
    insert_sql = """
        INSERT IGNORE INTO type_node_relations
            (parent_node_id, child_node_id, relation_kind)
        VALUES (%s, %s, %s)
    """
    cursor.execute(insert_sql, (
        parent_node_id, child_node_id, relation_kind
    ))


def insert_type_node(
        cursor,
        node: TypeNode
    ) -> int:
    """
    Inserts a row into type_nodes and returns the newly generated ID.
    """
    dedup_hash = node.dedup_hash()
    insert_sql = """
        INSERT IGNORE INTO type_nodes
            (version_id, namespace, name, kind, is_generic, dedup_hash)
        VALUES (%s, %s, %s, %s, %s, %s)
    """
    cursor.execute(insert_sql, (
        node.py_version_id, node.namespace, node.name, node.kind, node.is_generic, dedup_hash
    ))

    return cursor.lastrowid

