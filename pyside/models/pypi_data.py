from pydantic import BaseModel, Field
from typing import List, Optional


class Digests(BaseModel):
    blake2B256: str = Field(..., alias="blake2b_256")
    md5: str
    sha256: str

class ReleaseFile(BaseModel):
    commentText: Optional[str] = Field(None, alias="comment_text")
    digests: Digests
    downloads: int
    fileName: str = Field(..., alias="filename")
    hasSig: bool = Field(False, alias="has_sig")
    md5Digest: Optional[str] = Field(None, alias="md5_digest")
    packageType: Optional[str] = Field(None, alias="packagetype")
    pythonVersion: Optional[str] = Field(None, alias="python_version")
    requiresPython: Optional[str] = Field(None, alias="requires_python")
    size: Optional[int] = Field(None, alias="size")
    uploadTime: Optional[str] = Field(None, alias="upload_time")
    uploadTimeIso8601: str = Field(..., alias="upload_time_iso_8601")
    url: str
    yanked: bool = Field(False)
    yankedReason: Optional[str] = Field(None, alias="yanked_reason")

class Vulnerability(BaseModel):
    id: Optional[int] = None
    packageInfoId: int = Field(..., alias="package_info_id")
    vulnerabilityId: str = Field(..., alias="vulnerability_id")
    alias: Optional[str] = None
    details: Optional[str] = None
    link: Optional[str] = None
    severity: Optional[str] = None

class ProjectUrls(BaseModel):
    id: Optional[int] = None
    changeLog: Optional[str] = Field(default=None, alias="Changelog")
    code: Optional[str] = Field(default=None, alias="Code")
    download: Optional[str] = Field(default=None, alias="Download")
    documentation: Optional[str] = Field(default=None, alias="Documentation")
    homePage: Optional[str] = Field(default=None, alias="Homepage")
    issueTracker: Optional[str] = Field(default=None, alias="Issue tracker")
    source: Optional[str] = Field(default=None, alias="Source")

class PackageDownloads(BaseModel):
    id: Optional[int] = None
    lastDay: int = Field(default=0, alias="last_day")
    lastWeek: int = Field(default=0, alias="last_week")
    lastMonth: int = Field(default=0, alias="last_month")
    recordedAt: Optional[str] = Field(default=None, alias="recorded_at")

class PackageInfo(BaseModel):
    id: Optional[int] = None
    author: Optional[str] = None
    authorEmail: Optional[str] = Field(default=None, alias="author_email")
    bugtrackUrl: Optional[str] = Field(default=None, alias="bugtrack_url")
    classifiers: List[str] = Field(default_factory=list)
    description: Optional[str] = None
    descriptionContentType: Optional[str] = Field(default=None, alias="description_content_type")
    docsUrl: Optional[str] = Field(default=None, alias="docs_url")
    downloads: Optional[PackageDownloads] = None
    downloadUrl: Optional[str] = Field(default=None, alias="download_url")
    dynamic: Optional[List[str]] = None
    homePage: Optional[str] = Field(default=None, alias="home_page")
    keywords: Optional[str] = None
    license: Optional[str] = None
    licenseExpression: Optional[str] = Field(default=None, alias="license_expression")
    licenseFiles: Optional[List[str]] = Field(default=None, alias="license_files")
    maintainer: Optional[str] = None
    maintainerEmail: Optional[str] = Field(default=None, alias="maintainer_email")
    name: str
    packageUrl: Optional[str] = Field(default=None, alias="package_url")
    platform: Optional[str] = None
    projectUrl: Optional[str] = Field(default=None, alias="project_url")
    # PyPI sends `"project_urls": null` for packages that declare none; required here, that
    # rejected the whole payload and discarded every requirement the package declared.
    projectUrls: Optional[ProjectUrls] = Field(default=None, alias="project_urls")
    providesExtra: Optional[List[str]] = Field(default=None, alias="provides_extra")
    releaseUrl: Optional[str] = Field(default=None, alias="release_url")
    requiresDist: Optional[List[str]] = Field(default=None, alias="requires_dist")
    requiresPython: Optional[str] = Field(default=None, alias="requires_python")
    summary: Optional[str] = None
    version: Optional[str] = None
    yanked: bool = False
    yankedReason: Optional[str] = Field(default=None, alias="yanked_reason")

class PackageInfoResponse(BaseModel):
    info: PackageInfo
    lastSerial: int = Field(alias="last_serial")
    urls: List[ReleaseFile] = Field(default_factory=list)
    vulnerabilities: List[Vulnerability] = Field(default_factory=list)
