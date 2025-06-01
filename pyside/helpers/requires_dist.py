from dataclasses import dataclass
import logging
from typing import List, Optional
from packaging.requirements import Requirement
from db_ops.markers import insert_env_marker_comparison, insert_env_marker_expression, insert_env_marker_logical
from models.RequiredDistSpecifier import RequiredDistSpecifier
from models.marker_types import (
    MarkerComparison,
    MarkerExpr,
    MarkerLogical,
)
from utils.markers import build_marker_expr_tree, extract_required_extras, prune_extra_nodes, has_non_extra_conditions, normalize_marker_node
from utils.specifiers import normalize_specifier_info


logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


@dataclass
class RequiresDistInsertItem:
    specifier: RequiredDistSpecifier
    marker_expr: Optional[MarkerExpr]


def process_requires_dist_specifiers(
    cursor,
    distribution: str,
    dependent_package_id: int,
    raw_requires_dist: List[str]
):
    """
    Parse and normalize raw requires_dist strings into specifier rows and associated marker expressions.
    """
    if not raw_requires_dist:
        return None

    requires_dist_specifiers: List[RequiredDistSpecifier] = []
    marker_exprs: List[Optional[MarkerExpr]] = []

    try:
        for req_str in raw_requires_dist:
            try:
                req = Requirement(req_str)
            except Exception as e:
                logger.warning("Invalid requirement string: %r\nCause: %r", req_str, e.__cause__)
                continue

            marker_expr: Optional[MarkerExpr] = None

            if req.marker and has_non_extra_conditions(req.marker):
                try:
                    normalized = normalize_marker_node(req.marker._markers)
                    tree = build_marker_expr_tree(normalized)
                    pruned = prune_extra_nodes(tree)
                    if pruned:
                        marker_expr = pruned
                except Exception as marker_exc:
                    logger.error(
                        f"Failed to process marker for requirement '{req_str}' in {distribution}=={dependent_package_id}: {marker_exc}",
                        exc_info=True
                    )
                    return None

            # For each version specifier, process extras and emit rows
            for spec in req.specifier:
                try:
                    norm = normalize_specifier_info(cursor, spec, req)
                    extras = extract_required_extras(req)
                except Exception as spec_exc:
                    logger.error(
                        f"Failed to process specifier for requirement '{req_str}' in {distribution}=={dependent_package_id}: {spec_exc}",
                        exc_info=True
                    )
                    return None

                if not extras:
                    requires_dist_specifiers.append(RequiredDistSpecifier(
                        dependent_distribution=distribution,
                        dependent_package_id=dependent_package_id,
                        target_distribution=req.name,
                        target_package_id=norm.target_package_id,
                        operator=norm.operator_override,
                        wildcard_segment=norm.wildcard_segment,
                        target_extra_name=None,
                        raw=req_str
                    ))
                    marker_exprs.append(marker_expr)
                else:
                    for extra in extras:
                        requires_dist_specifiers.append(RequiredDistSpecifier(
                            dependent_distribution=distribution,
                            dependent_package_id=dependent_package_id,
                            target_distribution=req.name,
                            target_package_id=norm.target_package_id,
                            operator=norm.operator_override,
                            wildcard_segment=norm.wildcard_segment,
                            target_extra_name=extra,
                            raw=req_str
                        ))
                        marker_exprs.append(marker_expr)

        return [
            RequiresDistInsertItem(spec, marker)
            for spec, marker in zip(requires_dist_specifiers, marker_exprs)
        ]

    except Exception as e:
        logger.error(f"Failed to process requires_dist for {distribution}=={dependent_package_id}: {e}", exc_info=True)
        return None



def insert_marker_expr(
    cursor,
    expr: MarkerExpr,
    requirement_id: int,
    parent_id: Optional[int] = None
) -> int:
    if isinstance(expr, MarkerComparison):
        
        expression_id = insert_env_marker_expression(cursor, requirement_id, parent_id, mode='COMPARATIVE')

        insert_env_marker_comparison(
            cursor,
            expression_id,
            variable=expr.variable,
            operator=expr.operator,
            value=expr.value
        )

        return expression_id

    elif isinstance(expr, MarkerLogical):
        expression_id = insert_env_marker_expression(cursor, requirement_id, parent_id, mode='LOGICAL')
        left_id = insert_marker_expr(cursor, expr.children[0], requirement_id, parent_id=expression_id)
        right_id = insert_marker_expr(cursor, expr.children[1], requirement_id, parent_id=expression_id)
        insert_env_marker_logical(cursor, expression_id, expr.operator.upper(), left_id, right_id)
        return expression_id

    else:
        raise TypeError(f"Unsupported marker expression type: {type(expr)}")


