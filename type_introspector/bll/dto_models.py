import ast
from dataclasses import dataclass
from types import ModuleType
from typing import Any, List, Optional


@dataclass
class IntrospectedModule:
    type: ModuleType
    name: str
    py_version_id: int

@dataclass
class ClassDiscovery:
    module: IntrospectedModule
    class_name: str
    obj: type
    docstring: str
    base_class_names: List[str]
    is_generic: bool
    py_version_id: int


@dataclass
class IntrospectedClass:
    module: IntrospectedModule
    class_name: str
    obj: type
    docstring: str
    is_generic: bool
    base_classes: List["IntrospectedClass"]
    py_version_id: int

@dataclass
class ClassResolution:
    module_type: ModuleType
    module_name: str
    class_name: str
    docstring: Optional[str]
    base_names: List[str]
    is_generic: bool
    obj: Optional[type]
    py_version_id: int


@dataclass
class DiscoveredAlias:
    module: IntrospectedModule
    alias_name: str
    obj: Optional[Any]
    target_expr: ast.expr
    py_version_id: int
    lineno: int


@dataclass
class IntrospectedUnionType:
    namespace: Optional[str] = None
    name: Optional[str] = None
    kind: str = "union"


@dataclass
class IntrospectedAlias:
    module: IntrospectedModule
    alias_name: str


@dataclass
class AliasDiscovery:
    module_name: str
    alias_name: str
    target_expr: ast.expr  # original RHS expression
    lineno: int
    