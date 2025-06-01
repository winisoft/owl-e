from typing import List, Optional, Union, Tuple
from packaging.version import Version, InvalidVersion
from config import OVERFLOW_COMPONENTS, MAX_UINT64, _OVERFLOW_REGEX
import logging
from db_ops.base import interpolate_query
from models.OverflowVersion import OverflowVersion
from utils.version import check_overflow, get_all_version_fields
from typing import Tuple
from packaging.version import Version

logger = logging.getLogger(__name__)

VersionTuple = Tuple[int, int, int, int]  # epoch, major, minor, micro

def compatible_version_bounds(version_str: str) -> Tuple[VersionTuple, VersionTuple]:
    """
    Given a version string, return (inclusive_lower_bound, exclusive_upper_bound)
    suitable for a compatible release (`~=`) operator.
    """
    version = Version(version_str)

    lower = (
        version.epoch,
        version.major,
        version.minor if version.release.__len__() > 1 else 0,
        version.micro if version.release.__len__() > 2 else 0
    )

    # Determine which component to increment:
    if len(version.release) >= 3:
        # e.g. 1.4.5 → upper = 1.5.0
        upper = (version.epoch, version.major, version.minor + 1, 0)
    elif len(version.release) == 2:
        # e.g. 1.4 → upper = 2.0.0
        upper = (version.epoch, version.major + 1, 0, 0)
    elif len(version.release) == 1:
        # e.g. 1 → upper = 2.0.0
        upper = (version.epoch, version.major + 1, 0, 0)
    else:
        raise ValueError(f"Invalid release format in version: {version_str!r}")

    return lower, upper


def ensure_package_version(
        cursor, 
        version_string: str,
    ) -> int:
    """
    Ensure that a version row exists in the `versions` table.
    If it exists, return its ID. If not, insert it and return the new ID.
    """
    (epoch, major, minor, micro, 
     phase, phase_number, 
     post_number, dev_number, local, 
     has_overflow
     ) = get_all_version_fields(version_string)

    id = get_id_from_versions(
        cursor, 
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    )

    if id is not None:
        logger.debug(f"Version {version_string} already exists with ID {id}")
        return id  # Version already exists, return its ID

    id = insert_version(
        cursor,  
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    )
    
    return id


def insert_version(
    cursor, 
    epoch: int,
    major: int,
    minor: int,
    micro: int,
    phase: str,
    phase_number: Union[int|None],
    post_number: Union[int|None],
    dev_number: Union[int|None],
    local: Union[str|None],
    has_overflow: bool
) -> int:
    """
    Insert a new version row into the `versions` table.
    """
    insert_sql = """
      INSERT INTO versions 
        (epoch, major, minor, micro, 
        phase, phase_number, 
        post_number, dev_number, local, 
        has_overflow) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
    """
    cursor.execute(insert_sql, (
        epoch,
        major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow,
    ))

    version_id = cursor.lastrowid

    return version_id


def get_id_from_python_versions(
        cursor, 
        epoch: int,
        major: int,
        minor: int,
        micro: int,
        phase: str,
        phase_number: Union[int|None],
        post_number: Union[int|None],
        dev_number: Union[int|None],
        local: Union[str|None],
        has_overflow: bool
    ) -> Union[int|None]:
    """
    Get the ID of a version of Python (table python_versions) associated with a record in the primary versions table.
    If no such python version exists, return None.
    """

    sql = f"""
    SELECT pv.id 
    FROM python_versions pv 
    JOIN versions v ON v.id = pv.id
    WHERE {'v.epoch = %s AND v.major = %s AND v.minor = %s AND v.micro = %s'} 
    AND {'v.phase = %s'} 
    AND {'v.phase_number = %s' if phase_number is not None else 'v.phase_number IS %s'}
    AND {'v.post_number = %s' if post_number is not None else 'v.post_number IS %s'}
    AND {'v.dev_number = %s' if dev_number is not None else 'v.dev_number IS %s'}
    AND {'v.local = %s' if local is not None else 'v.local IS %s'}
    AND {'v.has_overflow IS %s'}
    """
    args = (
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    )

    cursor.execute(sql, args)
    row = cursor.fetchone()

    if row:
        logger.debug(f"Found PythonVersion ID: {row[0]}")
        return row[0]

    return None


def get_id_from_versions(
        cursor, 
        epoch: int,
        major: int,
        minor: int,
        micro: int,
        phase: str,
        phase_number: Union[int|None],
        post_number: Union[int|None],
        dev_number: Union[int|None],
        local: Union[str|None],
        has_overflow: bool
    ) -> int | None:
    """
    Use normalized version string to either fetch or insert version.
    Returns version_id or None on failure.
    """
    
    sql = f"""
    SELECT id 
    FROM versions
    WHERE epoch = %s AND major = %s AND minor = %s AND micro = %s 
    AND phase = %s AND {'phase_number = %s' if phase_number is not None else 'phase_number IS %s'}
    AND {'post_number = %s' if post_number is not None else 'post_number IS %s'}
    AND {'dev_number = %s' if dev_number is not None else 'dev_number IS %s'}
    AND {'local = %s' if local is not None else 'local IS %s'}
    AND has_overflow IS %s"""

    args = (
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    )

    logger.debug("Executing: %s with args %s", sql, args)
    its_value = interpolate_query(sql, args)

    cursor.execute(sql, (
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    ))

    row = cursor.fetchone()

    if row:
        logger.debug(f"Found versions table version ID: {row[0]}")
        return row[0]

    logger.debug(f"No existing version found for: {phase} release {epoch}.{major}.{minor}.{micro} "
                f"pre=[{phase_number}] post=[{post_number}] dev=[{dev_number}] local=[{local}] has_overflow=[{has_overflow}]")
    
    return None


def get_overflow_version_exists(cursor, overflow_version: OverflowVersion) -> bool:
    """
    Check if an overflow version record exists in the `overflow_versions` table.
    Returns True if it exists, False otherwise.
    """
    cursor.execute(
        """
        SELECT 1 FROM overflow_versions
        WHERE version_id = %s AND component_type = %s AND string_value = %s
        LIMIT 1
        """,
        (overflow_version.version_id, overflow_version.component_type, overflow_version.string_value)
    )

    return cursor.fetchone() is not None


def insert_overflow_version(cursor, overflow_version: OverflowVersion):
    """
    Insert a new overflow version record into the `overflow_versions` table.
    """
    insert_sql = (
        "INSERT INTO overflow_versions (version_id, component_type, string_value) "
        "VALUES (%s, %s, %s)"
    )
    cursor.execute(insert_sql, (
        overflow_version.version_id,
        overflow_version.component_type,
        overflow_version.string_value
    ))

    return cursor.lastrowid


def get_target_version_id(cursor, distribution: str, version_string: str) -> Optional[int]:
    """
    Get the version ID for a target distribution and version string.
    Returns None if not found.
    """
    cursor.execute(
        """
        SELECT v.id 
        FROM versions v 
        JOIN packages p ON v.id = p.id 
        WHERE p.distribution = %s AND v.version_str = %s
        LIMIT 1
        """,
        (distribution, version_string)
    )

    row = cursor.fetchone()
    return row[0] if row else None


def get_python_version_specifier(cursor, version_id: int, operator: str, wildcard: str) -> Optional[int]:
    cursor.execute(
        """
        SELECT id
          FROM python_version_specifiers
          WHERE version_id = %s AND operator = %s AND wildcard = %s
        """,
        (version_id, operator, wildcard)
    )

    row = cursor.fetchone()

    if not row:
        logger.error(f"No Python version specifier found for version_id={version_id}, operator={operator}, wildcard={wildcard}")
        return None

    return row[0]

