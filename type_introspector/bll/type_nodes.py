import builtins
import datetime
import decimal
import sys
from types import ModuleType
from typing import ForwardRef, Optional, Tuple, Any, Dict, List
import logging
import typing
import inspect

from dal.type_elements import insert_type_node_relation
from dal.type_generic_elements import insert_class_generic_params, insert_type_instantiation, insert_type_instantiation_arg
from bll.dto_models import IntrospectedClass
from bll.module_introspect import is_generic_class
from bll.type_node_cache import TypeNodeCache
from bll.type_node_models import InstantiatedTypeNode, TypeNode

from bll.callable_types import introspect_callable
from bll.typevars import populate_type_nodes_of_typevars

logger = logging.getLogger(__name__)



def classify_module(module: ModuleType) -> Dict[str, List[Tuple[str, type[Any]]]]:
    base_types = []
    generic_types = []
    callables = []
    modules = []
    others = []

    for name in dir(module):
        try:
            obj = getattr(module, name)
        except Exception:
            continue

        if inspect.isclass(obj):
            # Some classes are generic in 3.9+ because they have __class_getitem__
            if hasattr(obj, "__class_getitem__"):
                generic_types.append((name, obj))
            else:
                base_types.append((name, obj))
        elif inspect.isfunction(obj) or inspect.isbuiltin(obj):
            callables.append((name, obj))
        elif inspect.ismodule(obj):
            modules.append((name, obj))
        else:
            others.append((name, obj))

    return {
        "base_types": base_types,
        "generic_types": generic_types,
        "callables": callables,
        "modules": modules,
        "others": others
    }


def populate_base_type_nodes(
    resolved_classes: List[IntrospectedClass],
    type_node_cache: TypeNodeCache,
) -> TypeNodeCache:
    """
    Inserts base definitions. Assumes type_objects are known, pre-inspected and pre-qualified to be persisted.
    """
    for rc in resolved_classes:
        
        if rc.obj is None:
            continue

        type_node_cache.get_or_create(rc.obj, rc.module)

    return type_node_cache


def populate_ad_hoc_module_type_nodes(py_version_id: int, type_node_cache: TypeNodeCache) -> TypeNodeCache:
    type_node_cache = populate_ad_hoc_type_nodes(builtins, classify_module(builtins), py_version_id, type_node_cache)
    type_node_cache = populate_ad_hoc_type_nodes(typing, classify_module(typing), py_version_id, type_node_cache)
    type_node_cache = populate_ad_hoc_type_nodes(datetime, classify_module(datetime), py_version_id, type_node_cache)
    type_node_cache = populate_ad_hoc_type_nodes(decimal, classify_module(decimal), py_version_id, type_node_cache)

    return type_node_cache


def populate_ad_hoc_type_nodes(module: ModuleType, module_types: Dict[str, List[Tuple[str, type[Any]]]], py_version_id: int, type_node_cache: TypeNodeCache) -> TypeNodeCache:
    for (name, obj) in module_types['base_types']:
        node = TypeNode(
            kind="base",
            py_version_id=py_version_id,
            name=name,
            is_generic=False,
            owning_obj=module,
            obj=obj,
            namespace=module.__name__,
            docstring=inspect.getdoc(obj)
        )
        type_node_cache.register(node)
    for (name, obj) in module_types['generic_types']:
        node = TypeNode(
            namespace=module.__name__,
            kind="base",
            py_version_id=py_version_id,
            name=name,
            is_generic=True,
            owning_obj=module,
            obj=obj,
            docstring=inspect.getdoc(obj)
        )
        type_node_cache.register(node)

    type_node_cache = populate_type_nodes_of_typevars(modules=module_types["modules"], cache=type_node_cache)

    for (name, obj) in module_types['callables']:
        introspect_callable(
            obj=obj,
            name=name,
            owning_obj=module,
            globalns=vars(sys.modules[module.name]),
            py_version_id=py_version_id,
            type_node_cache=type_node_cache,
            class_name=None
        )


    return type_node_cache


def populate_generic_instantiations(
    cursor,
    object_to_node_id: Dict[Any, int]
    #, class_generic_params: Dict[Any, List[Any]],
) -> None:
    """
    For each known generic instantiation (from AST), record its origin relation and populate
    instantiation parameters.

    Note: this records origin as a type_node_relations row of kind 'instantiation' — NOT as a
    type_nodes.base_node_id column write (that column is dead; see the migration notes). The
    same origin fact is recorded independently in type_instantiations.origin_node_id.
    Requires: `class_generic_params` mapping classes to the TypeVars they were declared with.
    """
    for obj, node_id in object_to_node_id.items():
        # Skip anything not a typing instantiation
        origin = getattr(obj, "__origin__", None)
        if origin is None or origin not in object_to_node_id:
            continue

        origin_node_id = object_to_node_id[origin]
        insert_type_node_relation(
            cursor,
            parent_node_id=origin_node_id,
            child_node_id=node_id,
            relation_kind="instantiation",
        )

        args = getattr(obj, "__args__", ())
        for idx, arg in enumerate(args):
            member_node_id = object_to_node_id.get(arg)
            if member_node_id:
                insert_class_generic_params(
                    cursor, node_id=node_id, typevar_node_id=member_node_id, idx=idx
                )


def populate_type_inst_and_type_inst_params(cursor, node: Tuple[int, InstantiatedTypeNode]):

    insert_type_instantiation(cursor, instantiation_node_id = node[0], origin_node_id = node[1].origin_type_node_id)

    for i, param_id in enumerate(node[1].arg_type_node_ids):
        insert_type_instantiation_arg(cursor, instantiation_node_id = node[0], arg_node_id = param_id, position = i)


def populate_type_schema_refs(cursor, type_node_cache: TypeNodeCache):
    import inspect
    from dal.type_elements import insert_type_schema_ref
    from custom_errors import UnrepresentableTypeException
    
    # Get all base type nodes that represent classes
    base_type_nodes = type_node_cache.find(kind="base")
    
    for class_node_id, class_node in base_type_nodes:
        if not hasattr(class_node, 'obj') or class_node.obj is None:
            continue
            
        # Only process actual class objects
        if not isinstance(class_node.obj, type):
            continue
            
        class_obj = class_node.obj
        position = 0
        
        # Get all members of the class
        for member_name, member_obj in inspect.getmembers(class_obj):
            # Skip private/internal members
            if member_name.startswith('_'):
                continue
                
            try:
                # Determine member kind and get type information
                member_kind = None
                member_type_node_id = None
                
                if inspect.ismethod(member_obj) or inspect.isfunction(member_obj):
                    member_kind = 'method'
                    # For methods, we don't store the type_node_id as it's handled by callable ownership
                    
                elif inspect.isdatadescriptor(member_obj) and hasattr(member_obj, '__get__'):
                    member_kind = 'property'
                    # Try to get the property's return type if available
                    if hasattr(member_obj, 'fget') and member_obj.fget:
                        try:
                            type_hints = inspect.get_annotations(member_obj.fget, eval_str=False)
                            if 'return' in type_hints:
                                member_type_node_id, _ = type_node_cache.get_or_create(
                                    type_hints['return'], class_obj
                                )
                        except Exception:
                            pass
                            
                elif hasattr(class_obj, '__annotations__') and member_name in class_obj.__annotations__:
                    # This is a type-annotated class variable
                    member_kind = 'classvar'
                    try:
                        annotation = class_obj.__annotations__[member_name]
                        member_type_node_id, _ = type_node_cache.get_or_create(annotation, class_obj)
                    except UnrepresentableTypeException:
                        pass
                        
                else:
                    # Regular attribute - try to infer type from the value
                    member_kind = 'attribute'
                    try:
                        member_type = type(member_obj)
                        member_type_node_id, _ = type_node_cache.get_or_create(member_type, class_obj)
                    except UnrepresentableTypeException:
                        pass
                
                # Insert the schema reference if we determined a valid member kind
                if member_kind:
                    insert_type_schema_ref(
                        cursor, 
                        class_node_id, 
                        member_name, 
                        member_kind, 
                        member_type_node_id, 
                        position
                    )
                    position += 1
                    
            except Exception:
                # Skip members that cause errors during introspection
                continue



def normalize_type_to_type_node(
    return_type_obj: Any,
    py_version_id: int
) -> Optional[TypeNode]:
    """
    Attempts to convert a type-like object (including ForwardRef, Union, TypeVar, etc.)
    into a normalized TypeNode for hash comparison.
    """
    try:
        # Case: no annotation
        if return_type_obj is None:
            return None

        # Case: '-> None'
        if return_type_obj is type(None):
            return TypeNode(namespace="builtins", name="NoneType", obj=None, kind="base", is_generic=False, py_version_id=py_version_id, module=None)

        # Case: ForwardRef('SomeType')
        if isinstance(return_type_obj, ForwardRef):
            # We'll treat this as an alias
            return TypeNode(None, None, return_type_obj.__forward_arg__, "alias", False, py_version_id)

        # Case: TypeVar
        if isinstance(return_type_obj, typing.TypeVar):
            return TypeNode(None, None, return_type_obj.__name__, "typevar", False, py_version_id)

        # Case: Union[int, str]
        if getattr(return_type_obj, "__origin__", None) is typing.Union:
            return TypeNode(None, None, None, "union", False, py_version_id)

        # TODO: typing.GenericAlias > python 3.9 ... typing._GenericAlias before that
        # Case: List[str], Dict[str, int], Optional[int], etc.
        if isinstance(return_type_obj, typing.GenericAlias):
            return TypeNode(None, None, None, "instantiation", False, py_version_id)

        # Case: Literal[...] or Annotated[...] or other typing._SpecialForm
        if isinstance(return_type_obj, typing._SpecialForm):
            return TypeNode(namespace="typing", obj=str(return_type_obj), name=None, kind="base", is_generic=False, py_version_id=py_version_id, module=None)

        # Case: Normal class type like int, str, CustomClass
        if inspect.isclass(return_type_obj):
            mod = getattr(return_type_obj, "__module__", None)
            name = getattr(return_type_obj, "__name__", None)
            if mod and name:
                return TypeNode(mod, name, None, "base", is_generic_class(return_type_obj), py_version_id)

    except Exception:
        pass

    return None

