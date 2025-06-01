from config import OVERFLOW_COMPONENTS, MAX_UINT64, _OVERFLOW_REGEX
from typing import List, Dict, Optional


def is_overflow_numeric(value):
    """
    Return True if `value` (an int or str) would overflow BIGINT UNSIGNED.
    We check two ways:
      1. If it's a string of 20+ digits (no leading zeros) → definitely overflow.
      2. If parsed as int, exceeds MAX_UINT64.
    """
    if value is None:
        return False
    # If it's already an int:
    if isinstance(value, int):
        return value > MAX_UINT64
    # If it's a string of digits:
    s = str(value)
    if _OVERFLOW_REGEX.match(s):
        return True
    try:
        return int(s) > MAX_UINT64
    except Exception:
        # If it can't be parsed, assume overflow
        return True


def safe_int(value):
    """
    Try to parse `value` (string or int) into a Python int.
    Returns None if it cannot or should not be stored as an int.
    """
    if value is None:
        return None
    try:
        iv = int(value)
        return iv
    except Exception:
        return None


def has_overflow(parsed: dict) -> bool:
    return any(
        safe_int(parsed.get(field)) is not None and safe_int(parsed[field]) > MAX_UINT64
        for field in OVERFLOW_COMPONENTS
    )


def determine_version_overflows(version_id: int, parsed: dict) -> List[str]:
    """

    """
    overflow_sqls = []

    for field in OVERFLOW_COMPONENTS:
        value = parsed.get(field)
        if safe_int(value) is not None and safe_int(value) > MAX_UINT64:
            comp_type = field.upper()
            overflow_sql = (
                f"INSERT INTO overflow_versions (version_id, component_type, string_value) "
                f"VALUES ({version_id}, '{comp_type}', '{value}');"
            )
            overflow_sqls.append(overflow_sql)

    return overflow_sqls