import mysql.connector
from mysql.connector import pooling
from contextlib import contextmanager
import logging
import os
from typing import Optional
from config import DB_CONFIG

logger = logging.getLogger(__name__)

# Process-specific connection pools
_connection_pools = {}

def get_process_pool_name():
    """Get a unique pool name for the current process."""
    return f"package_processor_pool_{os.getpid()}"

def initialize_connection_pool():
    """Initialize a process-specific MySQL connection pool."""
    pool_name = get_process_pool_name()
    if pool_name not in _connection_pools:
        try:
            pool = pooling.MySQLConnectionPool(
                pool_name=pool_name,
                pool_size=3,  # Reduced pool size since it's per-process
                pool_reset_session=True,
                **DB_CONFIG
            )
            _connection_pools[pool_name] = pool
            logger.info(f"Database connection pool initialized for process {os.getpid()}")
        except mysql.connector.Error as e:
            logger.error(f"Failed to initialize connection pool: {e}")
            raise

def connect_db():
    """
    Create and return a new MySQL connection using process-specific pool.
    """
    try:
        pool_name = get_process_pool_name()
        if pool_name not in _connection_pools:
            initialize_connection_pool()
        
        connection = _connection_pools[pool_name].get_connection()
        return connection
    except mysql.connector.Error as e:
        logger.error(f"Failed to get database connection: {e}")
        raise

def cleanup_pool():
    """Cleanup the connection pool for the current process."""
    pool_name = get_process_pool_name()
    if pool_name in _connection_pools:
        try:
            pool = _connection_pools[pool_name]
            # Get all connections from the pool's queue
            while not pool._cnx_queue.empty():
                try:
                    conn = pool._cnx_queue.get(block=False)
                    if conn and hasattr(conn, 'close'):
                        conn.close()
                except:
                    pass  # Queue might be empty now
            del _connection_pools[pool_name]
            logger.info(f"Cleaned up connection pool for process {os.getpid()}")
        except Exception as e:
            logger.warning(f"Error during pool cleanup: {e}")

@contextmanager
def get_db_connection():
    """Context manager for database connections with automatic cleanup."""
    conn = None
    try:
        conn = connect_db()
        yield conn
        if conn and hasattr(conn, '_cnx') and conn._cnx:
            conn.commit()
    except Exception as e:
        if conn and hasattr(conn, '_cnx') and conn._cnx:
            try:
                conn.rollback()
            except:
                pass
        logger.error(f"Database operation failed: {e}")
        raise
    finally:
        if conn:
            try:
                conn.close()
            except Exception as e:
                logger.warning(f"Error closing connection: {e}")
                pass  # Ignore errors during connection cleanup