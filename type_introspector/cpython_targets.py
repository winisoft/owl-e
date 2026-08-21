import logging
import sys


logger = logging.getLogger(__name__)


# Each entry is one conda environment: the interpreter to run under, and its own stdlib directory.
#
# `path` points at the environment's own Lib rather than a downloaded CPython source tree on
# purpose. Introspection imports from the *running* interpreter, so enumerating module names from
# any other tree risks discovering names the running interpreter does not have (silently skipped)
# or missing ones it does. Pointing both at the same installation makes that class of mismatch
# impossible rather than merely unlikely.
CPYTHON_DATA = [
    { "id": 874741, "version": "3.14.7",  "path": r"C:\Users\wini\miniconda3\envs\py3-14-7\Lib"  },
    { "id": 874749, "version": "3.13.15", "path": r"C:\Users\wini\miniconda3\envs\py3-13-15\Lib" },
    { "id": 874766, "version": "3.12.13", "path": r"C:\Users\wini\miniconda3\envs\py3-12-13\Lib" },
    { "id": 874781, "version": "3.11.15", "path": r"C:\Users\wini\miniconda3\envs\py3-11-15\Lib" },
    { "id": 874798, "version": "3.10.20", "path": r"C:\Users\wini\miniconda3\envs\py3-10-20\Lib" },
    { "id": 874819, "version": "3.9.25",  "path": r"C:\Users\wini\miniconda3\envs\py3-9-25\Lib"  },
    { "id": 874845, "version": "3.8.20",  "path": r"C:\Users\wini\miniconda3\envs\py3-8-20\Lib"  },
    { "id": 874867, "version": "3.7.16",  "path": r"C:\Users\wini\miniconda3\envs\py3-7-16\Lib"  },
    { "id": 874886, "version": "3.6.13",  "path": r"C:\Users\wini\miniconda3\envs\py3-6-13\Lib"  },
]


def running_version() -> str:
    return "%d.%d.%d" % sys.version_info[:3]


def current_targets():
    """
    The entries matching the interpreter this process is running under - normally exactly one.

    Introspection reads the running interpreter, so an entry is only correct when the process
    executing it *is* that version. Selecting here rather than relying on which entries happen to
    be uncommented means running under the wrong environment fails loudly, instead of writing a
    whole stdlib's worth of type nodes under someone else's `py_version_id`.
    """
    running = running_version()
    matched = [d for d in CPYTHON_DATA if d["version"] == running]
    if not matched:
        known = ", ".join(d["version"] for d in CPYTHON_DATA)
        raise RuntimeError(
            f"running under Python {running}, which is not a declared target. "
            f"Activate one of: {known}"
        )
    return matched
