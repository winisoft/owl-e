import pytest
from packaging.markers import Marker
from utils.markers import (
    prune_extra_nodes,
    has_non_extra_conditions,
    normalize_marker_node,
    extract_extras_from_marker,
    extract_primary_extra,
    simplify_marker_for_logging,
    marker_applies_to_extra,
    compare_if_true,
    is_pure_extra_marker
)
from models.marker_types import MarkerComparison, MarkerLogical

# Test data
SIMPLE_EXTRA_MARKER = Marker('extra == "gpu"')
COMPLEX_EXTRA_MARKER = Marker('extra == "gpu" or extra == "cuda"')
MIXED_MARKER = Marker('python_version >= "3.7" and extra == "dev"')
COMPLEX_MIXED_MARKER = Marker('python_version >= "3.7" and (extra == "dev" or extra == "test")')

class TestPruneExtraNodes:

    def test_simple_extra_node(self):
        node = MarkerComparison(left="extra", op="==", right="gpu")
        assert prune_extra_nodes(node) is None


    def test_non_extra_node(self):
        node = MarkerComparison(left="python_version", op=">=", right="3.8")
        assert prune_extra_nodes(node) == node


    def test_logical_and_with_extra(self):
        node = MarkerLogical(
            operator="and",
            children=[
                MarkerComparison(left="extra", op="==", right="gpu"),
                MarkerComparison(left="python_version", op=">=", right="3.7")
            ]
        )
        pruned = prune_extra_nodes(node)
        assert isinstance(pruned, MarkerComparison)
        assert pruned.left == "python_version"


    def test_logical_or_with_extra(self):
        node = MarkerLogical(
            operator="or",
            children=[
                MarkerComparison(left="extra", op="==", right="gpu"),
                MarkerComparison(left="python_version", op=">=", right="3.7")
            ]
        )
        pruned = prune_extra_nodes(node)
        assert isinstance(pruned, MarkerComparison)
        assert pruned.left == "python_version"


    def test_singleton_list(self):
        # Previously: ['extra', '==', 'gpu'] — now wrapped correctly
        node = MarkerLogical(operator="or", children=[
            MarkerLogical(operator="and", children=[
                MarkerComparison(left="extra", op="==", right="gpu")
            ])
        ])
        pruned = prune_extra_nodes(node)
        assert pruned is None

class TestHasNonExtraConditions:
    def test_pure_extra_marker(self):
        assert not has_non_extra_conditions(SIMPLE_EXTRA_MARKER)
        assert not has_non_extra_conditions(COMPLEX_EXTRA_MARKER)

    def test_mixed_marker(self):
        assert has_non_extra_conditions(MIXED_MARKER)
        assert has_non_extra_conditions(COMPLEX_MIXED_MARKER)


class TestNormalizeMarkerNode:
    def test_comparison_node(self):
        node = ["python_version", "<", "3.10"]
        expected = ["python_version", "<", "3.10"]
        assert normalize_marker_node(node) == expected

    def test_normalize_marker_node_preserves_logical_structure(self):
        raw = [["python_version", ">=", "3.7"], "AND", ["extra", "==", "gpu"]]
        expected = [
            ["python_version", ">=", "3.7"],
            "and",
            ["extra", "==", "gpu"]
        ]
        assert normalize_marker_node(raw) == expected


    def test_normalize_marker_node_handles_singleton_list_wrapping(self):
        raw = [[["python_version", "<", "3.11"]]]
        expected = ["python_version", "<", "3.11"]
        assert normalize_marker_node(raw) == expected


    def test_normalize_marker_node_recursively_normalizes_tuples_and_case(self):
        raw = (("python_version", ">=", "3.7"), "Or", ("extra", "==", "gpu"))
        expected = [
            ["python_version", ">=", "3.7"],
            "or",
            ["extra", "==", "gpu"]
        ]
        assert normalize_marker_node(raw) == expected

    def test_normalize_marker_node_falls_back_on_invalid_node(self):
        raw = "python_version >= 3.7"  # not a list/tuple
        assert normalize_marker_node(raw) == "python_version >= 3.7"

    def test_logical_node(self):
        node = [["python_version", ">=", "3.7"], "and", ["extra", "==", "gpu"]]
        expected = [["python_version", ">=", "3.7"], "and", ["extra", "==", "gpu"]]
        assert normalize_marker_node(node) == expected

    def test_singleton_list(self):
        node = [["python_version", ">=", "3.7"]]
        expected = ["python_version", ">=", "3.7"]
        assert normalize_marker_node(node) == expected

class TestExtractExtrasFromMarker:
    def test_simple_extra(self):
        assert extract_extras_from_marker(SIMPLE_EXTRA_MARKER) == {"gpu"}

    def test_multiple_extras(self):
        assert extract_extras_from_marker(COMPLEX_EXTRA_MARKER) == {"gpu", "cuda"}

    def test_mixed_marker(self):
        assert extract_extras_from_marker(MIXED_MARKER) == {"dev"}

    def test_no_extras(self):
        marker = Marker('python_version >= "3.7"')
        assert extract_extras_from_marker(marker) == set()

class TestExtractPrimaryExtra:
    def test_simple_extra(self):
        assert extract_primary_extra(SIMPLE_EXTRA_MARKER) == "gpu"

    def test_multiple_extras(self):
        # Note: The order is not guaranteed, but it should return one of the extras
        result = extract_primary_extra(COMPLEX_EXTRA_MARKER)
        assert result in {"gpu", "cuda"}

    def test_no_extras(self):
        marker = Marker('python_version >= "3.7"')
        assert extract_primary_extra(marker) is None

class TestSimplifyMarkerForLogging:
    def test_short_marker(self):
        assert simplify_marker_for_logging(SIMPLE_EXTRA_MARKER) == 'extra == "gpu"'

    def test_long_marker(self):
        long_marker = Marker(' and '.join(['python_version >= "3.7"'] * 10))
        result = simplify_marker_for_logging(long_marker)
        assert len(result) == 100
        assert result.endswith("...")

class TestMarkerAppliesToExtra:
    def test_matching_extra(self):
        assert marker_applies_to_extra(SIMPLE_EXTRA_MARKER, "gpu")
        assert marker_applies_to_extra(COMPLEX_EXTRA_MARKER, "cuda")

    def test_non_matching_extra(self):
        assert not marker_applies_to_extra(SIMPLE_EXTRA_MARKER, "cuda")
        assert not marker_applies_to_extra(MIXED_MARKER, "gpu")

class TestCompareIfTrue:
    def test_true_value(self):
        assert compare_if_true(True) == "COMPARATIVE"

    def test_false_value(self):
        assert compare_if_true(False) == "LOGICAL"

class TestIsPureExtraMarker:
    def test_pure_extra_marker(self):
        assert is_pure_extra_marker(SIMPLE_EXTRA_MARKER)
        assert is_pure_extra_marker(COMPLEX_EXTRA_MARKER)

    def test_mixed_marker(self):
        assert not is_pure_extra_marker(MIXED_MARKER)
        assert not is_pure_extra_marker(COMPLEX_MIXED_MARKER)
