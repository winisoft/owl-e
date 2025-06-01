
import logging
from packaging.specifiers import SpecifierSet
from typing import List, Literal, Optional
from models.RequiresPythonSpecifier import RequiresPythonSpecifier
from db_ops.versions import get_all_version_fields, get_id_from_python_versions
from utils.operators import normalize_operator
from utils.version import determine_wildcard_segment, sanitize_wildcard_version

logger = logging.getLogger(__name__)



def process_requires_python(
    cursor,
    distribution: str,
    package_id: int,
    requires_python: Optional[str]
) -> Optional[List[RequiresPythonSpecifier]]:


    try:
        spec_set = SpecifierSet(requires_python) if requires_python is not None else None
        if not spec_set:
            return None
        
        packge_py_requires: list[RequiresPythonSpecifier] = []

        for spec in spec_set:

            operator = normalize_operator(spec.operator)

            version_str = sanitize_wildcard_version(spec.version)
            
            (
                epoch, major, minor, micro,
                phase, phase_number,
                post_number, dev_number, local,
                has_overflow
            ) = get_all_version_fields(version_str)
            
            py_version_id= get_id_from_python_versions(
                cursor, 
                epoch, major, minor, micro,
                phase, phase_number,
                post_number, dev_number, local,
                has_overflow
            )

            wildcard = determine_wildcard_segment(version_str)

            
            if py_version_id is None:
                logger.error(f"Failed to find target python version ID for {version_str} parsing required python for {distribution}=={package_id} -- bail on it.")
                return None
            
            packge_py_requires.append(RequiresPythonSpecifier(distribution, package_id, py_version_id, operator, wildcard))

        return packge_py_requires

    except Exception as e:
        logger.error("Failed to parse requires_python: %s", e)
        return None

