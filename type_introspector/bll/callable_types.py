import builtins
import inspect
import re
import sys
import types
from typing import Any, Callable, Dict, List, Optional, Tuple, Type, get_type_hints, Union

from bll.dto_models import IntrospectedClass, IntrospectedModule
from custom_errors import UnrepresentableTypeException
from bll.type_node_models import ClassScopeCallableTypeNode, ModuleScopeCallableTypeNode, ScopedCallableTypeNode, TypeNode
from dal.callable_elements import insert_callable_owners
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
            insert_callable_parameter(
                cursor, sig_id, param.position, param.name, pid,
                param.is_vararg, param.is_kwarg, param.default_value,
                param.is_positional_only, param.is_keyword_only
            )


# `include_extras` arrived in 3.9. On 3.8 passing it raises TypeError for *every* callable, which
# `is_safe_callable` read as "not introspectable" - the 3.8 pass collected zero callables because
# of it. Annotated metadata simply is not available there, which is the honest answer rather than
# a reason to reject the callable.
_SUPPORTS_INCLUDE_EXTRAS = sys.version_info >= (3, 9)


def get_type_hints_compat(obj, globalns=None):
    if _SUPPORTS_INCLUDE_EXTRAS:
        return get_type_hints(obj, globalns=globalns, include_extras=True)
    return get_type_hints(obj, globalns=globalns)


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
    except Exception:
        # Deliberately broad. Under PEP 649 (3.14) `signature` evaluates deferred annotations,
        # so a stdlib function annotated with a TYPE_CHECKING-only name raises NameError here -
        # `_colorize.can_colorize`, annotated `IO[str] | IO[bytes] | None`, aborted the whole
        # 3.14 run. Any failure to read a signature simply means it is not safely introspectable.
        return False

    try:
        get_type_hints_compat(obj)
    except Exception:
        return False

    return True


def introspect_callables(
    modules: List[IntrospectedModule],
    classes: List[IntrospectedClass],
    py_version_id: int,
    type_node_cache
):
     
     callables: List[Tuple[Any, str, Any, Any, Dict[str, Any]]] = []
     for m in modules:
        for attr_name in dir(m.type):
            obj = getattr(m.type, attr_name, None)
            if is_safe_callable(obj):
                callables.append((obj, attr_name, m.type, None, vars(sys.modules[m.name])))

     for c in classes:
        for attr_name, attr_value in inspect.getmembers(c.obj):
            if is_safe_callable(attr_value):
                callables.append((attr_value, attr_name, c.obj, c.class_name, vars(sys.modules[c.module.name])))

     # One unmodellable signature should not end the pass. The stdlib is full of callables whose
     # annotations cannot be represented - C functions carrying none, dynamically built wrappers,
     # deprecated shims - and aborting on the first leaves the entire version uncollected. These
     # are counted and reported rather than swallowed, so a pass that quietly collected very
     # little is visible as such.
     failures = {}
     done = 0
     for obj, name, owning_obj, class_name, globalns in callables:
         if should_skip_callable(obj):
            continue

         try:
             callable_id, _ = introspect_callable(
                 obj, name, owning_obj, globalns, py_version_id, type_node_cache, class_name
             )
             done += 1

             # Ownership is recorded here, not reconstructed later, because callable nodes dedup:
             # a method inherited by fifty classes is ONE node reachable from fifty owners, and a
             # deduped node remembers only the class it was first seen under. This loop still has
             # every (owner, callable) pair, so it is the only place the full multiplicity exists.
             if isinstance(owning_obj, type):
                 try:
                     owner_id, _ = type_node_cache.get_or_create(owning_obj)
                     insert_callable_owners(type_node_cache.cursor, owner_id, callable_id)
                 except Exception:
                     pass
         except Exception as e:
             failures[type(e).__name__] = failures.get(type(e).__name__, 0) + 1

     print(f"   callables: {done} introspected, {sum(failures.values())} skipped")
     for kind, count in sorted(failures.items(), key=lambda kv: -kv[1]):
         print(f"     {kind}: {count}")

     return type_node_cache


def _static_kind(owning_obj: Any, name: str, wrapper: type) -> bool:
    """
    Whether `name` is declared on `owning_obj` as a `staticmethod` / `classmethod`.

    Looked up on the *owning class*, not on the bound function: asking the function object for
    its own name finds nothing. `getattr_static` deliberately avoids the descriptor protocol, so
    it raises for anything supplied dynamically through `__getattr__` - common enough in the
    stdlib that it has to be tolerated rather than allowed to end the pass.
    """
    try:
        return isinstance(inspect.getattr_static(owning_obj, name), wrapper)
    except Exception:
        return False


def introspect_callable(
    obj: Any, 
    name: str,
    owning_obj: Any, 
    globalns: Dict[str, Any],
    py_version_id: int,
    type_node_cache,
    class_name: Optional[str]
) -> Tuple[int, Union[ModuleScopeCallableTypeNode, ClassScopeCallableTypeNode]]:
    
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
            # `owning_obj` is the class here, so its module is `__module__`; `__name__` would
            # name the class and file every method under a namespace like `ComboBox` rather than
            # `tkinter.tix`, which is also what stopped ownership lookups finding their class.
            module = IntrospectedModule(
                type = owning_obj,
                name = getattr(owning_obj, "__module__", "") or owning_obj.__name__,
                py_version_id = py_version_id
            ),
            is_staticmethod = _static_kind(owning_obj, name, staticmethod),
            is_classmethod = _static_kind(owning_obj, name, classmethod)
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
            # The resolved annotation object, not its repr: `str()` here turned every hint into a
            # string, which the cache rejects outright as an unrepresentable form - so every
            # parameter failed, and the `return [], []` below discarded the whole signature with
            # it. That is why `callable_parameters` came back empty for all 37,010 callables.
            hint = type_hints.get(sig_param_name, Any)
            (callable_param_tn_id, callable_param, _) = ensure_callable_param(
                obj=obj, name=sig_param_name, param=sig_param, hint=hint,
                position=i, type_node_cache=type_node_cache
            )
            ids.append(callable_param_tn_id)
            params.append((callable_param_tn_id, callable_param))

    except (ValueError, TypeError) as e:
        # `obj` rather than a parameter name: `inspect.signature` can raise before the loop binds
        # one, and naming it here raised NameError on top of the real error.
        print(f"Could not introspect callable signature for {obj!r}: {e}")
        return [], []

    return ids, params


def should_skip_callable(obj: Any) -> bool:
    if obj is None:
        return True

    # A source-path filter used to sit here, skipping anything under `lib/python`. Its guard read
    # `type(object) != type`, which is always False, so it never ran - and that is the only reason
    # this tool works: repairing the typo would make it skip the entire standard library, which is
    # precisely what it exists to introspect. Removed rather than fixed. Callables with no
    # retrievable source are already excluded by `is_safe_callable`.
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
        return get_type_hints_compat(obj, globalns=combined_globals)
    except Exception as e:
        with open(r"C:\Users\wini\Downloads\return_type_attempts.txt", "a", encoding="utf-8") as f:
            f.write(f"[TypeHintError] {getattr(obj, '__module__', '?')}.{getattr(obj, '__name__', '?')} -> {type(e).__name__}: {e}\n")
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
        print(f"[ERROR] {mod}.{name} -> {e}\n")
        return None


def _default_text(value: Any) -> Optional[str]:
    """
    A parameter's default rendered as text, because the column stores text and a default can be
    any object at all - an enum member, a sentinel, a function, a mutable container.

    `repr` rather than `str`, so `None` stays distinguishable from `"None"` and `0` from `"0"`.
    An object whose `repr` itself raises still yields something recordable instead of failing the
    whole insert, and the result is clipped to the column width.
    """
    try:
        text = repr(value)
    except Exception:
        text = f"<unrepresentable {type(value).__name__}>"
    return text[:1024]


def ensure_callable_param(
    obj: Any,
    name: str,
    param: inspect.Parameter,
    hint: Any,
    position: int,
    type_node_cache
) -> Tuple[Optional[int], CallableParameter, Optional[TypeNode]]:
    """
    A parameter always yields a row; only its *type* may be missing.

    An annotation that cannot be modelled is recorded as an absent type rather than dropping the
    parameter - the parameter demonstrably exists and its position matters, and
    `callable_parameters.param_type_node_id` is nullable for exactly this case.
    """
    try:
        param_node_id, node = type_node_cache.get_or_create(hint, obj, is_callable_return_or_param=True)
    except UnrepresentableTypeException:
        param_node_id, node = None, None

    callable_param = CallableParameter(
        name = name,
        docstring = inspect.getdoc(param),
        position = position,
        is_vararg = param.kind == inspect.Parameter.VAR_POSITIONAL,
        is_kwarg = param.kind == inspect.Parameter.VAR_KEYWORD,
        is_positional_only = param.kind == inspect.Parameter.POSITIONAL_ONLY,
        is_keyword_only = param.kind == inspect.Parameter.KEYWORD_ONLY,
        default_value = None if param.default is inspect.Parameter.empty else _default_text(param.default),
        type_node = node
    )

    return param_node_id, callable_param, node


def get_callable_log_text(scoped_callable: ScopedCallableTypeNode, type_node_cache) -> str:
    return_type_node = type_node_cache.by_id.get(scoped_callable.return_type_id)
    node_id = scoped_callable.return_type_id
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
