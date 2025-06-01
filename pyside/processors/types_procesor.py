import builtins
import collections.abc
import sys
import types
import typing
from typing import Any, Dict, List, Set, Tuple
import logging
import inspect
import typing_inspect
import warnings
import argparse
from utils.db_connection import get_db_connection
from config import DB_NAME
from db_ops.type_elements import get_or_create_type_node
from db_ops.versions import get_id_from_python_versions
from helpers.type_misc import (
    populate_class_generic_params,
    populate_type_node_relations, 
    populate_type_schema_refs, 
    populate_typevar_bounds_constraints, 
    populate_union_members
)

logger = logging.getLogger(__name__)

# Suppress typing_inspect warnings for Python 3.11+
if sys.version_info >= (3, 11):
    warnings.filterwarnings("ignore", category=DeprecationWarning, module="typing_inspect")

def get_python_version_info() -> Tuple[int, int, int]:
    """Get the current Python version tuple."""
    return sys.version_info.major, sys.version_info.minor, sys.version_info.micro


def collect_builtin_types() -> List[Any]:
    """Collect all built-in types from the builtins module."""
    builtin_types = []
    
    for name in dir(builtins):
        if name.startswith('_'):
            continue
            
        obj = getattr(builtins, name)
        if isinstance(obj, type):
            builtin_types.append(obj)
    
    return builtin_types


def collect_types_module_types() -> List[Any]:
    """Collect relevant types from the types module."""
    types_objects = []
    
    for name in dir(types):
        if name.startswith('_'):
            continue
            
        obj = getattr(types, name)
        if isinstance(obj, type):
            types_objects.append(obj)
    
    return types_objects


def collect_collections_abc_types() -> List[Any]:
    """Collect abstract base classes from collections.abc."""
    collections_types = []
    
    for name in dir(collections.abc):
        if name.startswith('_'):
            continue
            
        obj = getattr(collections.abc, name)
        if isinstance(obj, type):
            collections_types.append(obj)
    
    return collections_types


def collect_typing_types_by_version() -> List[Any]:
    """Collect typing module types, handling version-specific differences."""
    major, minor, _ = get_python_version_info()
    typing_objects = []
    
    # Handle typing.__all__ exports
    if hasattr(typing, '__all__'):
        for name in typing.__all__:
            if name.startswith('_'):
                continue
                
            try:
                obj = getattr(typing, name)
                # Skip modules that might be imported
                if isinstance(obj, types.ModuleType):
                    continue
                typing_objects.append(obj)
            except AttributeError:
                continue
    
    # Version-specific handling
    if (major, minor) >= (3, 9):
        # Python 3.9+ has _GenericAlias and _SpecialGenericAlias
        # These are already included via __all__, but we handle them specifically
        pass
    
    if (major, minor) >= (3, 10):
        # Python 3.10+ has ParamSpec, Concatenate, etc.
        # Also included via __all__
        pass
    
    if (major, minor) >= (3, 11):
        # Python 3.11+ has Self, LiteralString, etc.
        # Also included via __all__
        pass
    
    # Add any additional objects that might not be in __all__
    additional_objects = []
    for name in dir(typing):
        if name.startswith('_'):
            continue
            
        try:
            obj = getattr(typing, name)
            if isinstance(obj, types.ModuleType):
                continue
                
            # Check if it's already included
            if obj not in typing_objects:
                # Only include typing-related objects
                if (isinstance(obj, type) or 
                    typing_inspect.is_typing_type(obj) or
                    hasattr(obj, '__origin__')):
                    additional_objects.append(obj)
        except Exception:
            continue
    
    typing_objects.extend(additional_objects)
    return typing_objects


def collect_all_type_objects() -> List[Any]:
    """Collect all type objects from all relevant modules."""
    all_objects = []
    
    # Collect from each module separately to maintain clarity
    builtin_types = collect_builtin_types()
    types_types = collect_types_module_types()
    collections_types = collect_collections_abc_types()
    typing_types = collect_typing_types_by_version()
    
    logger.info(f"Collected {len(builtin_types)} builtin types")
    logger.info(f"Collected {len(types_types)} types module types")
    logger.info(f"Collected {len(collections_types)} collections.abc types")
    logger.info(f"Collected {len(typing_types)} typing types")
    
    # Combine all collections
    all_objects.extend(builtin_types)
    all_objects.extend(types_types)
    all_objects.extend(collections_types)
    all_objects.extend(typing_types)
    
    # Deduplicate by id to avoid inserting the same object multiple times
    seen_ids = set()
    deduplicated = []
    for obj in all_objects:
        obj_id = id(obj)
        if obj_id not in seen_ids:
            seen_ids.add(obj_id)
            deduplicated.append(obj)
    
    logger.info(f"Total unique objects after deduplication: {len(deduplicated)}")
    return deduplicated


def is_typing_type_safe(obj: Any) -> bool:
    """Safe wrapper around typing_inspect.is_typing_type that handles version differences."""
    try:
        return typing_inspect.is_typing_type(obj)
    except (AttributeError, TypeError):
        # Fallback for Python 3.11+ where some typing_inspect functions may not work
        return (
            hasattr(obj, '__origin__') or
            isinstance(obj, (typing._GenericAlias, typing._SpecialForm)) or
            (hasattr(typing, '_TypeAliasType') and isinstance(obj, typing._TypeAliasType))
        )


def classify_type_object(obj: Any) -> Tuple[str, str, str, str, bool]:
    """
    Classify a type object and return (kind, namespace, name, alias_name, is_generic).
    
    Returns:
        kind: 'base', 'typevar', 'union', 'instantiation', 'callable'
        namespace: module name (e.g., 'builtins', 'typing', 'collections.abc')
        name: object name within its module
        alias_name: typing alias name (for typing objects)
        is_generic: whether the type is generic
    """
    module_name = getattr(obj, '__module__', None)
    obj_name = getattr(obj, '__name__', None)
    
    # Handle TypeVar
    if isinstance(obj, typing.TypeVar):
        return 'typevar', None, None, obj.__name__, False
    
    # Handle Union types
    if typing_inspect.is_union_type(obj):
        return 'union', None, None, None, False
    
    # Handle Generic instantiations (e.g., List[int])
    if typing_inspect.is_generic_type(obj) and not isinstance(obj, typing.TypeVar):
        origin = typing_inspect.get_origin(obj)
        if origin is not None:
            return 'instantiation', None, None, None, False
    
    # Handle regular classes
    if inspect.isclass(obj):
        # Determine if it's a typing alias
        alias_name = None
        namespace = module_name
        name = obj_name
        
        # Check if this is a typing object by checking if it's in typing.__all__
        if module_name == 'typing' and hasattr(typing, '__all__'):
            for typing_name in typing.__all__:
                if hasattr(typing, typing_name) and getattr(typing, typing_name) is obj:
                    alias_name = typing_name
                    break
        
        is_generic = (issubclass(obj, typing.Generic) if 
                     isinstance(obj, type) and hasattr(typing, 'Generic') else False) or \
                     typing_inspect.is_generic_type(obj)
        
        return 'base', namespace, name, alias_name, is_generic
    
    # Handle functions/methods
    if inspect.isfunction(obj) or inspect.ismethod(obj):
        return 'callable', module_name, obj_name, None, False
    
    # Handle other typing objects (Generic aliases, etc.)
    if is_typing_type_safe(obj) or hasattr(obj, '__origin__'):
        alias_name = None
        # Try to find the name in typing.__all__
        if hasattr(typing, '__all__'):
            for typing_name in typing.__all__:
                if hasattr(typing, typing_name) and getattr(typing, typing_name) is obj:
                    alias_name = typing_name
                    break
        
        is_generic = hasattr(obj, '__parameters__') or hasattr(obj, '__origin__')
        return 'base', 'typing', None, alias_name, is_generic
    
    # Default case
    return 'base', module_name, obj_name, None, False


def populate_type_nodes_for_version(cursor, py_version_id: int, type_objects: List[Any]) -> Dict[Any, int]:
    """
    Create type_nodes entries for all collected type objects.
    Returns mapping from Python object to database node ID.
    """
    cache: Dict[Tuple, int] = {}
    object_to_node_id: Dict[Any, int] = {}
    
    for obj in type_objects:
        try:
            kind, namespace, name, alias_name, is_generic = classify_type_object(obj)
            
            node_id = get_or_create_type_node(
                cursor, cache, namespace, name, alias_name, kind, is_generic, 
                py_version_id=py_version_id
            )
            object_to_node_id[obj] = node_id
            
        except Exception as e:
            logger.warning(f"Failed to process object {obj}: {e}")
            continue
    
    return object_to_node_id


def populate_types(python_version_id: int):
    """Main function to populate the type system for the current Python version."""
    with get_db_connection() as conn:
        cursor = conn.cursor()

        try:
            logger.info(f"Processing types for version ID: {python_version_id}")

            # Collect all type objects
            type_objects = collect_all_type_objects()

            # Verify we have basic types
            builtin_str_found = any(obj is str for obj in type_objects)
            builtin_int_found = any(obj is int for obj in type_objects)
            logger.info(f"Basic types found - str: {builtin_str_found}, int: {builtin_int_found}")

            # Create type_nodes entries
            object_to_node_id = populate_type_nodes_for_version(cursor, python_version_id, type_objects)

            logger.info(f"Created {len(object_to_node_id)} type nodes")

            # Populate relationships and additional data
            populate_union_members(cursor, object_to_node_id)
            populate_typevar_bounds_constraints(cursor, object_to_node_id)
            populate_type_node_relations(cursor, object_to_node_id)
            populate_class_generic_params(cursor, object_to_node_id)
            populate_type_schema_refs(cursor, object_to_node_id)

            conn.commit()
            logger.info("Database population complete!")

        except Exception as e:
            conn.rollback()
            logger.error(f"Error during type population: {e}")
            raise
        finally:
            cursor.close()
            conn.close()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Populate type system for a specific Python version")
    parser.add_argument("version_id", type=int, help="Version ID from the database")
    args = parser.parse_args()

    logging.basicConfig(level=logging.INFO)
    populate_types(args.version_id)
