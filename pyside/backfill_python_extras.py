"""
Backfill Requires-Python and extras for packages whose dependency rows are already written.

`process_requires_python` and `insert_extras` were added to the derivation pass after most of the
454,287 packages had already been marked done, so `package_python_requirements` and
`package_extras` hold only a handful of rows each. A plain re-run is not an option:
`package_dist_requirements` has no unique key beyond its surrogate primary key, so it would gain a
second copy of all 5.2 million rows. This runs the two missing steps only, tracked on its own
worklist column so it is resumable.

    python backfill_python_extras.py [limit]
"""

import importlib.util
import logging
import os
import sys
import time

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

logging.basicConfig(level=logging.WARNING, format="%(asctime)s %(levelname)s %(message)s")
logging.getLogger("processors.infos").setLevel(logging.INFO)

_spec = importlib.util.spec_from_file_location(
    "infos_standalone",
    os.path.join(os.path.dirname(os.path.abspath(__file__)), "processors", "infos.py"),
)
_infos = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(_infos)


if __name__ == "__main__":
    limit = int(sys.argv[1]) if len(sys.argv) > 1 else None
    print(f"database = {os.getenv('DB_NAME', 'owle_dev')}, limit = {limit or 'none'}")
    started = time.time()
    _infos.backfill_python_and_extras(limit=limit)
    print(f"elapsed: {time.time() - started:.1f}s")
