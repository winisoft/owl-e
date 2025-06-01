from dataclasses import dataclass


@dataclass
class RequiresPythonSpecifier:
    """
    Associates the distribution and version of a package with a python version it has been published as
    requiring. Note that a given package may have multiple RequiresPythonSpecifiers, reflecting multiple
    terms in its version specifier, e.g. upper and lower bounds with 'not version x', etc
    """
    distribution: str
    package_id: int
    py_version_id: int
    operator: str
    wildcard: str