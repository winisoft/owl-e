from pathlib import Path
from typing import List
import logging

from cpython_targets import CPYTHON_DATA

from bll.dto_models import ClassDiscovery, IntrospectedClass
from bll.type_node_cache import TypeNodeCache

from bll import module_introspect, synthetic_inserts, type_aliases, callable_types, type_node_relations, type_nodes, typevars

from db_connection import get_db_connection



logger = logging.getLogger(__name__)

BASE_DIR = Path(__file__).parent.resolve()


def populate_introspected_type_data() -> None:
    """
    Discovers type-bearing classes via AST, resolves them into live objects,
    and performs dynamic introspection to fully populate the type system schema.
    """
    with get_db_connection() as conn:
        cursor = conn.cursor()

        try:
            for datum in CPYTHON_DATA:
                py_version_id = datum["id"]
                stdlib_lib_path = (BASE_DIR / datum["path"]).resolve(strict=True)

                print(f"Populating types for Python version ID: {py_version_id}")
                type_node_cache = TypeNodeCache(py_version_id, cursor)

                print(">> Phase 1: module & classes introspection...")
                # Discover modules & classes, introspect class info w/ inheritance hierarchy; insert type nodes
                lib_modules = module_introspect.introspect_modules(stdlib_lib_path, py_version_id)
                extra_modules = module_introspect.get_builtin_and_typing_modules(py_version_id)
                modules = lib_modules + extra_modules

                class_discoveries: List[ClassDiscovery] = module_introspect.discover_classes_from_modules(modules, py_version_id)
                classes: List[IntrospectedClass] = module_introspect.introspect_class_objects(class_discoveries)
                module_aliases = type_aliases.discover_aliases_from_modules(modules, py_version_id)

                print(">> Phase 2: insert core type...")
                type_node_cache = type_nodes.populate_base_type_nodes(classes, type_node_cache)
                type_node_cache = type_aliases.populate_type_nodes_of_alias(module_aliases, type_node_cache)
                type_node_cache = typevars.populate_type_nodes_of_typevars(modules, type_node_cache)
                type_node_cache = synthetic_inserts.populate(py_version_id, type_node_cache)
                type_node_cache = callable_types.introspect_callables(modules, classes, py_version_id, type_node_cache)

                print(">> Phase 3: insert ownership/composition and subclass relations...")
                type_node_relations.populate_type_node_relations(cursor, type_node_cache)
                type_nodes.populate_type_schema_refs(cursor, type_node_cache)
                type_node_relations.populate_callable_ownership(cursor, type_node_cache)

                conn.commit()
                print("Completed full type system population.")

        except Exception:
            conn.rollback()
            raise
        finally:
            cursor.close()
            conn.close()


if __name__ == "__main__":
    populate_introspected_type_data()
