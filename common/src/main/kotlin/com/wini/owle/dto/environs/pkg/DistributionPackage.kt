package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
@ModelDefinition
@Serializable
data class DistributionPackage(
    val distribution: Distribution,
    val version: Version,
    val versionString: String,
    val summary: String?,
    val keywords: String?,
    val description: String?,
    val descriptionContentType: String?,
    val license: String?,
    val homePage: String?,
    val projectUrl: String?,
    val downloadUrl: String?,
    val docsUrl: String?,
    val yanked: Boolean,
    val lastSerial: UInt?,
    //val requirements: List<PackageDistRequirement>,
) : CompositeDto {

    override val owleId: String get() = "distribution=${distribution},4"

}
