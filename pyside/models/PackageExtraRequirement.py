from dataclasses import dataclass


@dataclass
class PackageExtraRequirement:
    """
    Associates a package's extra with a required_dist_specifier
    """
    distribution: str
    version_id: int
    extra_name: str
    required_specifier_id: int