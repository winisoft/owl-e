from dataclasses import dataclass
from typing import Literal, Optional


@dataclass
class PackageExtra:
    distribution: str
    version_id: int
    extra_name: str
    description: Optional[str] = None

@dataclass
class PackageExtraRequirement:
    distribution: str
    version_id: int
    extra_name: str
    required_dist_id: int
