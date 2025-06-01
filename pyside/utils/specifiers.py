import re
from typing import Literal, Optional, Tuple, Union
from packaging.specifiers import Specifier
from packaging.requirements import Requirement

from db_ops.packages import find_real_version_for_distribution, get_id_from_packages
from models.NormalizedSpecifier import NormalizedSpecifier
from utils.operators import normalize_operator
from utils.version import determine_wildcard_segment, get_all_version_fields, sanitize_wildcard_version


def normalize_specifier_info(
    cursor, 
    spec: Specifier, 
    req: Requirement
) -> NormalizedSpecifier:
    """
    Normalizes a single version specifier for a dependency requirement and resolves its target version.

    This function performs:
    - Normalization of the operator (e.g., converts '===' or '==' with wildcards).
    - Wildcard segment detection.
    - Sanitization of the version string to ensure parseability.
    - Full deconstruction of the version into components for DB resolution.
    - Resolution of the corresponding package version ID, or fallback via directional search
      for comparative operators if the version doesn't exist.

    Args:
        cursor: A DB-API 2.0 compatible database cursor used for queries.
        spec: A single `Specifier` object from `packaging.specifiers`.
        req: The full parsed `Requirement` object; `req.name` is used for lookup.

    Returns:
        NormalizedSpecifier:
            A dataclass containing:
              - target_package_id: The resolved version ID from the DB, or None if not found.
              - operator_override: Optional override if the operator was logically changed during fallback.
              - wildcard_segment: A string like 'minor' if the version used a wildcard in that segment.
              - safe_version: The sanitized version string used for parsing and comparison.
              - spec: The original `Specifier` object (passed through unchanged).
    """
    op = normalize_operator(spec.operator)
    safe_version = sanitize_wildcard_version(spec.version)
    wildcard_segment = determine_wildcard_segment(spec.version)

    fields = get_all_version_fields(safe_version)
    target_id = get_id_from_packages(cursor, req.name, *fields)

    operator_override = None
    if not target_id and op in {'LT', 'LE', 'GT', 'GE', 'COMPATIBLE'}:
        result: Optional[int] = find_real_version_for_distribution(cursor, req.name, *fields[:4], operator=op)
        if result:
            target_id = result
            operator_override = 'LE' if op == 'LT' else 'GE' if op == 'GT' else op
    else:
        operator_override = op

    if not operator_override:
        operator_override = op

    return NormalizedSpecifier(
        target_package_id=target_id,
        operator_override=operator_override,
        wildcard_segment=wildcard_segment,
        safe_version=safe_version,
        spec=spec
    )

def split_operator_and_version(spec_str: str) -> tuple[str, str]:
    """
    Splits a specifier string like '>=1.20.*' into ('>=', '1.20.*').
    """
    match = re.match(r"^\s*([<>=!~]{1,3})\s*(.+)", spec_str)
    if not match:
        raise ValueError(f"Could not parse operator/version from: {spec_str}")
    return match.group(1), match.group(2)


def get_version_bounds_for_operator(
    operator: Literal['LT', 'LE', 'GT', 'GE', 'COMPATIBLE'],
    epoch: int,
    major: int,
    minor: int,
    micro: int
) -> Union[
    Tuple[Tuple[int, int, int, int]],  # for single-bound
    Tuple[Tuple[int, int, int, int], Tuple[int, int, int, int]]  # for COMPATIBLE
]:
    """
    Returns a version comparison target or lower/upper bounds, depending on operator.
    Assumes version has already been normalized and split.
    """
    if operator == 'COMPATIBLE':
        lower = (epoch, major, minor, micro)
        if minor is not None:
            upper = (epoch, major, minor + 1, 0)
        else:
            upper = (epoch, major + 1, 0, 0)
        return lower, upper

    return ((epoch, major, minor, micro),)


