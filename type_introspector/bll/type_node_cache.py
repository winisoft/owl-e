import ast
import inspect
import sys
import types
from typing import Any, Dict, ForwardRef, List, Optional, Tuple, Type
import typing

from custom_errors import UnrepresentableTypeException
from bll.type_node_models import AliasTypeNode, InstantiatedTypeNode, TypeNode, TypeVarTypeNode, UnionTypeNode

from bll.callable_types import is_safe_callable
from dal.type_elements import insert_type_node, insert_type_union, insert_type_alias
from dal.typevar_elements import insert_typevar_bounds, insert_typevar_constraints, insert_typevar_variance

GENERIC_ALIAS_TYPES = (getattr(typing, "_GenericAlias", object), getattr(types, "GenericAlias", object))


def _is_generic_class(cls: type) -> bool:
    """ Determines if the class is a subclass of typing.Generic *without* being a parameterized instantiation. """
    try:
        return issubclass(cls, typing.Generic)
    except TypeError:
        return False


class TypeNodeCache:
    
    def __init__(self, py_version_id: int, cursor):
        self.cursor = cursor
        self.py_version_id = py_version_id
        self._by_hash: Dict[str, Tuple[int, TypeNode]] = {}
        self.by_id: Dict[int, TypeNode] = {}


    def register(self, node: TypeNode) -> int:
        h = node.dedup_hash()
        if h in self._by_hash:
            return self._by_hash[h][0]
        node_id = insert_type_node(self.cursor, node)
        self._by_hash[h] = (node_id, node)
        self.by_id[node_id] = node

        if isinstance(node, TypeVarTypeNode):
            self.populate_typevar_ancillary_types(self.cursor, typevars=[(node_id, node)])

        if isinstance(node, UnionTypeNode):
            insert_type_union(self.cursor, node_id, node.member_type_node_ids)

        if isinstance(node, AliasTypeNode):
            insert_type_alias(self.cursor, node_id, node.target_node_id)

        if isinstance(node, InstantiatedTypeNode):
            from bll.type_nodes import populate_type_inst_and_type_inst_params
            populate_type_inst_and_type_inst_params(self.cursor, (node_id, node))
        return node_id


    def populate_typevar_ancillary_types(self, cursor, typevars: List[Tuple[int, TypeVarTypeNode]]) -> "TypeNodeCache":

        for (_, typevar) in typevars:
            for typevar_id, typevar_node in typevars:
                if isinstance(typevar_node, TypeVarTypeNode):
                    if typevar.dedup_hash() == typevar_node.dedup_hash():
                        continue

                    if typevar_node.obj:

                        if hasattr(typevar_node.obj, "__bound__"):
                            try:
                                bound_id, _ = self.infer_correct_node_type(obj=typevar_node.obj.__bound__, owning_obj=typevar_node.obj)
                            except UnrepresentableTypeException:
                                continue

                            insert_typevar_bounds(cursor, typevar_id, bound_id)

                        if hasattr(typevar_node.obj, "__constraints__"):
                            for constraint in typevar_node.obj.__constraints__:
                                try:
                                    constraint_id, _ = self.infer_correct_node_type(obj=constraint, owning_obj=typevar_node.obj)
                                except UnrepresentableTypeException:
                                    continue

                                insert_typevar_constraints(cursor, typevar_id, constraint_id)

                        if getattr(typevar_node.obj, "__covariant__", False):
                            insert_typevar_variance(cursor, typevar_id, "covariant")
                        elif getattr(typevar_node.obj, "__contravariant__", False):
                            insert_typevar_variance(cursor, typevar_id, "contravariant")
        
        return self


    def get_or_create(
        self,
        obj: Any,
        owning_obj: Optional[Any] = None,
        is_callable_return_or_param: bool = False
    ) -> Tuple[int, TypeNode]:
        node = self.infer_correct_node_type(obj=obj, owning_obj=owning_obj, is_callable_return_or_param=is_callable_return_or_param)
        return self.register(node), node


    def infer_correct_node_type(
        self,
        obj: Any,
        owning_obj: Optional[type] = None,
        is_callable_return_or_param: bool = False
    ) -> TypeNode:
        """
        Dispatches inference to the correct specialized routine based on the type of `obj`.

        This function determines what sort of `TypeNode` to construct based on the structure
        and type of the given Python object. In doing so, it applies a combination of:
        - rejection criteria (e.g. unannotated or malformed types),
        - canonicalization logic (e.g. `ForwardRef` resolution), and
        - instantiation mechanics (e.g. turning generic aliases into parametric nodes).

        The control flow is strict and exclusionary, designed to prevent invalid or ambiguous
        type expressions from entering the database.

        Args:
            obj: The Python object to infer as a TypeNode.
            owning_obj: The object this type annotation is a part of, if any.
            is_callable_return_or_param: Whether this type is part of a callable signature.

        Returns:
            A fully-resolved TypeNode, or subclass thereof.

        Raises:
            ValueError: For incomplete or contextually invalid base type references.
            UnrepresentableTypeException: For explicitly unsupported or disallowed objects.
        """

        # Skip objects coming from disqualified callables
        if is_callable_return_or_param and not is_safe_callable(owning_obj):
            raise UnrepresentableTypeException(f"Cannot represent callable: {obj}")

        # None implies an unannotated or unavailable type — in non-callable contexts that's unacceptable
        if not is_callable_return_or_param and obj is None:
            raise ValueError(
                f"Unannotated type cannot produce a TypeNode. module: {getattr(owning_obj, '__module__', '')} function: {getattr(owning_obj, '__name__', '')}"
            )

        # String annotations and Ellipsis are not valid type objects by themselves
        if isinstance(obj, (str, list)) or obj is Ellipsis:
            raise UnrepresentableTypeException(f"Unrepresentable type form: {obj!r}")

        # Un-evaluated forward references like `ForwardRef('MyClass')`
        if isinstance(obj, ForwardRef):
            return self._handle_unresolved_forwardref(obj, owning_obj)

        # Runtime function references (e.g., lambda or def f()) should never be used as a type
        if isinstance(obj, types.FunctionType) or inspect.isroutine(obj):
            raise UnrepresentableTypeException("Plain function objects are not representable as type nodes")

        # String-based forward references — allowed in some annotations but must be resolved to a real type
        if isinstance(obj, str):
            return self._resolve_string_forward_ref(obj, owning_obj)
        
        if isinstance(obj, int):
            return TypeNode(obj=type(int), namespace="builtins", kind="base", py_version_id=self.py_version_id, name="int", is_generic=False, module=getattr(owning_obj, '__module__', ''))
        
        if isinstance(obj, float):
            return TypeNode(obj=type(float), namespace="builtins", kind="base", py_version_id=self.py_version_id, name="float", is_generic=False, module=getattr(owning_obj, '__module__', ''))

        # Type variables used in generic classes or functions
        if isinstance(obj, typing.TypeVar):
            return self._typevar_node(obj, owning_obj)

        # Special case for `None` which is its own type
        if obj is type(None):
            return TypeNode(obj=obj, kind="base", py_version_id=self.py_version_id, name="NoneType", is_generic=False, owning_obj=owning_obj, module=getattr(owning_obj, '__module__', ''))

        # Special case for typing.Any
        if obj is typing.Any:
            return TypeNode(obj=obj, kind="base", py_version_id=self.py_version_id, name="Any", is_generic=False, owning_obj=owning_obj, module=getattr(owning_obj, '__module__', ''))

        # Union types in 3.10+ use a dedicated syntax tree (`X | Y`) tracked as UnionType
        if hasattr(types, "UnionType") and isinstance(obj, types.UnionType):
            return self._union_type_node(obj, owning_obj)

        # Special typing constructs like `typing.Optional` or `typing.Literal`
        if isinstance(obj, typing._SpecialForm):
            return TypeNode(obj=typing._SpecialForm, kind="base", py_version_id=self.py_version_id, name=str(obj), is_generic=False, owning_obj=owning_obj, module=getattr(owning_obj, '__module__', ''))

        # Generic aliases like List[int], Dict[str, float], etc.
        if isinstance(obj, GENERIC_ALIAS_TYPES):
            return self._instantiated_type_node(obj, owning_obj)

        # Classes or built-in types with a name
        if isinstance(obj, type):
            return self._base_type_node(obj, owning_obj)

        # It is an outright error to have a module object here
        if inspect.ismodule(obj):
            raise UnrepresentableTypeException(f"Got module instead of type: {obj}")

        # All other objects are unsupported
        raise UnrepresentableTypeException(f"Unsupported type reference: {type(obj)} :: {repr(obj)}")


    def find(
        self,
        *,
        kind: Optional[str] = None,
        name: Optional[str] = None,
        namespace: Optional[str] = None,
        is_generic: Optional[bool] = None,
        node_type: Optional[Type[TypeNode]] = None
    ) -> List[Tuple[int, TypeNode]]:
        """
        Filters the cache by one or more optional TypeNode properties.

        Args:
            kind: If specified, only TypeNodes with this kind will be returned.
            name: If specified, only TypeNodes with this name will be returned.
            is_generic: If specified, filters nodes by their `is_generic` flag.
            node_type: If specified, restricts to instances of the given TypeNode subclass.

        Returns:
            A list of (id, node) tuples for all matching entries.
            :param kind:
            :param name:
            :param node_type:
            :param is_generic:
            :param namespace:
        """
        results = []
        for node_id, node in self.by_id.items():
            if kind is not None and getattr(node, "kind", None) != kind:
                continue
            if name is not None and getattr(node, "name", None) != name:
                continue
            if is_generic is not None and getattr(node, "is_generic", None) != is_generic:
                continue
            if namespace is not None and getattr(node, "namespace", None) != namespace:
                continue
            if node_type is not None and not isinstance(node, node_type):
                continue
            results.append((node_id, node))
        return results


    def _handle_unresolved_forwardref(self, obj: ForwardRef, owning_obj: Any) -> TypeNode:
            """
            Treats `ForwardRef` objects that were never evaluated.

            These are often found in `__annotations__` before evaluation. Rather than reject them,
            we store the text form in an `AliasTypeNode`, and revisit the mapping at a later pass
            once more type information is known.
            """
            return AliasTypeNode(
                obj=None,
                py_version_id=self.py_version_id,
                name=obj.__forward_arg__,
                target_node_id=-1,
                owning_obj=owning_obj
            )


    def _resolve_string_forward_ref(self, ref_text: str, owning_obj: Any) -> TypeNode:
        """
        Evaluates string-encoded forward references like "SomeType" into usable TypeNode structures.

        - Resolves using module globals for the `owning_obj`, if provided.
        - Parses the reference to confirm all names were resolved.
        - If successful, recursively re-infers the resolved type.

        Raises:
            UnrepresentableTypeException: if resolution or parsing fails.
        """
        try:
            ref = ForwardRef(ref_text)
            globalns = {}
            if owning_obj:
                mod = inspect.getmodule(owning_obj)
                if mod:
                    globalns = vars(mod)

            evaluated = (
                ref._evaluate(globalns=globalns, localns=None, recursive_guard=frozenset())
                if sys.version_info >= (3, 11)
                else ref._evaluate(globalns=globalns, localns=None, recursive_guard=frozenset())
            )

            parsed = ast.parse(ref_text, mode="eval")
            used_names = {n.id for n in ast.walk(parsed) if isinstance(n, ast.Name)}
            unresolved = [n for n in used_names if n not in globalns]
            if unresolved:
                raise UnrepresentableTypeException(f"Unresolved names in forward ref: {unresolved} in {ref_text!r}")

            return self.infer_correct_node_type(obj=evaluated, owning_obj=owning_obj)

        except UnrepresentableTypeException:
            raise
        except Exception as e:
            raise UnrepresentableTypeException(f"Could not resolve forward ref {ref_text!r}: {e}") from e


    def _typevar_node(self, obj: typing.TypeVar, owning_obj: Any) -> TypeNode:
        """
        Constructs a TypeNode representing a generic type variable.
        """
        return TypeVarTypeNode(
            obj=type(obj),
            py_version_id=self.py_version_id,
            name=obj.__name__,
            module=None
        )


    def _union_type_node(self, obj: Any, owning_obj: Any) -> TypeNode:
        """
        Represents a union type (e.g., X | Y or Union[X, Y]) as a UnionTypeNode.

        The member types are recursively resolved.
        """
        member_ids = [self.get_or_create(arg, owning_obj)[0] for arg in obj.__args__]
        return UnionTypeNode(py_version_id=self.py_version_id, obj=obj, member_type_node_ids=member_ids)


    def _instantiated_type_node(self, obj: Any, owning_obj: Any) -> TypeNode:
        """
        Handles parametrized generic aliases like List[int], Dict[str, float], etc.

        Separates origin and arguments and resolves them into a normalized form.
        """
        origin = typing.get_origin(obj)
        args = typing.get_args(obj)
        if origin is None:
            raise ValueError(f"Cannot resolve origin for {obj}")
        origin_id, _ = self.get_or_create(origin, owning_obj)
        arg_ids = [self.get_or_create(arg, owning_obj)[0] for arg in args]
        return InstantiatedTypeNode(obj, self.py_version_id, origin_id, arg_ids)


    def _base_type_node(self, obj: type, owning_obj: Any) -> TypeNode:
        """
        Basic named type (like `int`, `str`, or user-defined class) with no generics.

        These are stored directly, assuming they have a valid `__name__`.
        """
        name = getattr(obj, "__name__", None)
        if not name:
            raise ValueError(f"Type missing __name__: {obj}")
        return TypeNode(obj=obj, kind="base", py_version_id=self.py_version_id, name=name, is_generic=_is_generic_class(obj), owning_obj=owning_obj, module=getattr(owning_obj, '__module__', ''))

    def __getitem__(self, node_id: int) -> Optional[TypeNode]:
        if node_id:
            return self.by_id[node_id]
        return None

