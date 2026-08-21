from typing import Literal, Optional, Tuple, Union
from db_ops.versions import logger
#from models.OwlePackage import OwlePackage
import logging
import os
from typing import Optional, Literal


logger = logging.getLogger(__name__)

OverrideOperator = Literal['LT', 'LE', 'GT', 'GE', 'COMPATIBLE']

# Where the banked PyPI responses are read from. The working schema holds derived data only;
# the raw payloads stay in the archive that phase A filled.
INFO_SOURCE_SCHEMA = os.getenv('INFO_SOURCE_SCHEMA', 'owle_test_archive')




def get_info(cursor, distribution: str, version_str: str) -> Optional[Tuple[int, str]]:

    cursor.execute(
        f"""
        SELECT version_id, info
        FROM {INFO_SOURCE_SCHEMA}.packages
        WHERE distribution = %s AND version_str = %s
        """,
        (distribution, version_str)
    )

    info_column = cursor.fetchone()
    if info_column is None:
        return None

    return info_column[0], info_column[1]


def update_package(
    cursor # , 
    # pkg: OwlePackage
    ) -> bool:
    """
    Update many columns in the packages table for a given distribution and version_str.
    Returns True if a row was updated, otherwise False.
    Logs diagnostic details if no match was found.
    """
    try: # 14
        update_sql = """
            UPDATE packages
            SET summary = %s,
                keywords = %s,
                description = %s,
                description_content_type = %s,
                license = %s,
                home_page = %s,
                project_url = %s,
                download_url = %s,
                docs_url = %s,
                yanked = %s,
                last_serial = %s
            WHERE distribution = %s AND version_str = %s AND id = %s
        """
        # cursor.execute(update_sql, ( # 14
        #     pkg.summary,
        #     pkg.keywords,
        #     pkg.description,
        #     pkg.description_content_type,
        #     pkg.license,
        #     pkg.home_page,
        #     pkg.project_url,
        #     pkg.download_url,
        #     pkg.docs_url,
        #     pkg.yanked,
        #     pkg.last_serial,
        #     pkg.distribution,
        #     pkg.version_string,
        #     pkg.version_id
        #     )
        # )

        return cursor.rowcount > 0
    except Exception as e:
        logger.exception(f"Unexpected error during update for {pkg.distribution}=={pkg.version_string}")
        return False


def insert_package_tags(cursor, distribution: str, version_id: int, associated_tag_ids: list[int]) -> None:
    if not associated_tag_ids:
        print(f"No associated tags for {distribution} {version_id}")
        return

    sql = """
    INSERT INTO package_tags (distribution, package_id, tag_id)
    VALUES (%s, %s, %s)
    """
    values = [
        (
            distribution,
            version_id,
            tag_id
        )
        for tag_id in associated_tag_ids
    ]

    cursor.executemany(sql, values)


def insert_extras(cursor, distribution: str, version_id: int, extras: list[str]) -> None:
    if not extras:
        return

    # the columns are (distribution, package_id, name); `versionId`/`extraName` were an older
    # spelling and this statement would have failed as written
    sql = """
    INSERT IGNORE INTO package_extras (distribution, package_id, name)
    VALUES (%s, %s, %s)
    """
    values = [
        (
            distribution,
            version_id,
            extra
        )
        for extra in extras
    ]

    cursor.executemany(sql, values)


def get_target_package(cursor, target_dist: str, version_id: int) -> Optional[int]:
    """
    Look up the `id` in the `packages` table for (distribution=target_dist, version_str=version_str).
    Returns None if not found.
    """
    cursor.execute(
        "SELECT version_id FROM packages WHERE distribution = %s AND version_id = %s",
        (target_dist, version_id)
    )
    row = cursor.fetchone()
    return row[0] if row else None


def create_dist(cursor, distribution: str) -> bool:
    """
    Insert a new row into packages. Returns True if insertion succeeded.
    """
    insert_sql = """
      INSERT IGNORE INTO distrubutions (name) VALUES (%s);
    """
    try:
        cursor.execute(insert_sql, (
            distribution,
        ))
        return True
    except Exception as e:
        logger.error(f"Failed to insert {distribution}")
        return False


def create_package(cursor, distribution: str, version_id: int, version_string: str) -> bool:
    """
    Insert a new row into `packages`. Returns True if insertion succeeded.
    """
    insert_sql = """
      INSERT INTO packages 
        (version_id, distribution, version_str, 
         summary, keywords, description, 
         description_content_type, license, home_page, 
         project_url, download_url, docs_url, 
         yanked, last_serial)
      VALUES (%s, %s, %s, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0);
    """
    try:
        cursor.execute(insert_sql, (
            version_id,
            distribution,
            version_string,  # corrected position: version_string goes into version_str col
            None             # summary is NULL in this example; adjust if you have real summary
        ))
        return True
    except Exception as e:
        logger.error(f"Failed to insert package {distribution}=={version_string}: {e}")
        return False


def get_compatible_bounds(
    epoch: int,
    major: int,
    minor: Optional[int],
    micro: Optional[int]
) -> Tuple[Tuple[int, int, int, int], Tuple[int, int, int, int]]:
    """
    Returns the lower and upper bounds for a compatible release specifier.
    """
    lower = (epoch, major, minor or 0, micro or 0)

    if minor is not None:
        upper = (epoch, major, minor + 1, 0)
    else:
        upper = (epoch, major + 1, 0, 0)

    return lower, upper


def get_single_bound(
    operator: Literal['LT', 'LE', 'GT', 'GE'],
    epoch: int,
    major: int,
    minor: int,
    micro: int
) -> Tuple[int, int, int, int]:
    """
    Returns a version comparison target for single-bound operators.
    """
    return (epoch, major, minor, micro)


def find_real_version_for_distribution(
    cursor,
    distribution: str,
    epoch: int,
    major: int,
    minor: int,
    micro: int,
    operator: OverrideOperator
) -> Optional[int]:
    """
    Returns the `packages.id` of a real version of the given distribution
    that satisfies the interpreted constraint. Used to substitute fake
    versions in a specifier (e.g., <1.5 when 1.5 doesn't exist).
    """
    if operator in ('LT', 'LE', 'COMPATIBLE'):
        order = 'DESC'
    elif operator in ('GT', 'GE'):
        order = 'ASC'
    else:
        raise ValueError(f"Unsupported operator: {operator}")
    
    if operator == 'COMPATIBLE':
        lower, upper = get_compatible_bounds(epoch, major, minor, micro)
    else:
        target = get_single_bound(operator, epoch, major, minor, micro)

    query = """
        SELECT p.version_id
        FROM packages p
        JOIN versions v ON v.id = p.version_id
        WHERE p.distribution = %s
    """

    if operator == 'LT':
        query += " AND (v.epoch, v.major, v.minor, v.micro) < (%s, %s, %s, %s)"
        params = (distribution, *target)
    elif operator == 'LE':
        query += " AND (v.epoch, v.major, v.minor, v.micro) <= (%s, %s, %s, %s)"
        params = (distribution, *target)
    elif operator == 'GT':
        query += " AND (v.epoch, v.major, v.minor, v.micro) > (%s, %s, %s, %s)"
        params = (distribution, *target)
    elif operator == 'GE':
        query += " AND (v.epoch, v.major, v.minor, v.micro) >= (%s, %s, %s, %s)"
        params = (distribution, *target)
    elif operator == 'COMPATIBLE':
        query += """
            AND (v.epoch, v.major, v.minor, v.micro) >= (%s, %s, %s, %s)
            AND (v.epoch, v.major, v.minor, v.micro) < (%s, %s, %s, %s)
        """
        params = (distribution, *lower, *upper)
    else:
        raise ValueError(f"Unsupported operator: {operator}")

    query += f"""
        ORDER BY
            v.epoch {order},
            v.major {order},
            v.minor {order},
            v.micro {order},
            CASE v.phase
                WHEN 'alpha' THEN 1
                WHEN 'beta' THEN 2
                WHEN 'candidate' THEN 3
                WHEN 'final' THEN 4
            END {order},
            v.phase_number {order},
            v.post_number {order},
            (v.dev_number IS NOT NULL) DESC,
            v.dev_number {order}
        LIMIT 1
    """

    cursor.execute(query, params)
    row = cursor.fetchone()
    return row[0] if row else None


def get_id_from_packages(
        cursor,
        distribution: str,
        epoch: int,
        major: int,
        minor: int,
        micro: int,
        phase: str,
        phase_number: Union[int|None],
        post_number: Union[int|None],
        dev_number: Union[int|None],
        local: Union[str|None],
        has_overflow: bool,
    ) -> int | None:
    """
    Use normalized version string to either fetch or insert version.
    Returns version_id or None on failure.
    """

    sql = f"""
    SELECT p.version_id
    FROM packages p 
    JOIN versions v ON v.id = p.version_id
    WHERE {'v.epoch = %s AND v.major = %s AND v.minor = %s AND v.micro = %s'} 
    AND {'v.phase = %s'} 
    AND {'v.phase_number = %s' if phase_number is not None else 'v.phase_number IS %s'}
    AND {'v.post_number = %s' if post_number is not None else 'v.post_number IS %s'}
    AND {'v.dev_number = %s' if dev_number is not None else 'v.dev_number IS %s'}
    AND {'v.local = %s' if local is not None else 'v.local IS %s'}
    AND {'v.has_overflow IS %s'}
    AND {"p.distribution = %s"}
    """

    args = (
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow, distribution
    )

    cursor.execute(sql, args)

    row = cursor.fetchone()

    return row

