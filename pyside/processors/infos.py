import os
from db_ops.requires_dist import insert_requires_dist
from helpers.csv import read_csv_lines
from helpers.requires_dist import RequiresDistInsertItem, process_requires_dist_specifiers
from utils.db_connection import get_db_connection
from utils.packages import build_package_info_model
from typing import List, Optional, Tuple
import logging

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)



def process_info_data():
    """
    Wrapper function to process package info and handle exceptions.
    """
    processed = 0
    OUTPUT_DIR = r"C:\Users\wini\Downloads\info_processing\workdir\outfiles"
    INPUT_DIR = r"C:\Users\wini\Downloads\info_processing\workdir\infiles"
    successes: List[Tuple[str, str]] = []
    failures: List[Tuple[str, str]] = []
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    success_log_path = os.path.join(OUTPUT_DIR, "successes.log")
    failure_log_path = os.path.join(OUTPUT_DIR, "failures.log")

    # Open the log files once (append mode) and keep them open
    with open(success_log_path, "a", encoding="utf-8") as success_fh, \
         open(failure_log_path, "a", encoding="utf-8") as failure_fh:

            for file in os.listdir(INPUT_DIR):
                fullpath = os.path.join(INPUT_DIR, file)
                print(f"Processing file: {file}")

                with get_db_connection() as conn:
                    cursor = conn.cursor()

                    for distribution, version_str in read_csv_lines(fullpath):

                        try:
                            process_package_info(cursor, distribution, version_str)
                            conn.commit()
                        except Exception as e:
                            conn.rollback()
                            reason = f"{type(e).__name__}: {e}"
                            print(f"Failed to create package for {distribution}=={version_str}: {reason}")
                            failures.append((distribution, version_str, reason))
                            failure_fh.write(f"{distribution},{version_str},{reason}\n")
                            failure_fh.flush()
                            raise

                        successes.append((distribution, version_str))
                        success_fh.write(f"{distribution},{version_str}\n")
                        success_fh.flush()


def process_package_info(cursor, distribution: str, version_str: str):

    package_info_model = build_package_info_model(cursor, distribution, version_str)
    if package_info_model is None:
        return None
    (version_id, package_info) = package_info_model

    if package_info is None:
        print(f"No valid package info found for {distribution} {version_str}")
        return None

    inserts_item: Optional[List[RequiresDistInsertItem]] = process_requires_dist_specifiers(cursor, distribution, version_id, package_info.requiresDist)

    if inserts_item:
        insert_requires_dist(cursor, inserts_item)
