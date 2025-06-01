from dataclasses import dataclass
from typing import Literal, Optional


@dataclass
class RequiredDistSpecifier:
    """
    Associates a package's version with a requirement from its requires_dist metadata.
    """
    dependent_distribution: str
    dependent_package_id: int
    target_distribution: str
    target_package_id: Optional[int]
    operator: Literal['LT', 'LE', 'NE', 'EQ', 'GE', 'GT', 'COMPATIBLE', 'EXACT']
    wildcard_segment: Literal['MAJOR', 'MINOR', 'MICRO', 'NONE'] = 'NONE'
    target_extra_name: Optional[str] = None  # NEW: name of the extra this requirement depends on
    raw: Optional[str] = None