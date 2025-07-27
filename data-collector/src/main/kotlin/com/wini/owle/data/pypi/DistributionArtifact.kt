package com.wini.owle.data.pypi

import kotlinx.serialization.Serializable

/**
 * Represents a downloadable distribution file for a specific release, returned in the 'urls' array of the
 * PyPI versioned API response.
 *
 * Common types include:
 *     - sdist: Source distribution
 *     - bdist_wheel: Built wheel distribution
 *
 * This list tends to reflect *current* installable files for a version.
 *
 * @param filename
 * @param packagetype One of: 'sdist', 'bdist_wheel', etc.
 * @param url Direct download URL
 * @param size File size in bytes
 * @param python_version Legacy field; often just 'source' or 'py3'
 * @param requires_python PEP 440 specifier, e.g. '>=3.6'
 * @param yanked Whether this artifact has been marked as yanked
 * @param yanked_reason Optional string explaining the yank
 * @param digests  e.g. {"sha256": "...", "md5": "..."}
 * @param upload_time_iso_8601 ISO-formatted UTC datetime string
 * @param has_sig True if a GPG signature is available
 * @param comment_text Rarely used; arbitrary publisher comments
 */
@Serializable
data class DistributionArtifact(
    val filename: String,
    val packagetype: String,
    val url: String,
    val size: Int,
    val python_version: String,
    val requires_python: String?,
    val yanked: Boolean,
    val yanked_reason: String? = null,
    val digests: Map<String, String>,
    val upload_time_iso_8601: Boolean,
)