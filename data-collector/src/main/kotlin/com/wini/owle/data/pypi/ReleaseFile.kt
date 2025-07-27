package com.wini.owle.data.pypi

import kotlinx.serialization.Serializable

/**
 * A downloadable file associated with a specific release version.
 *
 * These appear under the 'releases\[version]' key, and may include historical or superseded artifacts. This list is
 * comprehensive, unlike 'urls'.
 * @param filename
 * @param url Direct download URL
 * @param has_sig GPG signature present
 * @param digests Hashes for integrity verification
 * @param size File size in bytes
 * @param upload_time_iso_8601 ISO-formatted upload datetime
 * @param comment_text Arbitrary notes from the publisher
 * @param python_version Often legacy; use with caution
 * @param packagetype Artifact type; e.g. 'sdist', 'bdist_wheel'
 * @param requires_python PEP 440 specifier
 * @param yanked
 * @param yanked_reason
 */
@Serializable
data class ReleaseFile(
    val filename: String,
    val url: String,
    val has_sig: Boolean,
    val digests: Map<String, String>,
    val size: Int,
    val upload_time_iso_8601: String,
    val comment_text: String,
    val python_version: String,
    val packagetype: String,
    val requires_python: String?,
    val yanked: Boolean,
    val yanked_reason: String?
)