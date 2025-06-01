import logging
import subprocess
import sys
from typing import List, Optional, Tuple
import os
import json

from db_ops.python_versions import get_supported_python_versions
from utils.db_connection import get_db_connection
logger = logging.getLogger(__name__)

REQUIRED_PACKAGES = {
    'typing_inspect': None,  # None means use latest version
    'mysql-connector-python': None,
    'pydantic': "2.6.1",  # Specify version to ensure consistency
    'pydantic-core': None,  # Will be installed as dependency
    'typing-extensions': None  # Required by pydantic
}

def check_conda_environment() -> bool:
    """Check if we're running in a conda environment."""
    return 'CONDA_PREFIX' in os.environ

def run_command(cmd: List[str], check: bool = True) -> Tuple[int, str, str]:
    """Run a command and return (return_code, stdout, stderr)."""
    try:
        process = subprocess.Popen(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True
        )
        stdout, stderr = process.communicate()
        return process.returncode, stdout, stderr
    except Exception as e:
        logger.error(f"Failed to run command {' '.join(cmd)}: {e}")
        if check:
            raise
        return 1, "", str(e)

def get_installed_packages() -> dict:
    """Get currently installed packages and their versions."""
    try:
        result = subprocess.run(
            [sys.executable, "-m", "pip", "list", "--format", "json"],
            capture_output=True,
            text=True,
            check=True
        )
        packages = {pkg["name"]: pkg["version"] for pkg in json.loads(result.stdout)}
        return packages
    except Exception as e:
        logger.error(f"Failed to get installed packages: {e}")
        return {}

def ensure_python_version(major: int, minor: int, micro: int) -> bool:
    """
    Ensure the specified Python version is installed via conda.
    Returns True if successful, False otherwise.
    """
    version = f"{major}.{minor}.{micro}"
    
    # Check if this version is already active
    current = (sys.version_info.major, sys.version_info.minor, sys.version_info.micro)
    if current == (major, minor, micro):
        logger.info(f"Python {version} is already active")
        return True
    
    # Try to install the Python version
    logger.info(f"Installing Python {version}")
    cmd = ["conda", "install", "-y", f"python={version}"]
    retcode, stdout, stderr = run_command(cmd, check=False)
    
    if retcode != 0:
        logger.error(f"Failed to install Python {version}: {stderr}")
        return False
    
    return True

def ensure_package(package: str, version: Optional[str] = None) -> bool:
    """
    Ensure a package is installed with pip.
    Returns True if successful, False otherwise.
    """
    installed = get_installed_packages()
    package_spec = f"{package}=={version}" if version else package
    
    if package in installed:
        if version is None or installed[package] == version:
            logger.info(f"Package {package_spec} is already installed")
            return True
    
    logger.info(f"Installing {package_spec}")
    
    # First uninstall to ensure clean installation
    uninstall_cmd = [sys.executable, "-m", "pip", "uninstall", "-y", package]
    run_command(uninstall_cmd, check=False)
    
    # Then install with --no-cache-dir to force fresh install
    cmd = [sys.executable, "-m", "pip", "install", "--no-cache-dir", "-U", package_spec]
    retcode, stdout, stderr = run_command(cmd, check=False)
    
    if retcode != 0:
        logger.error(f"Failed to install {package_spec}: {stderr}")
        return False
    
    return True

def process_types_for_version(version_id: int, major: int, minor: int, micro: int) -> bool:
    """Process types for a specific Python version."""
    # First ensure we have the right Python version
    if not ensure_python_version(major, minor, micro):
        return False
    
    # Then ensure all required packages are installed in the correct order
    # Install base packages first
    base_packages = ['typing-extensions', 'pydantic-core']
    for package in base_packages:
        if package in REQUIRED_PACKAGES and not ensure_package(package, REQUIRED_PACKAGES[package]):
            return False
    
    # Then install other packages
    for package, version in REQUIRED_PACKAGES.items():
        if package not in base_packages and not ensure_package(package, version):
            return False
    
    # Now run the types processor with the version ID
    logger.info(f"Processing types for Python {major}.{minor}.{micro} (ID: {version_id})")
    
    cmd = [sys.executable, "-m", "processors.types_procesor", str(version_id)]
    retcode, stdout, stderr = run_command(cmd, check=False)
    
    # Clean up connection pool after child process completes
    from utils.db_connection import cleanup_pool
    cleanup_pool()
    
    if retcode != 0:
        logger.error(f"Failed to process types: {stderr}")
        return False
    
    logger.info(f"Successfully processed types for Python {major}.{minor}.{micro}")
    return True

def process_all_supported_versions():
    """Process types for all supported Python versions."""
    if not check_conda_environment():
        logger.error("Must be run from a conda environment")
        return
    
    supported_versions = None
    with get_db_connection() as conn:
        cursor = conn.cursor()
        try:
            supported_versions = get_supported_python_versions(cursor)
            if not supported_versions:
                logger.error("No supported Python versions found in database")
                return
            
            logger.info(f"Found {len(supported_versions)} supported Python versions")
        finally:
            cursor.close()
    
    # Process versions after closing the initial connection
    if supported_versions:
        for version_id, major, minor, micro in supported_versions:
            logger.info(f"Processing Python {major}.{minor}.{micro} (ID: {version_id})")
            success = process_types_for_version(version_id, major, minor, micro)
            if not success:
                logger.warning(f"Failed to process Python {major}.{minor}.{micro}")

if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    process_all_supported_versions() 