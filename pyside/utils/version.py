"""
Utilities for handling and normalizing package version strings.

This module provides functions for working with PEP 440 version strings,
detecting overflow conditions, and handling wildcard versions.
"""

from typing import Dict, List, Literal, Optional, Tuple, Union
from packaging.version import Version, InvalidVersion
import logging
import re

from config import OVERFLOW_COMPONENTS, MAX_UINT64, _OVERFLOW_REGEX

logger = logging.getLogger(__name__)


def is_overflow_numeric(value) -> bool:
    """
    True if value would overflow BIGINT UNSIGNED (MAX_UINT64).
    """
    if value is None:
        return False
    if isinstance(value, int):
        return value > MAX_UINT64
    s = str(value)
    if _OVERFLOW_REGEX.match(s):
        return True
    try:
        return int(s) > MAX_UINT64
    except Exception:
        return True
    

def get_overflow_fields_v2(version_str: str) -> List[Tuple[str, str]]:

    version = Version(version_str)

    overflowed = []
    for key in OVERFLOW_COMPONENTS:
        if key == "epoch":
            overflow = check_overflow("epoch", version_str, version.epoch)
            if overflow:
                overflowed.append(overflow)
        elif key == "major":
            overflow = check_overflow("major", version_str, version.major)
            if overflow:
                overflowed.append(overflow)
        elif key == "minor":
            overflow = check_overflow("minor", version_str, version.minor)
            if overflow:
                overflowed.append(overflow)
        elif key == "micro":
            overflow = check_overflow("micro", version_str, version.micro)
            if overflow:
                overflowed.append(overflow)
        elif key == "phase_number":
            if not version.is_prerelease:
                continue
            overflow = check_overflow("phase_number", version_str, version.pre[1])
            if overflow:
                overflowed.append(overflow)
        elif key == "post_number":
            if not version.is_devrelease:
                continue
            overflow = check_overflow("post_number", version_str, version.post)
            if overflow:
                overflowed.append(overflow)
        elif key == "dev_number":
            if not version.is_devrelease:
                continue
            overflow = check_overflow("dev_number", version_str, version.dev)
            if overflow:
                overflowed.append(overflow)
        else:
            return None


def sanitize_wildcard_version(version_str: str) -> str:
    """
    Converts a wildcard version like '2.2.*' into a safe form ('2.2') for Version()
    and returns that. Leaves non-wildcard versions unchanged.
    
    Args:
        version_str: A version string, possibly containing wildcards
        
    Returns:
        A version string with wildcards removed
    """
    if ".*" in version_str:
        return version_str.replace(".*", "")
    return version_str


def determine_wildcard_segment(version_str: str) -> Literal['MAJOR', 'MINOR', 'MICRO', 'NONE']:
    """
    Determine which segment is wildcarded in a version string.
    
    Args:
        version_str: A version string, possibly ending with '.*'
        
    Returns:
        The wildcard segment type: 'MAJOR', 'MINOR', 'MICRO', or 'NONE'
    """
    if version_str.endswith(".*"):
        segments = version_str[:-2].split(".")
        if len(segments) == 1:
            return 'MAJOR'
        elif len(segments) == 2:
            return 'MINOR'
        elif len(segments) == 3:
            return 'MICRO'
    return 'NONE'


def get_pep440_version_fields(version_str: str) -> Optional[Dict[str, Union[int, str, None]]]:
    from db_ops.versions import logger
    try:
        v = Version(version_str)
    except InvalidVersion:
        logger.error(f"Invalid version string! Could not parse PEP440 packaging.versions.Version: {version_str}")
        return None

    release = list(v.release)

    # Ensure we have at least 3 components (major, minor, micro)
    while len(release) < 3:
        release.append(0)

    major, minor, micro = release[:3]

    # Determine pre-release phase and number
    phase = (
        "alpha" if v.pre and v.pre[0] == "a"
        else "beta" if v.pre and v.pre[0] == "b"
        else "candidate" if v.pre and v.pre[0] in ("rc", "c")
        else "final"
    )
    phase_number = v.pre[1] if v.pre else None

    return { 
        "epoch": v.epoch, 
        "major": major, 
        "minor": minor, 
        "micro": micro, 
        "phase": phase, 
        "phase_number": phase_number, 
        "post_number": v.post, 
        "dev_number": v.dev, 
        "local": v.local 
    }


def get_all_version_fields(version_str: str) -> Optional[Tuple[
        int,      # epoch
        int,      # major
        int,      # minor
        int,      # micro
        str,      # phase
        Optional[int],  # phase_number
        Optional[int],  # post_number
        Optional[int],  # dev_number
        Optional[str],  # local
        bool      # has_overflow
    ]]:
    """
    Extract all version fields from a version string, handling overflow conditions.
    
    Args:
        version_str: A version string compliant with PEP 440
        
    Returns:
        A tuple containing all version components with overflow handling, or None if invalid
    """
    fields = get_pep440_version_fields(version_str)

    if not fields:
        return None
    
    normal_values = {}
    overflowed = []
    for key in OVERFLOW_COMPONENTS:
        val = fields.get(key)
        if val is None:
            normal_values[key] = None
            continue
        if is_overflow_numeric(val):
            logger.warning(f"Overflow detected for {key} in version {version_str}: {val}")
            overflowed.append((key, str(val)))
            normal_values[key] = 0
        else:
            normal_values[key] = val
    
    has_overflow = len(overflowed) > 0

    return (
        normal_values['epoch'], 
        normal_values['major'], 
        normal_values['minor'], 
        normal_values['micro'],
        fields['phase'], 
        normal_values['phase_number'], 
        normal_values['post_number'], 
        normal_values['dev_number'],
        fields['local'], 
        has_overflow
    )


def get_overflow_fields(version_str: str) -> List[Tuple[str, str]]:
    """
    Identify which fields in a version string would overflow database limits.
    
    Args:
        version_str: A version string to check for overflow
        
    Returns:
        A list of (field_name, string_value) tuples for overflowed components
    """
    try:
        version = Version(version_str)
    except InvalidVersion:
        logger.error(f"Invalid version string for overflow check: {version_str}")
        return []

    overflowed = []
    
    # Check each component for overflow
    if is_overflow_numeric(version.epoch):
        overflowed.append(("epoch", str(version.epoch)))
    
    # Check release components
    if version.release:
        if len(version.release) > 0 and is_overflow_numeric(version.release[0]):
            overflowed.append(("major", str(version.release[0])))
        if len(version.release) > 1 and is_overflow_numeric(version.release[1]):
            overflowed.append(("minor", str(version.release[1])))
        if len(version.release) > 2 and is_overflow_numeric(version.release[2]):
            overflowed.append(("micro", str(version.release[2])))
    
    # Check pre-release
    if version.pre and is_overflow_numeric(version.pre[1]):
        overflowed.append(("phase_number", str(version.pre[1])))
    
    # Check post-release
    if version.post is not None and is_overflow_numeric(version.post):
        overflowed.append(("post_number", str(version.post)))
    
    # Check dev-release
    if version.dev is not None and is_overflow_numeric(version.dev):
        overflowed.append(("dev_number", str(version.dev)))
    
    return overflowed 


def check_overflow(key: str, version_str: str, value):

    if is_overflow_numeric(value):
        logger.debug(f"Overflow detected for {key} in version {version_str}: {value}")
        return (key, value)
    else:
        return None
