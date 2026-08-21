import re
import logging
import os


# Logging configuration
LOG_LEVEL = os.getenv("LOG_LEVEL", "INFO")
LOG_DIR = os.getenv("LOG_DIR", "logs")

# Ensure log directory exists
os.makedirs(LOG_DIR, exist_ok=True)

# Set up logging
log_level = getattr(logging, LOG_LEVEL.upper(), logging.INFO)
logging.basicConfig(
    level=log_level,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler(os.path.join(LOG_DIR, 'package_processing.log')),
        logging.StreamHandler()
    ]
)

# Database configuration
DB_NAME = os.getenv("DB_NAME", "owle_dev")
DB_CONFIG = {
    "host": os.getenv("DB_HOST", "localhost"),
    "user": os.getenv("DB_USER", "dev_assistant"),
    "password": os.getenv("DB_PASSWORD", "*SandboxSquabble2"),
    "database": DB_NAME,
    "autocommit": False,
    "use_unicode": True,
    "charset": "utf8mb4"
}

# Directory paths
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
INPUT_DIR = os.getenv("INPUT_DIR", os.path.join(BASE_DIR, "workdir", "in_files"))
OUTPUT_DIR = os.getenv("OUTPUT_DIR", os.path.join(BASE_DIR, "workdir", "out_files"))

# Ensure directories exist
os.makedirs(INPUT_DIR, exist_ok=True)
os.makedirs(OUTPUT_DIR, exist_ok=True)

# Maximum unsigned 64-bit integer
MAX_UINT64 = 2**64 - 1

# Regex for ANY integer of 20 or more digits (guaranteed > 2^64-1 if no leading zeros)
_OVERFLOW_REGEX = re.compile(r"^[1-9]\d{19,}$")

# Which version components to consider for overflow
OVERFLOW_COMPONENTS = [
    "epoch",
    "major",
    "minor",
    "micro",
    "phase_number",
    "post_number",
    "dev_number",
]

# Configuration constants
CLASSIFIER_MAP_PATH = os.path.join(BASE_DIR, 'classifier_map.csv')
COMMIT_EVERY = int(os.getenv("COMMIT_EVERY", "500"))
PYPI_REQUEST_TIMEOUT = int(os.getenv("PYPI_REQUEST_TIMEOUT", "10"))
MAX_RETRIES = int(os.getenv("MAX_RETRIES", "3"))
MAX_FILE_SIZE = 50 * 1024 * 1024  # 50 MB