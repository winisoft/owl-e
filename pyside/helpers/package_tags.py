

from typing import List

from db_ops.package_tags import insert_package_tag
from helpers.csv import read_classifiers_csv
from models.owle.tags import PackageTag


def process_package_tags(cursor, classifiers: List[str], distribution: str, version_id: int):
    """
    Process package tags based on classifiers and insert them into the database.
    """
    package_tags = get_package_tags(classifiers, distribution, version_id)
            
    insert_package_tag(cursor, package_tags)
    

def get_package_tags(classifiers: List[str], distribution: str, version_id: int) -> List[PackageTag]:
    """
    Retrieve package tags based on classifiers, distribution, and version ID.
    """
    package_tags = []

    for classifier in classifiers:
            
        tag_id = read_classifiers_csv(classifier)
        
        if not tag_id is None:
            package_tag = PackageTag(
                distribution=distribution,
                version_id=version_id,
                tag_id=tag_id
            )

            package_tags.append(package_tag)

    return package_tags
