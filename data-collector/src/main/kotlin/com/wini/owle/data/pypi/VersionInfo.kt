package com.wini.owle.data.pypi

import kotlinx.serialization.Serializable

/**
 * @param name Project name
 * @param version Version string
 * @param summary Short summary of the distribution
 * @param description Long description (often README content)
 * @param requires_python Python version requirement string (e.g. ">=3.7")
 * @param dependencies Runtime dependencies
 * @param description_content_type e.g. "text/markdown"
 * @param author
 * @param author_email
 * @param maintainer
 * @param maintainer_email
 * @param license  SPDX string or license text
 * @param classifiers Trove classifiers
 * @param keywords Space-separated or comma-separated keywords
 * @param home_page Deprecated in favor of project_urls["Homepage"]
 * @param project_url Rarely used; use project_urls instead
 * @param project_urls Dict like {"Homepage": "...", "Docs": "..."}
 * @param provides_extras Optional dependency extras
 * @param platform Target platform (if specified)
 * @param download_url URL to source or binary archive
 * @param release_url API URL for this version
 * @param package_url API URL for the project
 * @param author_url Optional author URL (non-standard)
 * @param license_url Optional license URL (non-standard)
 */ 
@Serializable
data class VersionInfo(
    val name: String,
    val version: String,
    val summary: String? = null,
    val description: String? = null,
    val requires_python: String? = null,
    val dependencies: List<Dependency>, //field(default_factory=list
    val description_content_type: String? = null,
    val author: String? = null,
    val author_email: String? = null,
    val maintainer: String? = null,
    val maintainer_email: String? = null,
    val license: String? = null,
    val classifiers: List<String>, //= field(default_factory=list)  #
    val keywords: String? = null,
    val home_page: String? = null,
    val project_url: String? = null,
    val project_urls: Map<String, String>?,
    val platform: String? = null,
    val download_url: String? = null,
    val bugtrack_url: String? = null,
    val documentation_url: String? = null,
    val release_url: String = "",
    val package_url: String = "",
    val author_url: String? = null,
    val license_url: String? = null
)