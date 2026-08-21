"""
Runner for phase B - deriving requirements from banked PyPI metadata.

Loads `processors/infos.py` directly rather than importing it as `processors.infos`, because
`processors/__init__.py` eagerly imports every sibling and one of them (`prompts.py`) raises
`NameError: Int32` on import. That is unrelated to this job and not worth fixing to run it.

Usage:
    DB_NAME=owle_test_archive python derive_requirements.py [limit]
"""

import importlib.util
import logging
import os
import sys
import time

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

logging.basicConfig(
    level=logging.WARNING,
    format="%(asctime)s %(levelname)s %(message)s",
)
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
    _infos.process_info_data(limit=limit)
    print(f"elapsed: {time.time() - started:.1f}s")
