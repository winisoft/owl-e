import logging
import os

import mysql.connector


logger = logging.getLogger(__name__)



def get_db_connection():
    """Context manager for database connections with automatic cleanup."""
    conn = mysql.connector.connect(
        user='wini', 
        password=os.environ['DB_PASSWORD_DEV'],
        host='localhost',
        database=os.environ['DB_NAME_DEV']
    )

    return conn
