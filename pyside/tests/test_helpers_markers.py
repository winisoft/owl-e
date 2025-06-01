import pytest
from unittest.mock import Mock
from packaging.requirements import Requirement
from packaging.markers import Marker

from helpers.markers import (
    _walk_marker_expr_tree,
    process_marker_set
)
from models.marker_types import (
    EnvMarkerExpression,
    EnvMarkerComparison,
    EnvMarkerLogical,
    MarkerComparison,
    MarkerLogical
)

class TestWalkMarkerExprTree:
    def test_simple_comparison(self):
        node = MarkerComparison(left="python_version", op=">=", right="3.7")
        expressions = []
        comparisons = []
        logicals = []

        expr_id = _walk_marker_expr_tree(
            node=node,
            parent_expr_id=None,
            requirement_id=1,
            expressions_acc=expressions,
            comparisons_acc=comparisons,
            logicals_acc=logicals
        )

        assert len(expressions) == 1
        assert len(comparisons) == 1
        assert len(logicals) == 0
        assert isinstance(expressions[0], EnvMarkerExpression)
        assert isinstance(comparisons[0], EnvMarkerComparison)
        assert expressions[0].mode == "COMPARATIVE"
        assert comparisons[0].variable == "python_version"
        assert comparisons[0].operator == ">="
        assert comparisons[0].value == "3.7"

    def test_logical_expression(self):
        node = MarkerLogical(
            operator="and",
            children=[
                MarkerComparison(left="python_version", op=">=", right="3.7"),
                MarkerComparison(left="python_version", op="<", right="3.11")
            ]
        )
        expressions = []
        comparisons = []
        logicals = []

        expr_id = _walk_marker_expr_tree(
            node=node,
            parent_expr_id=None,
            requirement_id=1,
            expressions_acc=expressions,
            comparisons_acc=comparisons,
            logicals_acc=logicals
        )

        assert len(expressions) == 3  # root + 2 children
        assert len(comparisons) == 2
        assert len(logicals) == 1
        assert expressions[0].mode == "LOGICAL"
        assert logicals[0].operator == "AND"

    def test_invalid_node_type(self):
        node = "invalid"
        expressions = []
        comparisons = []
        logicals = []

        with pytest.raises(TypeError):
            _walk_marker_expr_tree(
                node=node,
                parent_expr_id=None,
                requirement_id=1,
                expressions_acc=expressions,
                comparisons_acc=comparisons,
                logicals_acc=logicals
            )

class TestProcessMarkerSet:
    def test_empty_requires_dist(self):
        result = process_marker_set(None)
        assert result == (None, None, None)

    def test_simple_marker(self):
        requires_dist = [(1, 'numpy>=1.20.0; python_version >= "3.7"')]
        expressions, comparisons, logicals = process_marker_set(requires_dist)
        
        assert len(expressions) > 0
        assert len(comparisons) > 0
        assert len(logicals) == 0
        assert all(isinstance(e, EnvMarkerExpression) for e in expressions)
        assert all(isinstance(c, EnvMarkerComparison) for c in comparisons)

    def test_complex_marker(self):
        requires_dist = [(1, 'numpy>=1.20.0; python_version >= "3.7" and python_version < "3.11"')]
        expressions, comparisons, logicals = process_marker_set(requires_dist)
        
        assert len(expressions) > 0
        assert len(comparisons) > 0
        assert len(logicals) > 0
        assert all(isinstance(e, EnvMarkerExpression) for e in expressions)
        assert all(isinstance(c, EnvMarkerComparison) for c in comparisons)
        assert all(isinstance(l, EnvMarkerLogical) for l in logicals)

    def test_invalid_requirement(self):
        requires_dist = [(1, "invalid requirement")]
        expressions, comparisons, logicals = process_marker_set(requires_dist)
        
        assert expressions is None
        assert comparisons is None
        assert logicals is None

    def test_requirement_without_marker(self):
        requires_dist = [(1, "numpy>=1.20.0")]
        expressions, comparisons, logicals = process_marker_set(requires_dist)
        
        assert expressions is None
        assert comparisons is None
        assert logicals is None
