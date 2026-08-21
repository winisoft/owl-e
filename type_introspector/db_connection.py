import logging
import os

from contextlib import contextmanager

import mysql.connector


logger = logging.getLogger(__name__)



def get_db_connection():
    """
    A connection for the introspection run.

    Connects as `type_introspector`, the account provisioned for exactly this job, rather than as a
    person. Every value comes from the environment; the passwords themselves live in
    `credentials.md` at the repository root, which is untracked and gitignored.
    """
    conn = mysql.connector.connect(
        user=os.environ.get('DB_USER_DEV', 'type_introspector'),
        password=os.environ['DB_PASSWORD_DEV'],
        host=os.environ.get('DB_HOST_DEV', 'localhost'),
        database=os.environ.get('DB_NAME_DEV', 'owle_dev'),
    )

    return conn


@contextmanager
def server_bound(cursor):
    """
    A prepared cursor on the same connection, and so the same transaction.

    mysql-connector builds a statement by interpolating escaped values into the SQL text and then
    re-parses that text before sending it. The parser does not recognise a backslash-escaped quote
    as escaped, so free text carrying backslashes or an odd number of quotes derails it - here it
    surfaces as a "backslash character is not a valid delimiter" interface error while inserting a
    docstring, since docstrings are full of regex examples and escape sequences.

    Server-side binding sends the value as a parameter instead of as SQL text, so none of that
    parsing touches it. Use it for any insert carrying prose; the ids and enums everywhere else in
    this package do not need it.
    """
    conn = getattr(cursor, "_connection", None) or getattr(cursor, "_cnx", None)
    if conn is None:
        yield cursor
        return
    bound = conn.cursor(prepared=True)
    try:
        yield bound
    finally:
        try:
            bound.close()
        except Exception:
            pass
