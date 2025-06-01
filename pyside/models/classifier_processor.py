from ..db_ops.tags import get_or_create_tag
from ..db_ops.tag_relations import insert_tag_relation
from ..db_ops.package_tags import associate_tag_with_package

from typing import Optional, List


class ClassifierTagProcessor:
    def __init__(self, source: str = "PYPI", user_id: Optional[int] = None):
        self.source = source
        self.user_id = user_id

    def process_classifiers(self, cursor, package_id: int, classifiers: List[str]) -> None:
        for classifier in classifiers:
            segments = [seg.strip() for seg in classifier.split("::") if seg.strip()]
            parent_id = None

            for segment in segments:
                tag_id = get_or_create_tag(cursor, segment, self.source, self.user_id)

                if parent_id is not None and parent_id != tag_id:
                    insert_tag_relation(cursor, parent_id, tag_id)

                parent_id = tag_id

            if parent_id is not None:
                associate_tag_with_package(cursor, package_id, parent_id)