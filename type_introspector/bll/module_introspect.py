import builtins
import importlib
import inspect
import typing
from pathlib import Path

from typing import List
from bll.dto_models import IntrospectedClass, IntrospectedModule, ClassDiscovery

EXCLUDED_MODULES = {
    "antigravity", "this", "turtle", "pydoc", "tabnanny",
    "test", "tests", "idlelib", "ensurepip", "__main__",
    # `path` is now an environment's own Lib, which carries installed packages alongside the
    # stdlib. Without these the type database would record whatever happens to be pip-installed
    # in each environment as though it were part of Python.
    "site-packages", "dist-packages", "lib2to3", "venv", "__pycache__",
}

def introspect_modules(lib_path: Path, py_version_id: int) -> List[IntrospectedModule]:
    """
    Extracts (module_name, module_obj) pairs for all .py files under lib_path.
    Ignores tests. Modules that fail to load are skipped.
    """
    results = []
        
    for file in lib_path.rglob("*.py"):
        if any(part in EXCLUDED_MODULES for part in file.parts):
            continue
        
        try:
            module_name = ".".join(file.relative_to(lib_path).with_suffix("").parts)
            if any(part in EXCLUDED_MODULES for part in module_name.split(".")):
                continue

            module_obj = importlib.import_module(module_name)
            introspected_module = IntrospectedModule(module_obj, module_name, py_version_id=py_version_id)
            results.append(introspected_module)
        except Exception:
            continue


    return results


def get_builtin_and_typing_modules(py_version_id: int) -> List[IntrospectedModule]:
    """
    Returns IntrospectedModule entries for builtins, typing, datetime, decimal, etc.
    """
    std_modules = [
        ("builtins", builtins),
        ("typing", typing),
        ("datetime", __import__("datetime")),
        ("decimal", __import__("decimal")),
        ("collections", __import__("collections")),
    ]
    return [IntrospectedModule(mod, name, py_version_id) for name, mod in std_modules]


def discover_classes_from_modules(modules: List[IntrospectedModule], py_version_id: int) -> List[ClassDiscovery]:
    discoveries = []
    for mod in modules:
        for attr_name in dir(mod.type):
            try:
                obj = getattr(mod.type, attr_name)
            except Exception:
                continue

            if inspect.isclass(obj):
                docstring = inspect.getdoc(obj)
                is_generic = hasattr(obj, "__class_getitem__")
                discoveries.append(
                    ClassDiscovery(
                        module=mod,
                        class_name = attr_name,
                        obj = obj,
                        docstring=docstring,
                        is_generic=is_generic,
                        py_version_id = py_version_id
                    )
                )
    return discoveries


def is_generic_class(cls: type) -> bool:
    """
    Determines if the class is a subclass of typing.Generic *without* being a parameterized instantiation.
    """
    try:
        return issubclass(cls, typing.Generic)
    except TypeError:
        return False


def introspect_class_objects(
    discovered_classes: List[ClassDiscovery]
) -> List[IntrospectedClass]:

    results = []

    for dc in discovered_classes:
        if not hasattr(dc.obj, "__bases__"):
            continue  # Skip anything not a class (or failed resolution)

        base_classes = [
            known for known in discovered_classes
            if known.obj and hasattr(known.obj, "__bases__") and known.obj in dc.obj.__bases__
        ]

        results.append(IntrospectedClass(dc.module, dc.class_name, dc.obj, dc.docstring, dc.is_generic, base_classes))

    return results