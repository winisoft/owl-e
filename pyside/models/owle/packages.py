from dataclasses import dataclass
from typing import Optional


@dataclass
class Distribution:
    name: str

@dataclass
class Package:
    id: Optional[int]
    distribution: str
    summary: str
    description: str
    description_content_type: Optional[str] = None




