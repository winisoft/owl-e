import pytest
from unittest.mock import Mock
from packaging.specifiers import Specifier, SpecifierSet, InvalidSpecifier
from packaging.requirements import Requirement
from collections import namedtuple

FakeReq = namedtuple("FakeReq", ["name"])
from db_ops.packages import get_compatible_bounds, get_single_bound
from utils.specifiers import (
    normalize_specifier_info,
    get_version_bounds_for_operator,
    split_operator_and_version
)
from models.NormalizedSpecifier import NormalizedSpecifier
from utils.version import sanitize_wildcard_version

class TestNormalizeSpecifierInfo:
    @pytest.fixture
    def mock_cursor(self):
        cursor = Mock()
        cursor.fetchone.return_value = (1,)  # Mock package_id
        return cursor

    def test_simple_version_specifier(self, mock_cursor):
        spec = Specifier(">=1.20.0")
        req = Requirement("numpy>=1.20.0")
        
        result = normalize_specifier_info(mock_cursor, spec, req)
        
        assert isinstance(result, NormalizedSpecifier)
        assert result.target_package_id == 1
        assert result.operator_override is None
        assert result.wildcard_segment is None
        assert result.safe_version == "1.20.0"


    def test_simple_version_specifier(self, mock_cursor):
        spec = Specifier(">=1.20.0")
        req = FakeReq(name="numpy")

        result = normalize_specifier_info(mock_cursor, spec, req)

        assert result.target_package_id == 1
        assert result.operator_override is 'GE'
        assert result.wildcard_segment is 'NONE'
        assert result.safe_version == "1.20.0"



    def test_version_not_found(self, mock_cursor):
        mock_cursor.fetchone.return_value = None
        spec = Specifier(">=1.20.0")
        req = Requirement("numpy>=1.20.0")
        
        result = normalize_specifier_info(mock_cursor, spec, req)
        
        assert isinstance(result, NormalizedSpecifier)
        assert result.target_package_id is None

    def test_comparative_operator_fallback(self, mock_cursor):
        mock_cursor.fetchone.side_effect = [None, (2,)]  # First lookup fails, second succeeds
        spec = Specifier(">1.20.0")
        req = Requirement("numpy>1.20.0")
        
        result = normalize_specifier_info(mock_cursor, spec, req)
        
        assert isinstance(result, NormalizedSpecifier)
        assert result.target_package_id == 2
        assert result.operator_override == "GE"

class TestGetVersionBoundsForOperator:
    def test_compatible_operator(self):
        result = get_version_bounds_for_operator(
            operator="COMPATIBLE",
            epoch=0,
            major=1,
            minor=2,
            micro=3
        )
        assert len(result) == 2
        assert result[0] == (0, 1, 2, 3)  # lower bound
        assert result[1] == (0, 1, 3, 0)  # upper bound

    def test_single_bound_operators(self):
        operators = ["LT", "LE", "GT", "GE"]
        for op in operators:
            result = get_version_bounds_for_operator(
                operator=op,
                epoch=0,
                major=1,
                minor=2,
                micro=3
            )
            assert len(result) == 1
            assert result[0] == (0, 1, 2, 3)


class TestGetCompatibleBounds:
    def test_with_minor_version(self):
        lower, upper = get_compatible_bounds(
            epoch=0,
            major=1,
            minor=2,
            micro=3
        )
        assert lower == (0, 1, 2, 3)
        assert upper == (0, 1, 3, 0)

    def test_without_minor_version(self):
        lower, upper = get_compatible_bounds(
            epoch=0,
            major=1,
            minor=None,
            micro=None
        )
        assert lower == (0, 1, 0, 0)
        assert upper == (0, 2, 0, 0)

    def test_without_micro_version(self):
        lower, upper = get_compatible_bounds(
            epoch=0,
            major=1,
            minor=2,
            micro=None
        )
        assert lower == (0, 1, 2, 0)
        assert upper == (0, 1, 3, 0)

class TestGetSingleBound:
    def test_all_operators(self):
        operators = ["LT", "LE", "GT", "GE"]
        for op in operators:
            result = get_single_bound(
                operator=op,
                epoch=0,
                major=1,
                minor=2,
                micro=3
            )
            assert result == (0, 1, 2, 3)
