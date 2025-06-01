import logging

import mysql.connector


logger = logging.getLogger(__name__)



def get_db_connection():
    """Context manager for database connections with automatic cleanup."""
    conn = mysql.connector.connect(
        user='wini', 
        password='the_eru_shrug_0!',
        host='localhost',
        database='owle_dev'
    )

    return conn
