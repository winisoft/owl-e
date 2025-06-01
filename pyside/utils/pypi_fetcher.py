import json
import random
from threading import Lock
import requests
import time
import logging
from typing import Optional, Dict, Any
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry
from pydantic import ValidationError

from main import logger

from models.pypi_data import PackageInfo
from config import PYPI_REQUEST_TIMEOUT, MAX_RETRIES

logger = logging.getLogger(__name__)

# Configure session with retry strategy
_session = None


def get_session():
    """Get a configured requests session with retry strategy."""
    global _session
    if _session is None:
        _session = requests.Session()
        retry_strategy = Retry(
            total=MAX_RETRIES,
            status_forcelist=[429, 500, 502, 503, 504],
            allowed_methods=["HEAD", "GET", "OPTIONS"],
            backoff_factor=1
        )
        adapter = HTTPAdapter(max_retries=retry_strategy)
        _session.mount("http://", adapter)
        _session.mount("https://", adapter)
    return _session


def fetch_pypi_metadata(distribution: str, version: str) -> Optional[PackageInfo]:
    """
    Queries PyPI's JSON API for a given distribution and version.
    
    Args:
        distribution: The package name
        version: The package version
    
    Returns:
        PackageInfo object or None on failure.
    """
    url = f"https://pypi.org/pypi/{distribution}/{version}/json"
    session = get_session()
    
    try:
        logger.debug(f"Fetching PyPI metadata for {distribution}=={version}")
        response = session.get(url, timeout=PYPI_REQUEST_TIMEOUT)
        response.raise_for_status()
        
        data = response.json()
        # Use model_validate for Pydantic v2 compatibility
        return PackageInfo.model_validate(data.get("info", {}))
        
    except requests.exceptions.RequestException as e:
        logger.warning(f"Failed to fetch PyPI metadata for {distribution}=={version}: {e}")
    except ValidationError as e:
        logger.error(f"Failed to parse PyPI metadata for {distribution}=={version} using Pydantic: {e}")
    except Exception as e:
        logger.error(f"Unexpected error fetching PyPI metadata for {distribution}=={version}: {e}")
    
    return None


def get_info_element(response: Dict[str, Any]) -> Optional[PackageInfo]:
    """
    Extracts the 'info' element from the PyPI response and returns it as a PackageInfo object.
    DEPRECATED: Use PackageInfo.model_validate directly in fetch_pypi_metadata.
    
    Args:
        response: The JSON response from PyPI API
        
    Returns:
        PackageInfo object with parsed data or None if validation fails
    """
    if not response or "info" not in response:
        logger.warning("Invalid PyPI response: missing 'info' element")
        return None # Return None for missing info
    
    info_data = response["info"]
    try:
        # Use model_validate for Pydantic v2 compatibility
        return PackageInfo.model_validate(info_data)
    except ValidationError as e:
        logger.error(f"Pydantic validation error in get_info_element: {e}")
        return None


class JitteredRateLimiter:
    def __init__(self, min_delay: float = 0.10, max_delay: float = 0.25):
        """
        Each wait() call will ensure that the interval from the *start* of the previous cycle
     `   to the *start* of the next cycle is a random value in [min_delay, max_delay].
        This way you never go below min_delay (0.10 s) and never above max_delay (0.25 s).
        """
        self.min_delay = min_delay
        self.max_delay = max_delay
        self._lock = Lock()
        # Initialize _next_time so that the first wait() does not block.
        self._next_time = time.monotonic()

    def wait(self):
        with self._lock:
            # Choose a new random interval in [min_delay, max_delay]
            interval = random.uniform(self.min_delay, self.max_delay)

            now = time.monotonic()
            if now < self._next_time:
                # Sleep just enough that start‐to‐start spacing is exactly `interval`
                time.sleep(self._next_time - now)

            # Schedule the next start time = last scheduled start + interval
            # (but if we're already past that point, bump to “now”)
            self._next_time = max(self._next_time + interval, time.monotonic())


def safe_json_dump(obj: dict) -> str:
    """
    Safely convert a dictionary to a JSON string.
    Ensures proper escaping for DB insertion.
    """
    try:
        return json.dumps(obj, ensure_ascii=False)
    except (TypeError, ValueError) as e:
        logger.error(f"JSON serialization failed: {e}")
        raise
