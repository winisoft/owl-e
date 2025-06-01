
from dataclasses import dataclass
from typing import Optional
from packaging.specifiers import Specifier

@dataclass
class NormalizedSpecifier:
    target_package_id: Optional[int]
    operator_override: Optional[str]
    wildcard_segment: Optional[str]
    safe_version: str
    spec: Specifier  # raw specifier for tracing
