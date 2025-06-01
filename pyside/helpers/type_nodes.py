from db_ops.type_elements import get_or_create_type_node, insert_type_node_relation

import typing_inspect

import inspect
import typing
from typing import Any, Dict, List, Tuple

from db_ops.type_elements import insert_instatiation_parameters


def populate_type_nodes(cursor, py_version_id: int, type_objects: List[Any]) -> Dict[Any, int]:
    """
    Inserts base definitions, typevars, unions, generics, callables, literals, and aliases into type_nodes.
    Returns a mapping from Python object to its type_nodes ID.
    """
    cache: Dict[Tuple, int] = {}
    object_to_node_id: Dict[Any, int] = {}
    deferred_base_links: List[Tuple[int, Any]] = []

    # First pass: create type_nodes entries with base_id = None
    for obj in type_objects:
        module_name = getattr(obj, "__module__", None)
        obj_name = getattr(obj, "__name__", None)

        if isinstance(obj, typing.TypeVar):
            kind = "typevar"
            alias_name = obj.__name__
            namespace = None
            name = None
            is_generic = False

        elif typing_inspect.is_union_type(obj):
            kind = "union"
            alias_name = None
            namespace = None
            name = None
            is_generic = False

        elif typing_inspect.is_generic_type(obj) and not isinstance(obj, typing.TypeVar):
            origin = typing_inspect.get_origin(obj)
            if origin is None:
                kind = "base"
                alias_name = None
                namespace = module_name
                name = obj_name
                is_generic = True
            else:
                kind = "instantiation"
                alias_name = None
                namespace = None
                name = None
                is_generic = False
                deferred_base_links.append((obj, origin))

        elif inspect.isclass(obj):
            kind = "base"
            alias_name = None
            namespace = module_name
            name = obj_name
            is_generic = issubclass(obj, typing.Generic) or typing_inspect.is_generic_type(obj)
            deferred_base_links.append((obj, obj.__bases__[0] if obj.__bases__ else None))

        elif inspect.isfunction(obj) or inspect.ismethod(obj):
            kind = "callable"
            alias_name = None
            namespace = module_name
            name = obj_name
            is_generic = False

        else:
            continue

        node_id = get_or_create_type_node(
            cursor, cache, namespace, name, alias_name, kind, is_generic, py_version_id=py_version_id
        )
        object_to_node_id[obj] = node_id


    return object_to_node_id


def populate_generic_instantiations(cursor,
                                    object_to_node_id: Dict[Any, int]) -> None:
    """
    For each instantiation type (e.g. List[int]), set its base_node_id and populate instantiation_parameters.
    """
    for obj, node_id in object_to_node_id.items():
        if typing_inspect.is_generic_type(obj) and not isinstance(obj, typing.TypeVar):
            origin = typing_inspect.get_origin(obj)
            if origin is not None:
                # Get the base_node_id (origin should have been inserted in first pass)
                base_node_id = object_to_node_id.get(origin)
                if base_node_id is None:
                    continue  # origin not found; skip

                # insert type_node_relation to associate this subclassing
                insert_type_node_relation(cursor, base_node_id, node_id)

                # Now insert instantiation parameters
                args = typing_inspect.get_args(obj, evaluate=True) or ()
                for idx, arg in enumerate(args):
                    # For simple types, key by name; but if arg is typing constructs, get node_id from cache
                    member_node_id = object_to_node_id.get(arg)
                    if member_node_id is None:
                        # Attempt to match by name if not directly in the cache
                        member_node_id = object_to_node_id.get(type(arg)) or object_to_node_id.get(arg)
                    if member_node_id:
                        insert_instatiation_parameters(node_id, idx, member_node_id)

