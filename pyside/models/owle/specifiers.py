from dataclasses import dataclass
from typing import Literal, Optional


@dataclass
class RequiredDistSpecifier:
    id: Optional[int]
    dependent_dist: str
    dependent_version_id: int
    dependent_package_extra: Optional[str]
    target_distribution: str
    target_version_id: Optional[int]
    operator: Literal['LT', 'LE', 'NE', 'EQ', 'GE', 'GT', 'COMPATIBLE', 'EXACT']
    wildcard_segment: Literal['MAJOR', 'MINOR', 'MICRO', 'NONE'] = 'NONE'

@dataclass
class RequiredPythonSpecifier:
    id: Optional[int]
    distribution: str
    version_id: int
    operator: Optional[Literal['LT', 'LE', 'NE', 'EQ', 'GE', 'GT', 'COMPATIBLE', 'EXACT']] = None
    python_version_id: Optional[int] = None
    wildcard_segment: Literal['MAJOR', 'MINOR', 'MICRO', 'NONE'] = 'NONE'
