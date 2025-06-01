import builtins
import inspect
import re
import sys
import types
from typing import Any, Callable, Dict, List, Optional, Tuple, Type, get_type_hints

from bll.dto_models import IntrospectedClass, IntrospectedModule
from custom_errors import UnrepresentableTypeException
from bll.type_node_models import ClassScopeCallableTypeNode, ModuleScopeCallableTypeNode, ScopedCallableTypeNode, TypeNode
from dal.callable_elements import insert_callable_signatures
from dal.callable_elements import insert_callable_parameter

from bll.type_node_models import CallableParameter


def populate_callable_ancillary_types(cursor, type_node_cache):
    """
    """
    callables: List[Tuple[int, ScopedCallableTypeNode]] = []

    for tn_id, type_node in type_node_cache:
        if type_node.kind == "callable":
            callables.append((tn_id, type_node))

    for tn_id, tn_callable in callables:
        sig_id = insert_callable_signatures(cursor, tn_id, tn_callable.return_type_id, tn_callable.docstring)
        
        for pid, param in tn_callable.callable_params:
            insert_callable_parameter(cursor, sig_id, param.position, param.name, pid, param.is_vararg, param.is_kwarg, param.default_value)


def is_safe_callable(obj: Any) -> bool:
    """
    Return True if the object appears to be a real function or method
    that we can safely introspect for parameters and return type.
    """
    if not callable(obj):
        return False

    # Exclude builtins (e.g., len, dict.update, etc.)
    if isinstance(obj, types.BuiltinFunctionType):
        return False

    if inspect.isbuiltin(obj):  # built-in or C-extension functions
        return False

    if not (inspect.isfunction(obj) or inspect.ismethod(obj)):
        return False

    try:
        sig = inspect.signature(obj)
    except (ValueError, TypeError):
        return False

    try:
        get_type_hints(obj, include_extras=True)
    except Exception:
        return False

    return True


def introspect_callables(
    modules: List[IntrospectedModule],
    classes: List[IntrospectedClass],
    py_version_id: int,
    type_node_cache
):
     
     callables: List[Tuple[Any, str, Any, Any, dict[str, Any]]] = []
     for m in modules:
        for attr_name in dir(m.type):
            obj = getattr(m.type, attr_name, None)
            if is_safe_callable(obj):
                callables.append((obj, attr_name, m.type, None, vars(sys.modules[m.name])))

     for c in classes:
        for attr_name, attr_value in inspect.getmembers(c.obj):
            if is_safe_callable(attr_value):
                callables.append((attr_value, attr_name, c.obj, c.class_name, vars(sys.modules[c.module.name])))

     for obj, name, owning_obj, class_name, globalns in callables:
         if should_skip_callable(obj):
            continue
         
         introspect_callable(obj, name, owning_obj, globalns, py_version_id, type_node_cache, class_name)
        
     return type_node_cache


def introspect_callable(
    obj: Any, 
    name: str,
    owning_obj: Any, 
    globalns: Dict[str, Any],
    py_version_id: int,
    type_node_cache,
    class_name: Optional[str]
) -> tuple[int, ModuleScopeCallableTypeNode | ClassScopeCallableTypeNode]:
    
    docstring = inspect.getdoc(obj)
    return_type_obj = discover_callable_return_type(obj, globalns)

    try:
        if return_type_obj is not None:
            return_type_id, _ = type_node_cache.get_or_create(return_type_obj, obj, is_callable_return_or_param=True)
        else:
            return_type_id = None
    except UnrepresentableTypeException as e:
        print(f"[Return Type Id None]: {e}\n")
        raise e
        
    param_ids, params = introspect_callable_parameters(obj, name, type_node_cache, globalns)

    if isinstance(owning_obj, types.ModuleType):
        scoped_callable = ModuleScopeCallableTypeNode(
            obj = obj,
            py_version_id = py_version_id,
            name = name,
            docstring = docstring,
            parameter_type_node_ids = param_ids,
            callable_params = params,
            return_type_obj = return_type_obj,
            return_type_id = return_type_id,
            module = IntrospectedModule(type = owning_obj, name = owning_obj.__name__, py_version_id=py_version_id)
        )
    else:
        scoped_callable = ClassScopeCallableTypeNode(
            obj=obj,
            name=name,
            py_version_id=py_version_id,
            class_type = owning_obj,
            class_name = class_name,
            callable_params = params,
            parameter_type_node_ids=param_ids,
            docstring=docstring,
            return_type_obj=return_type_obj,
            return_type_id=return_type_id,
            module = IntrospectedModule(type = owning_obj, name = owning_obj.__name__, py_version_id=py_version_id),
            is_staticmethod = isinstance(inspect.getattr_static(obj, name), staticmethod),
            is_classmethod = isinstance(inspect.getattr_static(obj, name), classmethod)
        )

    callable_id = type_node_cache.register(scoped_callable)
    
    return callable_id, scoped_callable


def introspect_callable_parameters(
    obj: Any,
    name: str,
    type_node_cache,
    globalns: Optional[Dict[str, Any]] = None,
) -> Tuple[List[int], List[Tuple[int, CallableParameter]]]:
    
    ids: List[int] = []
    params: List[Tuple[int, CallableParameter]] = []

    try:
        sig = inspect.signature(obj)
        type_hints = safe_get_type_hints(obj, globalns)

        for i, (sig_param_name, sig_param) in enumerate(sig.parameters.items()):
            param_result = ensure_callable_param(
                obj=obj, name=sig_param_name, param=sig_param, hint=str(type_hints.get(sig_param_name, Any)), position=i, type_node_cache=type_node_cache)
            if not param_result:
                return [], []
            else:
                (callable_param_tn_id, callable_param, _) = param_result
                ids.append(callable_param_tn_id)
                params.append((callable_param_tn_id, callable_param))

    except (ValueError, TypeError) as e:
        print(f"Could not introspect callable signature for {sig_param_name}: {e}")
        return [], []
    
    return ids, params


def should_skip_callable(obj: Any) -> bool:
    if obj is None:
        return True

    if type(obj) != classmethod and type(obj) != staticmethod and type(object) != type:
        source_path = inspect.getsourcefile(obj)
        if not source_path or "lib/python" in source_path:
            return True
    
    module = getattr(obj, "__module__", None)
    name = getattr(obj, "__name__", None)
    
    if not module:
        return True
    if module.startswith("_"):
        return True
 
    skip_modules = {"abc", "aifc", "distutils", "encodings", "imp"}
    skip_names = {
        "abstractmethod", "abstractclassmethod", "abstractstaticmethod",
        "__lt__", "__gt__", "__le__", "__ge__", "__eq__", "__ne__",
        "__new__", "__init_subclass__", "__class_getitem__", "namedtuple"
    }\

    if name in skip_names or module in skip_modules:
        return True
            
    if isinstance(obj, types.BuiltinFunctionType):
        return True

    # Sanity-check callability
    if not callable(obj):
        return True

    # Reject things that blow up on introspection
    try:
        inspect.signature(obj)
        get_type_hints(obj)
    except Exception:
        return True

    return False


def safe_get_type_hints(obj: Any, globalns: Optional[Dict[str, Any]] = None) -> Dict[str, Any]:
    try:
        base_globals = getattr(obj, '__globals__', {})
        if globalns is None:
            globalns = {}
        combined_globals = {
            **base_globals,
            **globalns,
            're': re,
            '__builtins__': builtins
        }
        return get_type_hints(obj, include_extras=True, globalns=combined_globals)
    except Exception as e:
        with open(r"C:\Users\wini\Downloads\return_type_attempts.txt", "a", encoding="utf-8") as f:
            f.write(f"[TypeHintError] {getattr(obj, '__module__', '?')}.{getattr(obj, '__name__', '?')} → {type(e).__name__}: {e}\n")
        return {}


def discover_callable_return_type(
        func: Callable,
        globalns: Optional[Dict[str, Any]] = None
    ) -> Optional[Type]:
    """
    Returns:
        - `type(None)` if annotated with `-> None`
        - `None` if there is no annotation
        - Some other `type` if annotated
    """
    try:
        annotations = inspect.get_annotations(
            func, 
            eval_str=True, 
            globals=globalns or getattr(func, '__globals__', {})
        )
        return annotations.get('return', None)

    except Exception as e:
        try:
            name = getattr(func, '__name__', repr(func))
            mod = getattr(func, '__module__', '<unknown>')
        except Exception:
            name = repr(func)
            mod = '<unknown>'
        print(f"[ERROR] {mod}.{name} → {e}\n")
        return None


def ensure_callable_param(
    obj: Any, 
    name: str,
    param: inspect.Parameter,
    hint: str,
    position: int,
    type_node_cache
) -> Optional[Tuple[int, CallableParameter, TypeNode]]:

    try:
        param_node_id, node = type_node_cache.get_or_create(hint, obj, is_callable_return_or_param=True)
    except UnrepresentableTypeException:
        return None

    callable_param = CallableParameter(
        name = name,
        docstring = inspect.getdoc(param),
        position = position,
        is_vararg = param.kind == inspect.Parameter.VAR_POSITIONAL,
        is_kwarg = param.kind == inspect.Parameter.VAR_KEYWORD,
        default_value = None if param.default is inspect.Parameter.empty else param.default,
        type_node = node
    )

    return param_node_id, callable_param, node


def get_callable_log_text(scoped_callable: ScopedCallableTypeNode, type_node_cache) -> str:
    node_id, return_type_node = type_node_cache.by_id[scoped_callable.return_type_id]
    return_type_desc = f"""{return_type_node.name if return_type_node.name else '(Unnamed)'}
            node_id:    {node_id}
            namespace:  {return_type_node.namespace if return_type_node.namespace else 'None'}
            kind:       {return_type_node.kind}
            is_generic: {str(return_type_node.is_generic)}
            dedup_hash: {return_type_node.dedup_hash()}\n""" if return_type_node else "[No Data]"

    return f"""
    {scoped_callable.name}
        qualified_name: {scoped_callable.namespace}{f'.{scoped_callable.name}' if isinstance(scoped_callable, ClassScopeCallableTypeNode) else ''}{scoped_callable.name}
        {'declared:       class' if isinstance(callable, ClassScopeCallableTypeNode) else 'declared:       module'}
        namespace:      {scoped_callable.namespace}
        class:          {scoped_callable.class_name}
        docstring:      {scoped_callable.docstring or 'None'}
        returns:        {return_type_desc}
        params:         {get_callable_params_log_text(scoped_callable.callable_params)}
        dedup_hash:     {scoped_callable.dedup_hash()}
    """


def get_callable_params_log_text(params: List[Tuple[int, CallableParameter]]) -> str:
    return "[None]" if len(params) == 0 else "".join(
            f"""{param.name}
            position:       {param.position}
            node_id:        {node_id}
            node_namespace: {param.type_node.namespace}
            node_name:      {param.type_node.name}
            node_kind:      {param.type_node.kind}
            node_hash:      {param.type_node.dedup_hash()}
            default value:  {param.default_value or 'None'}
            docstring:      {param.docstring or 'None'}
    """ for node_id, param in params)
