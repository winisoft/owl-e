import requests
from typing import Optional, Dict, Any

import requests

from models.pypi_data import PackageInfo

def fetch_pypi_metadata(distribution: str, version: str) -> Optional[Dict[str, Any]]:
    """
    Queries PyPI's JSON API for a given distribution and version.
    
    Returns:
        Metadata dict or None on failure.
    """
    url = f"https://pypi.org/pypi/{distribution}/{version}/json"
    try:
        response = requests.get(url, timeout=10)
        if response.status_code == 200:
            data = response.json()
            info = get_info_element(data)
            return info
    except Exception as e:
        print(f"Failed to fetch PyPI metadata for {distribution}=={version}: {e}")
    return None


def get_info_element(response: Dict[str, Any]) -> PackageInfo:
    """
    Extracts the 'info' element from the PyPI response and returns it as a PackageInfo object.
    """
    if not response or "info" not in response:
        return PackageInfo()
    
    info = response["info"]

    return PackageInfo(
        classifiers=info.get("classifiers", []),
        summary=info.get("summary", ""),
        description=info.get("description", ""),
        keywords=info.get("keywords", ""),
        requires_python=info.get("requires_python", ""),
        requires_dist=info.get("requires_dist", [])
    )