import pytest
from unittest.mock import Mock

from helpers.requires_dist import process_requires_dist_specifiers

class TestProcessRequiresDistSpecifiers:
    @pytest.fixture
    def mock_cursor(self):
        cursor = Mock()
        cursor.fetchone.return_value = (1,)  # Mock package_id
        return cursor

    def test_empty_requires_dist(self):
        result = process_requires_dist_specifiers(
            cursor=Mock(),
            distribution="test-pkg",
            dependent_package_id=1,
            raw_requires_dist=[]
        )
        assert result is None

    def test_simple_requirement(self, mock_cursor):
        raw_requires_dist = [("numpy>=1.20.0", 1)]
        result = process_requires_dist_specifiers(
            cursor=mock_cursor,
            distribution="test-pkg",
            dependent_package_id=1,
            raw_requires_dist=raw_requires_dist
        )
        assert isinstance(result, RequiresDistInsertGroup)
        assert len(result.requires_dist_specifiers) == 1
        spec = result.requires_dist_specifiers[0]
        assert spec.dependent_distribution == "test-pkg"
        assert spec.target_distribution == "numpy"
        assert spec.operator == "GE"
        assert spec.raw == "numpy>=1.20.0"

    def test_requirement_with_marker(self, mock_cursor):
        raw_requires_dist = [('numpy>=1.20.0; python_version >= "3.7"', 1)]
        result = process_requires_dist_specifiers(
            cursor=mock_cursor,
            distribution="test-pkg",
            dependent_package_id=1,
            raw_requires_dist=raw_requires_dist
        )
        assert isinstance(result, RequiresDistInsertGroup)
        assert len(result.requires_dist_specifiers) == 1
        assert len(result.env_marker_expressions) > 0
        assert len(result.env_marker_comparisons) > 0

    def test_requirement_with_single_extra(self, mock_cursor):
        raw_requires_dist = [('numpy[gpu]>=1.20.0', 1)]
        result = process_requires_dist_specifiers(
            cursor=mock_cursor,
            distribution="test-pkg",
            dependent_package_id=1,
            raw_requires_dist=raw_requires_dist
        )
        assert isinstance(result, RequiresDistInsertGroup)
        assert len(result.requires_dist_specifiers) == 1
        spec = result.requires_dist_specifiers[0]
        assert spec.target_extra_name == "gpu"

    def test_invalid_requirement(self, mock_cursor):
        raw_requires_dist = [("invalid requirement", 1)]
        result = process_requires_dist_specifiers(
            cursor=mock_cursor,
            distribution="test-pkg",
            dependent_package_id=1,
            raw_requires_dist=raw_requires_dist
        )
        assert isinstance(result, RequiresDistInsertGroup)
        assert len(result.requires_dist_specifiers) == 0

    def test_requirement_with_multiple_extras(self, mock_cursor):
        raw_requires_dist = [('numpy[gpu,simd]>=1.20.0', 1)]
        result = process_requires_dist_specifiers(
            cursor=mock_cursor,
            distribution="test-pkg",
            dependent_package_id=1,
            raw_requires_dist=raw_requires_dist
        )
        assert isinstance(result, RequiresDistInsertGroup)
        specs = result.requires_dist_specifiers
        assert len(specs) == 2

        # Extract extras for verification
        extras = {spec.target_extra_name for spec in specs}
        assert extras == {"gpu", "simd"}

        # Assert all common fields are preserved
        for spec in specs:
            assert spec.dependent_distribution == "test-pkg"
            assert spec.target_distribution == "numpy"
            assert spec.operator == "GE"
            assert spec.raw == "numpy[gpu,simd]>=1.20.0"

