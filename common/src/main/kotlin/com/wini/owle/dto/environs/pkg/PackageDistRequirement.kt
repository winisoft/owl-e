package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@ModelDefinition
@ApiEndpointDefinition("api.environs.pkg.packageRequiredDist", ["environs"], "/package-required-dist")
@Serializable
class PackageDistRequirement(

    override val id: ULong,

    /**
     * The unique identifier of this PyPackage
     */
    val dependentDistribution: Distribution,

//    /**
//     * The unique identifier of the [DistributionPackage] that holds the dependency represented by this PackageRequirement
//     */
//    val dependentPackage: DistributionPackage,

    val dependentVersion: Version,

    val requiredDistribution: Distribution,


    val requiredVersion: Version?,


    val operator: VersionOperator,


    val wildcardSegment: WildcardSegment

): ULongDto {

    override val owleId: String get() = "dependentVersion.id=${dependentVersion.owleId},requiredDistribution=${requiredDistribution.name},requiredPackage.id=${requiredVersion?.owleId}"

}

