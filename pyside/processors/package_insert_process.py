from helpers.csv import read_csv_lines
from .helpers.version_utils import process_version_entry, write_per_file_outputs


def process_csv_file_4pi(file_path: str, file_name: str, output_dir: str, start_id: int) -> int:
    """
    Full processing pipeline for a single CSV file.(for package inserts)

    Handles:
      - Reading distribution/version pairs,
      - Skipping/recording failures,
      - Assigning IDs,
      - Routing to the entry processor for insert generation,
      - Emitting per-file SQL outputs.

    Returns:
        The next available ID after processing this file.
    """
    version_lines = []
    package_lines = []
    overflow_lines = []
    failures = []

    current_id = start_id
    rows, csv_failures = read_csv_lines(file_path)
    failures.extend(csv_failures)

    for distribution, version_str in rows:
        result, failure = process_version_entry(distribution, version_str, current_id)
        if failure:
            failures.append(failure)
            continue

        version_lines.append(result["version_sql"])
        package_lines.append(result["package_sql"])
        overflow_lines.extend(result["overflow_sqls"])
        current_id += 1

    write_per_file_outputs(file_name, output_dir, version_lines, package_lines, overflow_lines, failures)
    return current_id
