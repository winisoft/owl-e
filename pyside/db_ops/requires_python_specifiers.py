from typing import List
from models.owle.specifiers import RequiresPythonSpecifiers, RequiredDistSpecifier

TABLE_NAME = "requires_python_specifiers"
COLUMNS = [
    "dependentDist", "dependentVersionId", "targetDistribution",
    "targetVersionId", "operator", "wildcardSegment", "targetExtra"
]


def insert_requires_python_specifiers_bulk(cursor, requires_python_specifiers_list: list[RequiresPythonSpecifiers]) -> None:
    """
    Insert multiple requires_python_specifiers records into the database.
    """
    if not requires_python_specifiers_list:
        return

    values = [
        (
            rps.dependent_dist,
            rps.dependent_version_id,
            rps.target_distribution,
            rps.target_version_id,
            rps.operator,
            rps.wildcard_segment,
            rps.target_extra
        )
        for rps in requires_python_specifiers_list
    ]

    cursor.executemany(
        "INSERT INTO `owle_db`.`requires_python_specifiers` (dependentDist, dependentVersionId, targetDistribution, targetVersionId, operator, wildcardSegment, targetExtra) VALUES (%s, %s, %s, %s, %s, %s, %s)",
        values
    )


