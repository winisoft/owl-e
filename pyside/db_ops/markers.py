"""
Database operations for environment markers.

This module provides functions for inserting and querying environment markers
in the database.
"""
import logging
from typing import Literal, Optional

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def insert_env_marker_expression(cursor, requirement_id: int, parent_id: Optional[int], mode: str) -> int:
    """
    Insert a new environment marker expression node and return its auto-generated ID.
    """
    logger.debug(f"Inserting into env_marker_expressions: req_id={requirement_id}, parent_id={parent_id}, mode={mode}")
    cursor.execute(
        """
        INSERT INTO env_marker_expressions (requirement_id, parent_id, mode)
        VALUES (%s, %s, %s)
        """,
        (requirement_id, parent_id, mode),
    )
    expr_id = cursor.lastrowid
    logger.debug(f"env_marker_expressions insert successful, id={expr_id}")
    return expr_id


def insert_env_marker_comparison(
        cursor, 
        expression_id: int, 
        variable: Literal[
            "python_version", "python_full_version", "os_name", "sys_platform",
            "platform_machine", "platform_python_implementation", "platform_release",
            "platform_system", "platform_version", "implementation_name", "implementation_version", "extra"
        ], 
        operator: Literal["==", "!=", "<", "<=", ">", ">=", "in", "not in"],
        value: str
    ):
    
    logger.debug(f"Inserting into env_marker_comparisons: expr_id={expression_id}, var={variable}, op={operator}, value={value}")
    cursor.execute(
        """
        INSERT INTO env_marker_comparisons (expression_id, variable, operator, value)
        VALUES (%s, %s, %s, %s)
        """,
        (expression_id, variable, operator, value),
    )
    logger.debug("env_marker_comparisons insert successful.")


def insert_env_marker_logical(cursor, expression_id: int, operator: str, left_child_id: int, right_child_id: int):
    logger.debug(f"Inserting into env_marker_logicals: expr_id={expression_id}, op={operator}, left={left_child_id}, right={right_child_id}")
    cursor.execute(
        """
        INSERT INTO env_marker_logicals (expression_id, operator, left_child_id, right_child_id)
        VALUES (%s, %s, %s, %s)
        """,
        (expression_id, operator, left_child_id, right_child_id),
    )
    logger.debug("env_marker_logicals insert successful.")