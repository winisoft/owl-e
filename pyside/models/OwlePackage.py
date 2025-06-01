from typing import Optional, List
from models.pypi_data import PackageInfo


class OwlePackage:
    """
        An object representing a package that is the subject of our data collection effort for the Owl-E app's venv management
    
        :param distribution: The uniquely identifying name for a project releasing packages to the community
        :param version_string: The string identifying this package to ecosystem
        :param version_id: The id of this package, either selected or inserted into the versions table
        :param package_info: The raw bundle from the PyPI API response consumed
        :param distribution_sql: The sql text that will insert (or ignore) the distribution into the distributions table
        :param package_insert_sql: The sql text that will insert this package itself into the packages table
        :param package_tag_insert_sql: The sql text that will insert the semantic tags tied to the classifiers into package_tags
        :param extras_insert_sql: The sql text that will insert this package's extras (if any exist) into package_extras
    """
    def __init__(
            self,
            distribution: str,
            version_string: str,
            version_id: int,
            package_info: PackageInfo,
            extras: Optional[List[str]] = None,
            associated_tag_ids: Optional[List[int]] = None
    ):
        self.distribution: str = distribution
        self.version_string: str = version_string
        self.version_id: int = version_id
        self.package_info: PackageInfo = package_info
        self.extras: Optional[List[str]] = extras
        self.associated_tag_ids: Optional[List[int]] = associated_tag_ids
        self.summary: Optional[str] = package_info.summary if package_info.summary else None
        self.keywords: Optional[str] = package_info.keywords if package_info.keywords else None
        self.description: Optional[str] = package_info.description if package_info.description else None
        self.description_content_type: Optional[str] = package_info.descriptionContentType if package_info.descriptionContentType else None
        self.license: Optional[str] = package_info.license if package_info.license else None
        self.home_page: Optional[str] = package_info.homePage if package_info.homePage else None
        self.project_url: Optional[str] = package_info.projectUrl if package_info.projectUrl else None
        self.download_url: Optional[str] = package_info.downloadUrl if package_info.downloadUrl else None
        self.docs_url: Optional[str] = package_info.docsUrl if package_info.docsUrl else None
        self.yanked: bool = package_info.yanked if package_info.yanked else False
        self.last_serial: Optional[int] = package_info.last_serial if package_info.last_serial else None
    