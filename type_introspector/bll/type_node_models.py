from typing import List, Literal, Optional, Type, Union, Set, Tuple
from hashlib import sha256

from bll.dto_models import IntrospectedModule


def module_namespace(module) -> Optional[str]:
    """
    The namespace a node belongs to, from whichever form the caller had to hand.

    Callers hold either an `IntrospectedModule` or - far more often - a bare `__module__` string
    off the object being described, and an object with no `__module__` yields the empty string.
    All three collapse to the same thing here, since `namespace` is what actually identifies a
    node in `dedup_hash`.
    """
    if module is None:
        return None
    return getattr(module, "name", module) or None


class TypeNode:

    def __init__(
        self,
        module: Optional[Union[IntrospectedModule, str]],
        obj: Optional[type],
        name: Optional[str],
        kind: Literal['base','typevar','union','instantiation','callable', 'alias'],
        is_generic: bool,
        py_version_id: int,
        namespace: Optional[str] = None,
        owning_obj: Optional[type] = None,
        docstring: Optional[str] = None
    ):
        self.module = module
        self.obj = obj
        self.name = name
        self.kind = kind
        self.is_generic = is_generic
        self.py_version_id = py_version_id
        # an explicit namespace wins; otherwise derive it from whatever `module` turned out to be
        self.namespace: Optional[str] = namespace if namespace else module_namespace(module)
        self.owning_obj = owning_obj
        self.docstring = docstring

    def dedup_hash(self) -> str:
        hash_str = f"{self.py_version_id}|{self.namespace or 'NULL'}|{self.name or 'NULL'}|{self.kind}|{int(self.is_generic)}"
        hash_bytes = hash_str.encode("utf-8")
        return sha256(hash_bytes).hexdigest()


class TypeVarTypeNode(TypeNode):
    def __init__(
            self,
            module: Optional[IntrospectedModule],
            obj: Optional[type],
            name: Optional[str],
            py_version_id: int
    ):
        super().__init__(
            module = module,
            obj = obj,
            name = name,
            kind = "typevar",
            is_generic = False,
            py_version_id = py_version_id
        )


class AliasTypeNode(TypeNode):
    def __init__(
            self,
            module: Optional[IntrospectedModule],
            obj: Optional[type],
            name: Optional[str],
            target_node_id: int,
            py_version_id: int
    ):
        super().__init__(
            module=module,
            obj=obj,
            name=name,
            kind="alias",
            is_generic=False,
            py_version_id=py_version_id
        )
        self.target_node_id = target_node_id


class CallableParameter:
    def __init__(
            self,
            name: Optional[str],
            docstring: Optional[str],
            position: int,
            is_vararg: bool,
            is_kwarg: bool,
            default_value: Optional[str],
            type_node: TypeNode
    ):
        self.name = name
        self.docstring = docstring
        self.position = position
        self.is_vararg = is_vararg
        self.is_kwarg = is_kwarg
        self.default_value = default_value
        self.type_node = type_node


class ScopedCallableTypeNode(TypeNode):
    def __init__(
            self,
            module: Optional[IntrospectedModule],
            obj: Optional[type],
            name: Optional[str],
            py_version_id: int,
            docstring: Optional[str],
            callable_params: Optional[List[CallableParameter]],
            parameter_type_node_ids: Optional[List[int]],
            return_type_obj: Optional[Type],
            return_type_id: int
    ):
        super().__init__(
            module = module,
            obj = obj,
            name = name,
            kind = "callable",
            is_generic = False,
            py_version_id = py_version_id
        )

        self.callable_params = callable_params if callable_params else []
        self.docstring: Optional[str] = docstring
        self.parameter_type_node_ids: Optional[List[int]] = parameter_type_node_ids
        self.return_type_obj: Optional[Type] = return_type_obj
        self.return_type_id: int = return_type_id


class ModuleScopeCallableTypeNode(ScopedCallableTypeNode):
    def __init__(
            self,
            module: IntrospectedModule,
            obj: Optional[type],
            name: str,
            py_version_id: int,
            docstring: Optional[str],
            callable_params: Optional[List[Tuple[int, CallableParameter]]],
            parameter_type_node_ids: Optional[List[int]],
            return_type_obj: Optional[Type],
            return_type_id: int
    ):
        super().__init__(
            module = module,
            obj = obj,
            name = name,
            py_version_id = py_version_id,
            docstring = docstring,
            callable_params=callable_params,
            parameter_type_node_ids = parameter_type_node_ids,
            return_type_obj = return_type_obj,
            return_type_id = return_type_id
        )

    def dedup_hash(self) -> str:
        parameter_id_list = self.parameter_type_node_ids if self.parameter_type_node_ids else []
        param_str = ",".join(str(pid) for pid in parameter_id_list)
        return_type = str(self.return_type_id or "NULL")
        hash_str = f"{self.py_version_id}|{param_str}|{return_type}"
        return sha256(hash_str.encode("utf-8")).hexdigest()


class ClassScopeCallableTypeNode(ScopedCallableTypeNode):
    def __init__(
            self,
            module: IntrospectedModule,
            obj: Optional[type],
            name: str,
            py_version_id: int,
            class_type: Type,
            class_name: str,
            callable_params: Optional[List[Tuple[int, CallableParameter]]],
            parameter_type_node_ids: Optional[List[int]],
            docstring: Optional[str],
            return_type_obj: Optional[Type],
            return_type_id: int,
            is_staticmethod: bool,
            is_classmethod: bool
    ):
        super().__init__(
            module = module,
            obj = obj,
            name = name,
            py_version_id = py_version_id,
            docstring = docstring,
            callable_params = callable_params,
            parameter_type_node_ids = parameter_type_node_ids,
            return_type_obj = return_type_obj,
            return_type_id = return_type_id
        )

        self.class_type: Type = class_type
        self.class_name: str = class_name
        self.is_staticmethod: bool = is_staticmethod
        self.is_classmethod: bool = is_classmethod


class UnionTypeNode(TypeNode):

    def __init__(
            self,
            py_version_id: int,
            member_hashes: Set[str],
            obj: Optional[type],
            member_type_node_ids: Set[int]
    ):
        super().__init__(
            module = None,
            obj = obj,
            name = None,
            kind = 'union',
            is_generic = False,
            py_version_id = py_version_id
        )
        self.member_hashes = member_hashes
        self.member_type_node_ids = member_type_node_ids


    def dedup_hash(self) -> str:
        hash_str = f"{self.py_version_id}|'NULL'|'NULL'|union|0|{sorted(self.member_hashes)}"
        hash_bytes = hash_str.encode("utf-8")
        return sha256(hash_bytes).hexdigest()


class InstantiatedTypeNode(TypeNode):
    """
    Represents a generic type instantiation (e.g., List[str], Dict[str, int]).

    The node refers to:
      - An origin type (e.g., 'list' or 'dict'), whose ID is already present in the DB/cache.
      - A list of argument type IDs, in positional order.
    """

    def __init__(
            self,
            obj: Optional[type],
            py_version_id: int,
            origin_type_node_id: int,
            arg_type_node_ids: List[int]
    ):
        super().__init__(
            module=None,
            obj=obj,
            name=None,
            kind="instantiation",
            is_generic=False,
            py_version_id=py_version_id
        )

        self.origin_type_node_id = origin_type_node_id
        self.arg_type_node_ids = arg_type_node_ids

    def dedup_hash(self) -> str:
        """
        Combines version, origin ID, and ordered arg IDs into a stable hash key.
        """
        arg_str = ",".join(str(i) for i in self.arg_type_node_ids)
        hash_str = f"{self.py_version_id}|instantiation|{self.origin_type_node_id}|{arg_str}"
        return sha256(hash_str.encode("utf-8")).hexdigest()