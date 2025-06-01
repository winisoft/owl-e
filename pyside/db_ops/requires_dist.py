from typing import List, Optional

from helpers.requires_dist import RequiresDistInsertItem, insert_marker_expr

import logging
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

def ensure_scalar(val):
    if isinstance(val, tuple):
        if len(val) == 1:
            return val[0]
        raise TypeError(f"Expected scalar or singleton tuple, got multi-length tuple: {val}")
    return val

def insert_one_req_dist_spec(cursor, dependent_distribution: str, dependent_package_id: int, target_distribution: str,
                             target_package_id: Optional[int], target_extra_name: Optional[str],
                             operator: str, wildcard_segment: str, raw: str) -> int | None:
    logger.debug("Attempting to insert one requirement specifier...")
    try:
        cursor.execute(
            """
            INSERT INTO package_dist_requirements 
            (dependent_dist, dependent_package_id, required_dist, required_package_id, 
             required_extra_name, operator, wildcard_segment, raw)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            """,
            tuple(map(ensure_scalar, (
                dependent_distribution,
                dependent_package_id,
                target_distribution,
                target_package_id,
                target_extra_name,
                operator,
                wildcard_segment,
                raw,
            )))
        )
        new_id = cursor.lastrowid
        logger.debug(f"Requirement specifier inserted with id={new_id}")
        return new_id
    except Exception as e:
        logger.exception("Failed to insert requirement specifier")
        return None


def insert_requires_dist(
    cursor,
    insert_items: List[RequiresDistInsertItem]
) -> str | None:
    """
    Insert a list of requirement specifiers and optionally associate them with marker expressions.

    Each `RequiresDistInsertItem` provides both the normalized specifier and an optional parsed marker tree.
    """
    logger.debug("Starting insert_requires_dist...")

    for idx, item in enumerate(insert_items):
        spec = item.specifier
        logger.debug(f"Inserting requirement specifier {idx}: {spec}")

        new_id = insert_one_req_dist_spec(
            cursor,
            dependent_distribution=spec.dependent_distribution,
            dependent_package_id=spec.dependent_package_id,
            target_distribution=spec.target_distribution,
            target_package_id=spec.target_package_id,
            target_extra_name=spec.target_extra_name,
            operator=spec.operator,
            wildcard_segment=spec.wildcard_segment,
            raw=spec.raw
        )

        if new_id is None:
            msg = f"Failed to insert requirement specifier at index {idx}"
            logger.error(msg)
            return msg

        if item.marker_expr is not None:
            logger.debug(f"Inserting marker expression for requirement_id={new_id}")
            insert_marker_expr(
                cursor=cursor,
                expr=item.marker_expr,
                requirement_id=new_id,
                parent_id=None
            )

    return None  # success