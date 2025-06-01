from dataclasses import dataclass
from typing import Literal, Optional


@dataclass
class OverflowVersion:
    id: Optional[int]
    version_id: int
    component_type: Literal['major', 'minor', 'micro', 'phase_number', 'post_number', 'dev_number']
    string_value: str