import pytest
from helpers.version_specifiers import parse_version_specifier, VersionSpecifierPart


def test_parse_single_strict_equal():
    result = parse_version_specifier("==3.9.1")
    assert result == [VersionSpecifierPart("EQ", "3.9.1", "NONE")]

def test_parse_compatible_and_not_equal():
    result = parse_version_specifier("~=3.6,!=3.6.5")
    assert result == [
        VersionSpecifierPart("COMPATIBLE", "3.6.0", "NONE"),
        VersionSpecifierPart("NE", "3.6.5", "NONE")
    ]

def test_parse_wildcard_ne():
    result = parse_version_specifier("!=3.9.*")
    assert result == [VersionSpecifierPart("NE", "3.9", "MICRO")]

def test_parse_multiple_versions():
    result = parse_version_specifier(">=3.6,<3.10")
    assert result == [
        VersionSpecifierPart("GE", "3.6.0", "NONE"),
        VersionSpecifierPart("LT", "3.10.0", "NONE")
    ]

def test_parse_wildcard_minor():
    result = parse_version_specifier("!=3.*")
    assert result == [VersionSpecifierPart("NE", "3", "MINOR")]

def test_parse_exact():
    result = parse_version_specifier("===3.9.1+local")
    assert result == [VersionSpecifierPart("EXACT", "3.9.1+local", "NONE")]
