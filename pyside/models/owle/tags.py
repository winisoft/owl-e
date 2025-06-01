from dataclasses import dataclass
from typing import Optional, Literal, List


@dataclass
class Tag:
    id: Optional[int]
    name: str
    description: Optional[str] = None
    source: Literal['pypi', 'system', 'user']
    userId: Optional[int] = None

@dataclass
class TagRelation:
    parentTagId: int
    childTagId: int

@dataclass
class PackageTag:
    distribution: str
    version_id: int
    tag_id: int

@dataclass
class DistributionTag:
    subject_id: int
    tag_id: int

@dataclass
class WorkflowTag:
    subject_id: int
    tag_id: int

@dataclass
class StepTag:
    subject_id: int
    tag_id: int

@dataclass
class PhaseTag:
    subject_id: int
    tag_id: int

@dataclass
class ToolTag:
    subject_id: int
    tag_id: int