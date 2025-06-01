from dataclasses import dataclass
from typing import Literal


@dataclass
class MarkerExpr:
    """Base class for any marker expression node."""
    pass


@dataclass
class MarkerComparison(MarkerExpr):
    variable: Literal[
        "python_version", "python_full_version", "os_name", "sys_platform",
        "platform_machine", "platform_python_implementation", "platform_release",
        "platform_system", "platform_version", "implementation_name", "implementation_version", "extra"
    ]
    operator: Literal["==", "!=", "<", "<=", ">", ">=", "in", "not in"]
    value: str


@dataclass
class MarkerLogical(MarkerExpr):
    operator: Literal["AND", "OR"]
    left: MarkerExpr
    right: MarkerExpr

    @property
    def children(self) -> list[MarkerExpr]:
        return [self.left, self.right]

# @dataclass
# class EnvMarkerExpression:
#     requirement_id: int
#     parent_id: Optional[int]
#     mode: Literal['COMPARATIVE', 'LOGICAL']
#     id: Optional[int] = None

# @dataclass
# class EnvMarkerComparison:
#     expression_id: int
#     operator: Literal['==', '!=', '<', '<=', '>', '>=', 'in', 'not in']
#     value: str
#     variable: Optional[
#         Literal[
#             'python_version', 'python_full_version', 'os_name', 'sys_platform',
#             'platform_machine', 'platform_python_implementation', 'platform_release',
#             'platform_system', 'platform_version', 'implementation_name',
#             'implementation_version', 'extra'
#         ]
#     ] = None
#     id: Optional[int] = None


# @dataclass
# class EnvMarkerLogical:
#     expression_id: int
#     operator: Literal['AND', 'OR']
#     left_child_id: int
#     right_child_id: int
#     id: Optional[int] = None




