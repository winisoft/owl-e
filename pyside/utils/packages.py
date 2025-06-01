from db_ops.packages import get_info
from db_ops.versions import get_all_version_fields, get_id_from_versions
from helpers.specifiers import sanitize_wildcard_version
from models.pypi_data import PackageInfo


import json
from typing import Optional, Tuple


def build_package_info_model(cursor, distribution: str, version_str: str) -> Optional[Tuple[int, PackageInfo]]:

    result = get_info(cursor, distribution, version_str)
    if result is None:
        return None
    version_id, info = result
    if info is None:
        return None

    if info is None:
        return None
    try:
        info_data = json.loads(info)
        package_info = PackageInfo.model_validate(info_data["info"])
        if package_info is None:
            print(f"Failed to validate package info for {distribution} {version_str}")
            return None
        return version_id, package_info
    except json.JSONDecodeError:
        print(f"Error decoding JSON for {distribution} {version_str}")
        return None


def clean_description_text(desc:str) -> str:
    return desc.replace("'", "''")


def get_target_package_id(cursor, spec_version_str: str) -> Optional[int]:

    safe_version_str = sanitize_wildcard_version(spec_version_str)

    (
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    ) = get_all_version_fields(safe_version_str)

    target_package_id = get_id_from_versions(
        cursor,
        epoch, major, minor, micro,
        phase, phase_number,
        post_number, dev_number, local,
        has_overflow
    )

    return target_package_id