from dataclasses import dataclass
from typing import Optional, List, Literal


@dataclass
class PythonVersion:
    id: int
    is_supported: bool

@dataclass
class Version:
    id: Optional[int]
    major: int
    minor: Optional[int]
    micro: Optional[int]
    phase: Literal['final', 'alpha', 'beta', 'candidate'] = 'final'
    phase_number: Optional[int] = None
    is_post_release: bool = False
    post_number: Optional[int] = None
    is_dev_release: bool = False
    dev_number: Optional[int] = None
    version_string: Optional[str] = None
    has_overflow: bool = False


@dataclass
class OverflowVersion:
    id: Optional[int]
    version_id: int
    component_type: Literal['major', 'minor', 'micro', 'phase_number', 'post_number', 'dev_number']
    string_value: str
