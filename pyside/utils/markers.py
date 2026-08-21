import re
from typing import Any, List, Literal, Optional, Set

from packaging.markers import Marker, Variable
from packaging.requirements import Requirement

from models.marker_types import MarkerComparison, MarkerExpr, MarkerLogical



def prune_extra_nodes(node: MarkerExpr) -> Optional[MarkerExpr]:
    """
    Recursively remove any subtree that is purely `extra == ...`.

    - If node is MarkerComparison with variable == "extra", return None.
    - If node is MarkerLogical, prune left/right and rebuild if possible.
    """
    if isinstance(node, MarkerComparison):
        if node.variable.lower() == "extra":
            return None
        return node

    elif isinstance(node, MarkerLogical):
        left = prune_extra_nodes(node.left)
        right = prune_extra_nodes(node.right)

        if left is None and right is None:
            return None
        if left is None:
            return right
        if right is None:
            return left

        return MarkerLogical(operator=node.operator, left=left, right=right)

    raise TypeError(f"Unexpected MarkerExpr type: {type(node)}")



def has_non_extra_conditions(marker: Marker) -> bool:
    """
    Return True if the marker contains any condition other than `extra == ...`.
    """
    try:
        normalized = normalize_marker_node(marker._markers)
        expr_tree = build_marker_expr_tree(normalized)
        pruned = prune_extra_nodes(expr_tree)
        return pruned is not None
    except Exception:
        return True



def normalize_marker_node(node: Any) -> Any:
    """
    Normalize a Marker._markers tree to ensure all elements are standard Python
    types (lists and strings), making it easier to traverse later.

    Leaf comparisons will be lists of [variable, operator, value]
    Logical expressions will be lists of [left, operator, right]
    """
    # Handle packaging.markers objects
    if hasattr(node, 'value'):
        return str(node.value)
    if hasattr(node, 'name'):
        return str(node.name)

    # Singleton list wrapper
    if isinstance(node, list) and len(node) == 1:
        return normalize_marker_node(node[0])

    # Logical node: [left, 'and'/'or', right]
    if (
        isinstance(node, (list, tuple))
        and len(node) == 3
        and isinstance(node[1], str)
        and node[1].lower() in {"and", "or"}
    ):
        return [
            normalize_marker_node(node[0]),
            node[1].lower(),
            normalize_marker_node(node[2]),
        ]

    # Leaf comparison: ['python_version', '<', '3.10']
    if isinstance(node, (list, tuple)) and len(node) == 3:
        return [
            normalize_marker_node(node[0]),
            normalize_marker_node(node[1]),
            normalize_marker_node(node[2])
        ]

    # Fallback — likely malformed
    return node


# `a < b` reversed is `b > a`; equality and membership keep their meaning either way, except
# `in`/`not in`, whose operands are not interchangeable and which are left as written.
_REVERSED_OPERATORS = {"<": ">", "<=": ">=", ">": "<", ">=": "<="}

# PEP 508 defines a closed set of marker variables. `normalize_marker_node` flattens packaging's
# Variable/Value/Op objects to plain strings, so by the time a comparison is built the only way to
# tell which side is the variable is by name.
MARKER_VARIABLES = frozenset({
    "python_version", "python_full_version", "os_name", "sys_platform", "platform_machine",
    "platform_python_implementation", "platform_release", "platform_system", "platform_version",
    "implementation_name", "implementation_version", "extra",
})


def build_marker_expr_tree(node: Any) -> MarkerExpr:
    """
    Convert a normalized marker structure (nested lists or tuples) into a proper MarkerExpr tree.
    Handles n-ary logical operations by converting them into a binary tree structure.
    """
    if isinstance(node, list):
        # Handle n-ary logical operations
        if len(node) > 3 and isinstance(node[1], str) and node[1].lower() in {"and", "or"}:
            operator = node[1].lower()
            result = MarkerLogical(
                operator=operator.upper(),
                left=build_marker_expr_tree(node[0]),
                right=build_marker_expr_tree(node[2])
            )
            for i in range(4, len(node), 2):
                result = MarkerLogical(
                    operator=operator.upper(),
                    left=result,
                    right=build_marker_expr_tree(node[i])
                )
            return result

        # Handle binary logical operation
        elif len(node) == 3 and isinstance(node[1], str) and node[1].lower() in {"and", "or"}:
            return MarkerLogical(
                operator=node[1].upper(),
                left=build_marker_expr_tree(node[0]),
                right=build_marker_expr_tree(node[2])
            )

        # Handle plain comparative list like ['python_version', '==', '3.6']
        elif len(node) == 3 and all(isinstance(n, str) for n in node):
            left, operator, right = node

            # Either side may hold the variable: `sys_platform == "win32"` and
            # `"freebsd" in sys_platform` are both legal PEP 508 and packaging preserves whichever
            # order was written. Taking the left blindly stored "freebsd" in the variable column,
            # which surfaced as an opaque truncation error rather than a parse failure.
            if left in MARKER_VARIABLES:
                variable, value = left, right
            elif right in MARKER_VARIABLES:
                variable, value = right, left
                # reading right-to-left flips an ordering operator; `in`/`not in` are left as
                # written, since their operands are not interchangeable
                operator = _REVERSED_OPERATORS.get(operator, operator)
            else:
                variable, value = left, right

            return MarkerComparison(
                variable=variable,
                operator=operator,
                value=value
            )

    # Handle structured comparison tuple like (Variable, Op, Value)
    elif isinstance(node, tuple) and len(node) == 3:
        left, operator, right = node
        operator = getattr(operator, 'value', operator)

        # PEP 508 lets either side hold the variable: `sys_platform == "win32"` and
        # `"freebsd" in sys_platform` are both legal, and packaging hands back whichever order was
        # written. Taking node[0] as the variable stored "freebsd" in the variable column, which
        # the enum rejects - and because the failure surfaces as a truncation error rather than a
        # parse error, it looked like a schema problem rather than a reversed comparison.
        if isinstance(left, Variable):
            variable, value = left, right
        elif isinstance(right, Variable):
            variable, value = right, left
            # the comparison now reads right-to-left, so an ordering operator has to flip with it
            operator = _REVERSED_OPERATORS.get(operator, operator)
        else:
            variable, value = left, right

        return MarkerComparison(
            variable=getattr(variable, 'value', variable),
            operator=operator,
            value=getattr(value, 'value', value)
        )

    raise TypeError(f"Cannot convert node to MarkerExpr: {node!r}")




def extract_extras_from_marker(marker: Marker) -> Set[str]:
    """
    Extract all extra names from an environment marker.

    Handles complex markers like:
    - extra == "gpu"
    - extra == "gpu" or extra == "cuda"
    - python_version >= "3.7" and extra == "dev"

    Args:
        marker: A packaging.markers.Marker object

    Returns:
        Set of extra names found in the marker
    """
    extras = set()

    try:
        # Convert marker to string for regex parsing
        marker_str = str(marker)

        # Find all extra == "value" patterns
        extra_patterns = [
            r"extra\s*==\s*['\"]([^'\"]+)['\"]",  # extra == "name"
            r"extra\s*==\s*([a-zA-Z0-9_-]+)",     # extra == name (without quotes)
        ]

        for pattern in extra_patterns:
            matches = re.findall(pattern, marker_str, re.IGNORECASE)
            extras.update(matches)

    except Exception:
        # If parsing fails, return empty set
        pass

    return extras


def extract_required_extras(requirement: Requirement) -> List[str]:
    """
    Extract all extras from the required distribution, e.g. 'foo[bar,baz]' → ['bar', 'baz'].

    Args:
        requirement: A packaging.requirements.Requirement object.

    Returns:
        A list of extras specified in brackets on the required package.
    """
    return sorted(requirement.extras)  # sorted for determinism, optional


def extract_primary_extra(marker: Marker) -> Optional[str]:
    """
    Extract the primary extra name from a marker.

    For simple cases like extra == "gpu", returns "gpu".
    For complex cases, returns the first extra found.

    Args:
        marker: A packaging.markers.Marker object

    Returns:
        The primary extra name, or None if no extra is found
    """
    extras = extract_extras_from_marker(marker)
    return next(iter(extras)) if extras else None


def simplify_marker_for_logging(marker: Marker) -> str:
    """
    Create a simplified string representation of a marker for logging.

    Args:
        marker: A packaging.markers.Marker object

    Returns:
        A simplified string representation
    """
    try:
        marker_str = str(marker)
        # Truncate very long markers
        if len(marker_str) > 100:
            return marker_str[:97] + "..."
        return marker_str
    except Exception:
        return "<unparseable marker>"


def marker_applies_to_extra(marker: Marker, extra_name: str) -> bool:
    """
    Check if a marker applies to a specific extra.

    Args:
        marker: A packaging.markers.Marker object
        extra_name: The extra name to check for

    Returns:
        True if the marker explicitly includes the given extra
    """
    extras = extract_extras_from_marker(marker)
    return extra_name in extras


def compare_if_true(value: bool) -> Literal['COMPARATIVE', 'LOGICAL']:
    return 'COMPARATIVE' if value else 'LOGICAL'


def is_pure_extra_marker(marker: Marker) -> bool:
    """
    Check if a marker contains only extra conditions (no environment conditions).

    Args:
        marker: A packaging.markers.Marker object

    Returns:
        True if the marker contains only extra conditions
    """
    return not has_non_extra_conditions(marker)


def extract_extra_marker(requirement: Requirement) -> Optional[str]:
    """
    Extract the extra name from a requirement.

    Args:
        requirement: A packaging.requirements.Requirement object

    Returns:
        The extra name if present, otherwise None.
    """
    if requirement.extras:
        return next(iter(requirement.extras))

    marker = requirement.marker
    if marker is None:
        return None

    try:
        markers = marker._markers
    except AttributeError:
        return None

    for item in markers:
        if (
            isinstance(item, tuple)
            and len(item) == 3
            and getattr(item[0], "value", None) == "extra"
            and getattr(item[1], "value", None) == "=="
        ):
            return getattr(item[2], "value", None)
    return None
