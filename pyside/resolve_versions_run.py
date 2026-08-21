"""
Runner for stage 1 - resolving each package's version.

Loads the processor by path for the same reason `derive_requirements.py` does: importing
`processors` as a package pulls in every sibling, and `prompts.py` raises NameError on import.

Usage:
    DB_NAME=owle_test_archive python resolve_versions_run.py [limit]
"""

import importlib.util
import logging
import os
import sys
import time

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
logging.basicConfig(level=logging.WARNING, format="%(asctime)s %(levelname)s %(message)s")

_spec = importlib.util.spec_from_file_location(
    "resolve_versions_standalone",
    os.path.join(os.path.dirname(os.path.abspath(__file__)), "processors", "resolve_versions.py"),
)
_mod = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(_mod)

if __name__ == "__main__":
    limit = int(sys.argv[1]) if len(sys.argv) > 1 else None
    print(f"database = {os.getenv('DB_NAME', 'owle_dev')}, limit = {limit or 'none'}")
    started = time.time()
    _mod.resolve_versions(limit=limit)
    print(f"elapsed: {time.time() - started:.1f}s")
