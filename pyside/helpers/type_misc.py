from db_ops.type_elements import (
    insert_callable_parameter, 
    insert_callable_signatures, 
    insert_class_generic_params,
    insert_type_node_relation, 
    insert_type_schema_ref, 
    insert_typevar_bounds, 
    insert_typevar_constraints, 
    insert_typevar_variance, 
    insert_union_members
)

import typing_inspect
import inspect
from typing import Any, Dict, Generic, TypeVar


def populate_type_node_relations(cursor,
                               object_to_node_id: Dict[Any, int]) -> None:
    """
    For each class, record direct superclass relationships in class_inheritance.
    """
    for obj, node_id in object_to_node_id.items():
        if inspect.isclass(obj):
            for base in getattr(obj, "__bases__", []):
                base_node_id = object_to_node_id.get(base)
                if base_node_id:
                    insert_type_node_relation(cursor, parent_node_id=base_node_id, child_node_id=node_id)


def populate_callable_signatures(cursor, object_to_node_id: Dict[Any, int]) -> None:
    """
    For each function/method, insert a row in callable_signatures, then fill callable_parameters.
    """
    for obj, node_id in object_to_node_id.items():
        if inspect.isfunction(obj) or inspect.ismethod(obj):
            qualname = getattr(obj, "__qualname__", None)
            # Determine parent_schema_ref_id by finding its class in type_schema_refs later
            # For now, insert a signature row with parent_schema_ref_id = NULL
            try:
                sig = inspect.signature(obj)
            except (ValueError, TypeError):
                continue  # Unable to get signature; skip

            signature_id = insert_callable_signatures(cursor, name=obj.__name__, docstring=inspect.getdoc(obj))

            for idx, (param_name, param) in enumerate(sig.parameters.items()):
                param_annotation = param.annotation
                if param_annotation is inspect._empty:
                    continue  # No annotation; skip

                # Find the node_id for the annotation if available
                param_node_id = object_to_node_id.get(param_annotation)
                if not param_node_id and hasattr(param_annotation, "__origin__"):
                    param_node_id = object_to_node_id.get(param_annotation.__origin__)

                if param_node_id:
                    insert_callable_parameter(cursor, signature_id=signature_id, position=idx,
                                              param_name=param_name, param_type_node_id=param_node_id,
                                              is_vararg=(param.kind == inspect.Parameter.VAR_POSITIONAL),
                                              is_kwarg=(param.kind == inspect.Parameter.VAR_KEYWORD)
                                              )


def populate_class_generic_params(cursor, object_to_node_id: Dict[Any, int]) -> None:
    """
    For each class that subclasses typing.Generic, record its TypeVar parameters.
    """
    for obj, node_id in object_to_node_id.items():
        if inspect.isclass(obj) and issubclass(obj, Generic):
            # Get the TypeVar parameters
            params = getattr(obj, "__parameters__", ()) or ()
            for idx, param in enumerate(params):
                typevar_node_id = object_to_node_id.get(param)
                if typevar_node_id:
                    insert_class_generic_params(cursor, node_id, typevar_node_id, idx)


def populate_type_schema_refs(cursor, object_to_node_id: Dict[Any, int]) -> None:
    """
    For each class, walk its __dict__ to record fields (attributes, classvars) and methods in type_schema_refs.
    """
    for obj, class_node_id in object_to_node_id.items():
        if not inspect.isclass(obj):
            continue

        # Retrieve class members
        members = inspect.getmembers(obj)
        for position, (member_name, member_obj) in enumerate(members):
            # Skip private/magic names
            if member_name.startswith("_"):
                continue

            if inspect.isroutine(member_obj):
                member_kind = "method"
                type_node_id = None  # Method signature is in callable_signatures
            else:
                member_kind = "attribute"
                # Attempt to find annotation or default type
                annotation = getattr(obj, "__annotations__", {}).get(member_name)
                if annotation is not None:
                    type_node_id = object_to_node_id.get(annotation)
                else:
                    type_node_id = None

            insert_type_schema_ref(cursor, class_node_id, member_name, member_kind, type_node_id, position)


def populate_typevar_bounds_constraints(cursor, object_to_node_id: Dict[Any, int]) -> None:
    """
    For each TypeVar, record bounds and constraints, and set variance.
    """
    for obj, node_id in object_to_node_id.items():
        if isinstance(obj, TypeVar):
            # Bound
            bound = getattr(obj, "__bound__", None)
            if bound:
                bound_node_id = object_to_node_id.get(bound)
                if bound_node_id:
                    insert_typevar_bounds(cursor, node_id, bound_node_id)
            # Constraints
            constraints = getattr(obj, "__constraints__", ()) or ()
            for constr in constraints:
                constr_node_id = object_to_node_id.get(constr)
                if constr_node_id:
                    insert_typevar_constraints(cursor, node_id, constr_node_id)
            # Variance
            # Determine if TypeVar is covariant or contravariant
            cov = getattr(obj, "__covariant__", False)
            contra = getattr(obj, "__contravariant__", False)
            variance = "invariant"
            if cov:
                variance = "covariant"
            elif contra:
                variance = "contravariant"

            insert_typevar_variance(cursor, node_id, variance)


def populate_union_members(cursor, object_to_node_id: Dict[Any, int]) -> None:
    """
    For each union type, populate type_union_members.
    """
    for obj, node_id in object_to_node_id.items():
        if typing_inspect.is_union_type(obj):
            args = typing_inspect.get_args(obj, evaluate=True) or ()
            for arg in args:
                member_node_id = object_to_node_id.get(arg)
                if member_node_id:
                    insert_union_members(cursor, node_id, member_node_id)