from typing import Optional, List, Tuple
import logging
from packaging.requirements import Requirement
from models.marker_types import MarkerComparison, MarkerExpr, MarkerLogical
from utils.markers import build_marker_expr_tree, has_non_extra_conditions, normalize_marker_node, prune_extra_nodes

logger = logging.getLogger(__name__)


def _walk_marker_expr_tree(
    node: MarkerExpr,
    parent_expr_id: Optional[int],
    requirement_id: int,
    expressions_acc: List[MarkerExpr],
    comparisons_acc: List[MarkerComparison],
    logicals_acc: List[MarkerLogical]
) -> int:
    """
    Recursively traverse a MarkerExpr tree, emit EnvMarker* dataclasses, and
    populate the given accumulators.

    Returns:
        The expression_id (int) of the node in `expressions_acc`
    """
    placeholder_id = len(expressions_acc)

    if isinstance(node, MarkerComparison):
        expressions_acc.append(MarkerComparison(
            id=placeholder_id,
            requirement_id=requirement_id,
            mode="COMPARATIVE",
            parent_id=parent_expr_id
        ))

        comparisons_acc.append(MarkerComparison(
            expression_id=placeholder_id,
            variable=node.variable,
            operator=node.operator,
            value=node.value
        ))
        return placeholder_id

    elif isinstance(node, MarkerLogical):
        expressions_acc.append(MarkerExpr(
            id=placeholder_id,
            requirement_id=requirement_id,
            mode="LOGICAL",
            parent_id=parent_expr_id
        ))

        left_id = _walk_marker_expr_tree(
            node=node.left,
            parent_expr_id=placeholder_id,
            requirement_id=requirement_id,
            expressions_acc=expressions_acc,
            comparisons_acc=comparisons_acc,
            logicals_acc=logicals_acc
        )

        right_id = _walk_marker_expr_tree(
            node=node.right,
            parent_expr_id=placeholder_id,
            requirement_id=requirement_id,
            expressions_acc=expressions_acc,
            comparisons_acc=comparisons_acc,
            logicals_acc=logicals_acc
        )

        logicals_acc.append(MarkerLogical(
            expression_id=placeholder_id,
            operator=node.operator,
            left_child_id=left_id,
            right_child_id=right_id
        ))
        return placeholder_id

    else:
        raise TypeError(f"Unexpected MarkerExpr type: {type(node)}")



def process_marker_set(
    requires_dist_id_strs: Optional[List[Tuple[int, str]]]
) -> Tuple[
    Optional[List[MarkerExpr]],
    Optional[List[MarkerComparison]],
    Optional[List[MarkerLogical]]
]:
    """
    Given a list of (required_dist_specifier_id, raw_requires_dist_string),
    this function:

      1. Parses each raw_requires_dist_string with packaging.Requirement.
      2. Checks if it has any “non-extra” conditions (via has_non_extra_conditions).
      3. Converts the marker to a MarkerExpr tree.
      4. Prunes away any purely-extra subtrees.
      5. Walks the pruned tree to build:
         - EnvMarkerExpression objects (1 per node in the pruned tree)
         - EnvMarkerComparison objects (1 per leaf comparison)
         - EnvMarkerLogical objects (1 per logical op node)

    Returns:
      (expressions, comparisons, logicals), or (None, None, None) if:
        - `requires_dist_id_strs` is None or empty, or
        - after pruning, there are no non-extra nodes to record.

    — No DB calls happen here; we only return three pure-Python lists of dataclasses.
    """
    if not requires_dist_id_strs:
        return None, None, None

    all_expressions: List[MarkerExpr] = []
    all_comparisons: List[MarkerComparison] = []
    all_logicals: List[MarkerLogical] = []

    for (req_dist_id, req_str) in requires_dist_id_strs:
        if not req_str or not req_str.strip():
            continue

        try:
            req = Requirement(req_str)
        except Exception as e:
            logger.error(f"Failed to parse Requirement('{req_str}'): {e}")
            continue

        if req.marker and has_non_extra_conditions(req.marker):
            try:
                raw = req.marker._markers
                normalized = normalize_marker_node(raw)
                tree = build_marker_expr_tree(normalized)
                pruned = prune_extra_nodes(tree)
                if pruned is None:
                    continue

                _walk_marker_expr_tree(
                    node=pruned,
                    parent_expr_id=None,
                    requirement_id=req_dist_id,
                    expressions_acc=all_expressions,
                    comparisons_acc=all_comparisons,
                    logicals_acc=all_logicals
                )
            except Exception as exc:
                logger.error(f"Failed to process marker tree for '{req_str}': {exc}")

    if not all_expressions and not all_comparisons and not all_logicals:
        return None, None, None

    return all_expressions, all_comparisons, all_logicals
