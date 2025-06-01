from db_ops.versions import get_target_version_id
from helpers.csv import read_csv_lines
from helpers.package_tags import process_package_tags
from helpers.packages import format_keywords
from models.owle.packages import Package
from .helpers.pypi_fetcher import fetch_pypi_metadata
from .db import connect_db
import os


def process_csv_file_4pm(file_path: str, file_name: str, output_dir: str, start_id: int) -> int:
    """
    Full processing pipeline for a single CSV file.(for package metadata)
    - Hits the PyPI API to pull metadata for each distribution/version pair.
    - Hits the db to pull versions.id
    - Inserts into package_tags entries corresponding to classifiers. 
	    - finds the classifier text from a csv file, uses the other column for tag_id for this versionId pair
    - Populates summary, description, description_content_type; updates the package record
    - Exports keywords to a local file
    - Inserts new PackageExtra records for each extra
    - Parses RequiredPythonSpecifier @info.requires_python; inserts. 
    - Parses each requires_dist for a RequiredDistSpecifier, PackageExtraRequirements, EnvMarkerExpressions
    """
    conn = connect_db()
    cursor = conn.cursor()

    basename = os.path.splitext(os.path.basename(file_path))[0]
    failures = []

    for distribution, version_str in read_csv_lines(file_path):

        info = fetch_pypi_metadata(distribution, version_str)
        if info is None:
            failures.append([distribution, version_str])
            continue

        version_id = get_target_version_id(cursor, distribution, version_str)
        if version_id is None:
            failures.append([distribution, version_str])
            continue

        #process_requires_python_specifiers(info.requires_python, distribution, version_id)

        process_package_tags(cursor, info.classifiers, distribution, version_id)

        package = Package(
            id = version_id,
            distribution=distribution,
            summary=info.summary,
            description=info.description,
            description_content_type=info.description_content_type,
            keywords = format_keywords(info.keywords) if info.keywords else None
        )





        
        

