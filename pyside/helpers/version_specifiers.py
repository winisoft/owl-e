import re
from dataclasses import dataclass
from typing import List, Optional, Literal

Operator = Literal['LT', 'LE', 'EQ', 'NE', 'GE', 'GT', 'COMPATIBLE', 'EXACT']
WildcardSegment = Literal['MAJOR', 'MINOR', 'MICRO', 'NONE']


@dataclass
class VersionSpecifierPart:
    operator: Operator
    version_str: Optional[str]  # May be None if it's a wildcard-only spec
    wildcard_segment: WildcardSegment


def parse_version_specifier(spec: str) -> List[VersionSpecifierPart]:
    pattern = re.compile(
        r"""(?x)
        (?P<operator>===|==|!=|>=|<=|<|>|~=)       # specifier operator
        \s*
        (?P<version>[^\s,]+)                       # version part, including wildcards
        """
    )
    
    results: List[VersionSpecifierPart] = []

    for match in pattern.finditer(spec):
        op_str = match.group("operator")
        version_raw = match.group("version")
        
        # Determine operator enum
        operator_map = {
            '==': 'EQ', '!=': 'NE', '>=': 'GE', '<=': 'LE',
            '>': 'GT', '<': 'LT', '~=': 'COMPATIBLE', '===': 'EXACT'
        }
        operator: Operator = operator_map[op_str]
        
        # Determine wildcard segment
        wildcard_segment: WildcardSegment = 'NONE'
        version_str = version_raw
        
        if version_raw.endswith(".*"):
            segments = version_raw.split(".")
            wildcard_segment = {1: 'MAJOR', 2: 'MINOR', 3: 'MICRO'}.get(len(segments), 'NONE')
            version_str = ".".join(segments[:-1])
        else:
            segments = version_raw.split(".")
            while len(segments) < 3:
                segments.append("0")  # Normalize to micro version
            version_str = ".".join(segments)

        results.append(VersionSpecifierPart(
            operator=operator,
            version_str=version_str,
            wildcard_segment=wildcard_segment
        ))
    
    return results
