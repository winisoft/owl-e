from typing import NamedTuple, Optional


class FetchResult(NamedTuple):
    data: Optional[dict]
    error: Optional[str]