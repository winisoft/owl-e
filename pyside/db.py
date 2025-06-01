import mysql.connector
from .config import DB_CONFIG, DB_NAME
from typing import List


def connect_db():
    """
    Create and return a new MySQL connection using DB_CONFIG.
    """
    return mysql.connector.connect(**DB_CONFIG)


def sql_escape(value) -> str:
    """
    Escape a value for use in an SQL statement.
    Handles None, bool, int, and string types.
    Returns 'NULL' for None, 'TRUE'/'FALSE' for bool, and properly quoted strings.
    """
    if value is None:
        return 'NULL'
    if isinstance(value, bool):
        return 'TRUE' if value else 'FALSE'
    if isinstance(value, int):
        return str(value)
    return "'" + str(value).replace("'", "''") + "'"


def formatInsert(table_name: str, columns: List[str]) -> str: 
    """
    Format an SQL insert statement for the given table and columns.
    """
    placeholders = ", ".join(["%s"] * len(columns))
    columns_str = ", ".join(columns)
    
    return f"INSERT INTO `{DB_CONFIG['database']}`.`{table_name}` ({columns_str}) VALUES ({placeholders})"


def insert(cursor, table_name: str, columns: List[str], args: List) -> int:
    """
    Insert a row into the specified table with the given columns and values.
    Returns the last inserted ID.
    """
    sql = formatInsert(table_name, columns)
    
    # Ensure args has the same length as columns
    if len(args) != len(columns):
        raise ValueError(f"Expected {len(columns)} arguments, got {len(args)}")
    
    cursor.execute(sql, args)
    return cursor.lastrowid