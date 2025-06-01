"""
Base database operations and utilities.

This module provides common database helper functions and standardized
error handling for database operations.
"""

import logging
from typing import Any, Callable, Optional, Tuple, TypeVar, Union

T = TypeVar('T')
logger = logging.getLogger(__name__)


def execute_with_error_handling(
    cursor, 
    sql: str, 
    params: Tuple[Any, ...] = None,
    success_handler: Optional[Callable[[], T]] = None
) -> Optional[T]:
    """
    Execute a SQL statement with standardized error handling.
    
    Args:
        cursor: Database cursor
        sql: SQL statement to execute
        params: Parameters for the SQL statement
        success_handler: Function to call on success
        
    Returns:
        Result of success_handler if provided and execution succeeds, otherwise None
    """
    try:
        if params:
            cursor.execute(sql, params)
        else:
            cursor.execute(sql)
            
        if success_handler:
            return success_handler()
        return None
    except Exception as e:
        logger.error(f"Database error executing: {sql}")
        logger.error(f"Error details: {str(e)}")
        return None


def insert_and_get_id(cursor, sql: str, params: Tuple[Any, ...]) -> Optional[int]:
    """
    Insert a record and return its ID.
    
    Args:
        cursor: Database cursor
        sql: SQL INSERT statement
        params: Parameters for the SQL statement
        
    Returns:
        The ID of the inserted record, or None if the insertion failed
    """
    return execute_with_error_handling(
        cursor, 
        sql, 
        params, 
        lambda: cursor.lastrowid
    )


def interpolate_query(sql: str, args: tuple) -> str:
    def sql_literal(val):
        if val is None:
            return 'NULL'
        elif isinstance(val, str):
            return "'{}'".format(val.replace("'", "''"))
        elif isinstance(val, (int, float)):
            return str(val)
        else:
            return repr(val)  # fallback

    parts = sql.split('%s')
    result = []

    for i in range(len(parts)):
        result.append(parts[i])
        if i < len(args):
            result.append(sql_literal(args[i]))

    return ''.join(result)
