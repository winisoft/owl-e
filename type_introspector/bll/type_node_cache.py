import ast
import inspect
import sys
import types
from typing import Any, Dict, List, Optional, Tuple, Type
try:
    from typing import ForwardRef
except ImportError:  # 3.6 spells it with a leading underscore
    from typing import _ForwardRef as ForwardRef
import typing

from custom_errors import UnrepresentableTypeException
from bll.type_node_models import AliasTypeNode, InstantiatedTypeNode, TypeNode, TypeVarTypeNode, UnionTypeNode

from bll.callable_types import is_safe_callable
from dal.type_elements import insert_type_node, insert_type_union, insert_type_alias
from dal.typevar_elements import insert_typevar_bounds, insert_typevar_constraints, insert_typevar_variance

# Absent members are dropped rather than defaulted to `object`: `isinstance(x, object)` is true of
# everything, so on 3.8 - where `types.GenericAlias` does not exist - that default sent every
# object down the parameterised-generic path, including plain classes like `abc.ABC`, which have
# no origin to resolve. `isinstance(x, ())` is simply False, which is the intended "no match".
GENERIC_ALIAS_TYPES = tuple(
    alias for alias in (getattr(typing, "_GenericAlias", None), getattr(types, "GenericAlias", None))
    if alias is not None
)


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
        """
        A TypeVar's bound, constraints and variance.

        Previously this walked `typevars` twice and skipped whenever the two entries had the same
        dedup hash - which, given it is called with a single freshly-registered node, was always.
        Nothing was ever written.

        Variance is recorded for every TypeVar including the invariant default, since "invariant"
        is a real answer rather than an absent one, and the column enumerates it.
        """
        for typevar_id, typevar_node in typevars:
            if not isinstance(typevar_node, TypeVarTypeNode) or not typevar_node.obj:
                continue

            obj = typevar_node.obj

            # Read these off a genuine TypeVar only. On the `TypeVar` class itself - and on a few
            # look-alikes reached through `dir()` - `__bound__` and `__constraints__` resolve to
            # the raw getset descriptors rather than to a value or a tuple, which is what the
            # "'getset_descriptor' object is not iterable" warnings were.
            if not isinstance(obj, typing.TypeVar):
                continue

            bound = getattr(obj, "__bound__", None)
            if bound is not None:
                try:
                    bound_id, _ = self.get_or_create(bound, obj)
                    insert_typevar_bounds(cursor, typevar_id, bound_id)
                except (UnrepresentableTypeException, ValueError):
                    pass

            constraints = getattr(obj, "__constraints__", ()) or ()
            for constraint in (constraints if isinstance(constraints, (tuple, list)) else ()):
                try:
                    constraint_id, _ = self.get_or_create(constraint, obj)
                    insert_typevar_constraints(cursor, typevar_id, constraint_id)
                except (UnrepresentableTypeException, ValueError):
                    pass

            if getattr(obj, "__covariant__", False):
                variance = "covariant"
            elif getattr(obj, "__contravariant__", False):
                variance = "contravariant"
            else:
                variance = "invariant"
            insert_typevar_variance(cursor, typevar_id, variance)

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

        # String annotations are not valid type objects by themselves
        if isinstance(obj, (str, list)):
            raise UnrepresentableTypeException(f"Unrepresentable type form: {obj!r}")

        # `...` is meaningless as an annotation on its own, but it is a real and common *argument*:
        # `Callable[..., int]` and `Tuple[int, ...]` both yield it from `get_args`, and the two are
        # not the same type as `Callable[[], int]` and `Tuple[int]`. Dropping it would silently
        # collapse that distinction, so it gets a canonical node of its own and dedups like any
        # other. It only ever reaches here from an argument position.
        if obj is Ellipsis:
            return TypeNode(
                obj=obj, kind="base", py_version_id=self.py_version_id, name="...",
                is_generic=False, namespace="builtins", owning_obj=owning_obj, module=None
            )

        # Un-evaluated forward references like `ForwardRef('MyClass')`
        if isinstance(obj, ForwardRef):
            return self._handle_unresolved_forwardref(obj, owning_obj)

        # Runtime function references (e.g., lambda or def f()) should never be used as a type
        if isinstance(obj, types.FunctionType) or inspect.isroutine(obj):
            raise UnrepresentableTypeException("Plain function objects are not representable as type nodes")

        # String-based forward references — allowed in some annotations but must be resolved to a real type
        if isinstance(obj, str):
            return self._resolve_string_forward_ref(obj, owning_obj)
        
        # These branches name the type they describe, so the namespace is that type's own home -
        # `builtins` or `typing` - never the module the value happened to be reached through.
        # Using the owning object's `__module__` gave `typing.Any` a separate node per importing
        # module: roughly 400 of them per interpreter, none of which could ever dedup, because
        # `namespace` is half of `dedup_hash`. They also stored `type(int)` and
        # `typing._SpecialForm` - the metaclass and the base class - rather than the thing itself.
        if isinstance(obj, int):
            return TypeNode(obj=int, namespace="builtins", kind="base", py_version_id=self.py_version_id, name="int", is_generic=False, module=None)
        
        if isinstance(obj, float):
            return TypeNode(obj=float, namespace="builtins", kind="base", py_version_id=self.py_version_id, name="float", is_generic=False, module=None)

        # Type variables used in generic classes or functions
        if isinstance(obj, typing.TypeVar):
            return self._typevar_node(obj, owning_obj)

        # Special case for `None` which is its own type
        if obj is type(None):
            return TypeNode(obj=obj, kind="base", py_version_id=self.py_version_id, name="NoneType", is_generic=False, owning_obj=owning_obj, namespace="builtins", module=None)

        # Special case for typing.Any
        if obj is typing.Any:
            return TypeNode(obj=obj, kind="base", py_version_id=self.py_version_id, name="Any", is_generic=False, owning_obj=owning_obj, namespace="typing", module=None)

        # Union types in 3.10+ use a dedicated syntax tree (`X | Y`) tracked as UnionType
        if hasattr(types, "UnionType") and isinstance(obj, types.UnionType):
            return self._union_type_node(obj, owning_obj)

        # Special typing constructs like `typing.Optional` or `typing.Literal`
        if isinstance(obj, typing._SpecialForm):
            return TypeNode(obj=obj, kind="base", py_version_id=self.py_version_id, name=str(obj), is_generic=False, owning_obj=owning_obj, namespace="typing", module=None)

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
                module=None,
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

            # `ForwardRef._evaluate` is private and its signature has moved: `type_params` was
            # added in 3.13 and `recursive_guard` became keyword-only. The previous version check
            # had two identical arms, so it asserted a difference it did not implement. Try the
            # newest shape first and fall back, rather than pin behaviour to a version test that
            # will be wrong again at the next release.
            evaluated = None
            for attempt in (
                lambda: ref._evaluate(globalns, None, type_params=(), recursive_guard=frozenset()),
                lambda: ref._evaluate(globalns=globalns, localns=None, recursive_guard=frozenset()),
                lambda: ref._evaluate(globalns, None, frozenset()),
            ):
                try:
                    evaluated = attempt()
                    break
                except TypeError:
                    continue
            else:
                raise UnrepresentableTypeException(
                    f"No supported ForwardRef._evaluate signature on this interpreter: {ref_text!r}"
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
        # The TypeVar itself, not its class. `type(obj)` is `typing.TypeVar` for every type
        # variable in existence, which discards which one this is and leaves `__bound__` /
        # `__constraints__` resolving to class-level getset descriptors instead of values - so no
        # bound, constraint or variance could ever be read from it.
        return TypeVarTypeNode(
            obj=obj,
            py_version_id=self.py_version_id,
            name=obj.__name__,
            module=getattr(obj, "__module__", None)
        )


    def _union_type_node(self, obj: Any, owning_obj: Any) -> TypeNode:
        """
        Represents a union type (e.g., X | Y or Union[X, Y]) as a UnionTypeNode.

        The member types are recursively resolved.
        """
        members = [self.get_or_create(arg, owning_obj) for arg in obj.__args__]
        member_ids = [node_id for node_id, _ in members]
        # `dedup_hash` is built from these, which is what makes `Union[int, str]` discovered in two
        # places resolve to a single node rather than two indistinguishable ones.
        member_hashes = {node.dedup_hash() for _, node in members}
        return UnionTypeNode(
            py_version_id=self.py_version_id, member_hashes=member_hashes,
            obj=obj, member_type_node_ids=member_ids
        )


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

        # A Callable's parameter list arrives as a genuine Python list:
        # `get_args(Callable[[str], int])` is `([str], int)`. Flattened in order, so the arguments
        # read parameters-then-return - the same shape `Callable[..., int]` already produces, where
        # the placeholder is one node and the return follows it. A bare list is still not a type,
        # which is why it stays rejected in `infer_correct_node_type`; it is only meaningful here.
        arg_ids = []
        for arg in args:
            if isinstance(arg, list):
                arg_ids.extend(self.get_or_create(a, owning_obj)[0] for a in arg)
            else:
                arg_ids.append(self.get_or_create(arg, owning_obj)[0])

        return InstantiatedTypeNode(obj, self.py_version_id, origin_id, arg_ids)


    def _base_type_node(self, obj: type, owning_obj: Any) -> TypeNode:
        """
        Basic named type (like `int`, `str`, or user-defined class) with no generics.

        These are stored directly, assuming they have a valid `__name__`.
        """
        name = getattr(obj, "__name__", None)
        if not name:
            raise ValueError(f"Type missing __name__: {obj}")
        # The namespace is where the type is *defined*, taken from the type itself - not from
        # whatever object we happened to reach it through. Reading `__module__` off the owning
        # object meant an `IntrospectedModule` answered with its own defining module, so 1,959
        # standard-library types were filed under `bll.dto_models`, this tool's internals. It also
        # makes `os.PathLike` one node whether it is found via `os` or `posixpath`, since
        # `namespace` is half of `dedup_hash`.
        namespace = getattr(obj, "__module__", None) or getattr(owning_obj, "name", None)
        return TypeNode(
            obj=obj, kind="base", py_version_id=self.py_version_id, name=name,
            is_generic=_is_generic_class(obj), owning_obj=owning_obj,
            namespace=namespace, module=None
        )

    def __getitem__(self, node_id: int) -> Optional[TypeNode]:
        if node_id:
            return self.by_id[node_id]
        return None


    def __iter__(self):
        """
        Every registered node, as `(node_id, node)`.

        Without this, `for a, b in cache` silently falls back to the legacy sequence protocol and
        calls `__getitem__(0)` - which returns None here, since `__getitem__` takes a node id and
        treats 0 as absent. Callers iterating the cache got `cannot unpack non-iterable NoneType`
        rather than anything suggesting the real cause.
        """
        return iter(list(self.by_id.items()))


    def __len__(self) -> int:
        return len(self.by_id)

