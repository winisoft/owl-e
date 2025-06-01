package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class PackageDistRequirementEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<PackageDistRequirementEntity>(PackageDistRequirements)

    var dependentDistribution by DistributionEntity referencedOn PackageDistRequirements.dependentDist
    var dependentVersion by VersionEntity referencedOn PackageDistRequirements.dependentVersion
    //var dependentPackage by DistributionPackageEntity referencedOn PackageDistRequirements.dependentPackage

    var requiredDistribution by DistributionEntity referencedOn PackageDistRequirements.requiredDist
    var requiredVersion by VersionEntity optionalReferencedOn PackageDistRequirements.requiredVersion

    var operator by PackageDistRequirements.operator
    var wildcardSegment by PackageDistRequirements.wildcardSegment

}
