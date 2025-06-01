import types
import typing
from typing import List, Tuple

from bll.type_node_cache import TypeNodeCache
from bll.type_node_models import TypeVarTypeNode
from bll.dto_models import IntrospectedModule


def extract_typevars_from_modules(
        modules: List[IntrospectedModule], 
        py_version_id: int,
        type_node_cache: TypeNodeCache
    ) -> List[TypeVarTypeNode]:
    """
    """
    typevars = []
    for module in modules:
        for attr_name in dir(module.type):
            try:
                obj = getattr(module.type, attr_name)
                if isinstance(obj, typing.TypeVar):
                    node = TypeVarTypeNode(
                        module=module,
                        obj=typing.TypeVar,
                        name=attr_name, 
                        py_version_id=py_version_id
                    )
                    type_node_cache.register(node)
            except Exception as e:
                print("typevar error: ", e)
                continue
    return typevars


def populate_type_nodes_of_typevars_tuples(
    modules: List[Tuple[str, types.ModuleType]],
    cache: TypeNodeCache
) -> TypeNodeCache:
    for (module_name, module_type) in modules:
        for attr_name in dir(module_type):
            try:
                obj = getattr(type, attr_name)
                if isinstance(obj, typing.TypeVar):
                    cache.get_or_create(obj=obj, owning_obj=type)
            except Exception as e:
                print(f"[WARN] TypeVar introspection failed on {module_name}.{attr_name}: {e}")
                continue

    return cache


def populate_type_nodes_of_typevars(
    modules: List[IntrospectedModule],
    cache: TypeNodeCache
) -> TypeNodeCache:
    
    for module in modules:
        for attr_name in dir(module.type):
            try:
                obj = getattr(module.type, attr_name)
                if isinstance(obj, typing.TypeVar):
                    cache.get_or_create(obj, module)
            except Exception as e:
                print(f"[WARN] TypeVar introspection failed on {module.name}.{attr_name}: {e}")
                continue

    return cache
        
