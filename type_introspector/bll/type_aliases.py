import inspect

from typing import Dict, List

from bll.dto_models import DiscoveredAlias, IntrospectedModule
from bll.type_node_cache import TypeNodeCache
from bll.type_node_models import AliasTypeNode, TypeNode
from custom_errors import UnrepresentableTypeException
from dal.type_elements import insert_type_alias



def discover_aliases_from_modules(
    modules: List[IntrospectedModule],
    py_version_id
) -> List[DiscoveredAlias]:
    """
    Enumerates non-class, non-function attributes of the given modules and records them as DiscoveredAlias.
    """
    aliases = []

    for mod in modules:
        for attr_name in dir(mod.type):
            if attr_name.startswith("__") and attr_name.endswith("__"):
                continue

            # Retrieve safely
            try:
                obj = getattr(mod.type, attr_name)
            except Exception:
                continue

            # Some attributes may error even during isinstance or equality checks
            try:
                # Skip known categories
                if inspect.isclass(obj):
                    continue
                if inspect.isfunction(obj) or inspect.isbuiltin(obj):
                    continue
                if inspect.ismodule(obj):
                    continue
                if obj is None:
                    continue
                if isinstance(obj, (int, str, float, bool, bytes, tuple, list, dict, set)):
                    continue
                if obj is Ellipsis or obj is NotImplemented:
                    continue
            except Exception:
                # Skip anything that fails inspection
                continue

            aliases.append(
                DiscoveredAlias(
                    module=mod,
                    alias_name=attr_name,
                    obj=obj,
                    target_expr=None,
                    py_version_id=py_version_id,
                    lineno=-1,
                )
            )

    return aliases


def populate_type_nodes_of_alias(
    aliases: List[DiscoveredAlias],
    type_node_cache: TypeNodeCache
) -> TypeNodeCache:
    
    for alias in aliases:
        if alias.obj is None or inspect.ismodule(alias.obj):
            continue  # Can't resolve target if runtime object isn't available

        # Normalize the alias's *referenced type*
        try:
            target_type_node = type_node_cache.infer_correct_node_type(obj=alias.obj)
        except (UnrepresentableTypeException, ValueError, TypeError):
            # Optionally log here to see what you're skipping
            continue

        target_hash = target_type_node.dedup_hash()

        # Look up matching node
        target_node_id = None
        for existing_id, existing_node in type_node_cache.by_id.items():
            if existing_node.dedup_hash() == target_hash:
                target_node_id = existing_id
                break

        if target_node_id is None:
            continue  # Target type not inserted yet — skip or defer

        alias_node = AliasTypeNode(
            obj=alias.obj,
            name=alias.alias_name,
            target_node_id=target_node_id,
            py_version_id=alias.py_version_id,
            module = alias.module
        )

        type_node_cache.register(alias_node)
        

    return type_node_cache


def populate_type_aliases(cursor, type_node_cache: Dict[int, TypeNode]):
    alias_count = 0
    for type_node_id, type_node in type_node_cache.items():
        if isinstance(type_node, AliasTypeNode):
            alias_count += 1
            insert_type_alias(cursor, type_node_id, type_node.target_node_id)

    print(f"Inserted {alias_count} type_aliases")

