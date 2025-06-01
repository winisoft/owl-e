import json
import logging
import os
from typing import List, Tuple
from config import COMMIT_EVERY, INPUT_DIR, OUTPUT_DIR
from db_ops.packages import update_package
from helpers.csv import read_csv_lines
from models.FetchResult import FetchResult
from utils.db_connection import get_db_connection
from utils.logging import persist_failed_response
from utils.pypi_fetcher import JitteredRateLimiter, fetch_pypi_metadata, safe_json_dump

MAX_FILE_SIZE = 50 * 1024 * 1024  # 50 MB
OUTPUT_DIR = "failed_payloads"

logger = logging.getLogger(__name__)
# Configure session with retry strategy
_session = None
os.makedirs(OUTPUT_DIR, exist_ok=True)


def process_api_data():
    processed = 0
    successes: List[Tuple[str, str]] = []
    failures: List[Tuple[str, str]] = []
    rate_limiter = JitteredRateLimiter() 
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    success_log_path = os.path.join(OUTPUT_DIR, "successes.log")
    failure_log_path = os.path.join(OUTPUT_DIR, "failures.log")

    # Open the log files once (append mode) and keep them open
    with open(success_log_path, "a", encoding="utf-8") as success_fh, \
         open(failure_log_path, "a", encoding="utf-8") as failure_fh:

        try:
            for file in os.listdir(INPUT_DIR):
                fullpath = os.path.join(INPUT_DIR, file)
                print(f"Processing file: {file}")

                with get_db_connection() as conn:
                    cursor = conn.cursor()

                    for distribution, version_str in read_csv_lines(fullpath):
                        rate_limiter.wait()

                        try:
                            result: FetchResult = fetch_pypi_metadata(distribution, version_str)
                        except Exception as e:
                            reason = f"fetch package info: {type(e).__name__}: {e}"
                            logger.warning(f"{distribution}=={version_str} → failure: {reason}")
                            failures.append((distribution, version_str, reason))
                            failure_fh.write(f"{distribution},{version_str},{reason}\n")
                            failure_fh.flush()
                            continue

                        if result.data is None:
                            reason = result.error or "unknown failure"
                            logger.error(f"{distribution}=={version_str}: {reason}")
                            failures.append((distribution, version_str, reason))
                            failure_fh.write(f"{distribution},{version_str},{reason}\n")
                            failure_fh.flush()

                            # NEW: capture error text instead of response payload
                            persist_failed_response(distribution, version_str, {"error": reason})
                            continue


                        try:
                            json_str = safe_json_dump(result.data)
                            succeeded = update_package(cursor, distribution, version_str, json_str)
                        except Exception as e:
                            reason = f"serialization or db error: {type(e).__name__}: {e}"
                            distribution = distribution if 'distribution' in locals() else "<unknown>"
                            version_str = version_str if 'version_str' in locals() else "<unknown>"
                            logger.error(f"Update failed for {distribution}=={version_str}: {reason}")
                            failures.append((distribution, version_str, reason))
                            failure_fh.write(f"{distribution},{version_str},{reason}\n")
                            failure_fh.flush()
                            persist_failed_response(distribution, version_str, result.data)
                            continue

                        if not succeeded:
                            reason = f"Success false from update_package"
                            logger.error(f"Failed to update package for {distribution}=={version_str}: {reason}")
                            failures.append((distribution, version_str, reason))
                            failure_fh.write(f"{distribution},{version_str},{reason}\n")
                            failure_fh.flush()
                            persist_failed_response(distribution, version_str, result.data)
                            continue

                        # 3) Mark as success
                        processed += 1
                        successes.append((distribution, version_str))
                        success_fh.write(f"{distribution},{version_str}\n")
                        success_fh.flush()

                        # 4) Periodic commit + log flush
                        if processed % COMMIT_EVERY == 0:
                            conn.commit()
                            logger.info(f"Committed {processed} processed entries")

                    # Final commit for leftovers in this file
                    conn.commit()

                print(f"  Finished {file}\n")

        except Exception as e:
            reason = f"{type(e).__name__}: {e}"
            print(f"Failed to create package for {distribution}=={version_str}: {reason}")
            failures.append((distribution, version_str, reason))
            failure_fh.write(f"{distribution},{version_str},{reason}\n")
            failure_fh.flush()

    print("All done.")
    print(f"Total successes: {len(successes)}; failures: {len(failures)}")

if __name__ == "__main__":
    process_api_data()