package com.wini.owle.data.pypi

import kotlinx.serialization.Serializable

/**
 *  Full metadata payload returned by /pypi/{distribution}/{version}/json.
 *
 * Contains general information about the given release, active distribution artifacts, and a historical
 * record of all uploaded files for the version.
 */
@Serializable
data class VersionedMetadata(
    val info: VersionInfo,
    val urls: List<DistributionArtifact>,
    val releases: Map<String, List<ReleaseFile>>,
    val lastSerial: Int
) {

}