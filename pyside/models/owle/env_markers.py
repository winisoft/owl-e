from dataclasses import dataclass
from typing import Literal, Optional


@dataclass
class EnvMarkerExpression:
    id: Optional[int]
    requirement_id: int
    parent_id: Optional[int]
    mode: Literal['COMPARATIVE', 'LOGICAL']

@dataclass
class EnvMarkerComparison:
    id: Optional[int]
    expression_id: int
    variable: Optional[
        Literal[
            'python_version', 'python_full_version', 'os_name', 'sys_platform',
            'platform_machine', 'platform_python_implementation', 'platform_release',
            'platform_system', 'platform_version', 'implementation_name',
            'implementation_version', 'extra'
        ]
    ] = None
    operator: Literal['==', '!=', '<', '<=', '>', '>=', 'in', 'not in']
    value: str


@dataclass
class EnvMarkerLogical:
    id: Optional[int]
    expression_id: int
    operator: Literal['AND', 'OR']
    left_child_id: int
    right_child_id: int
