package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
class PackagePythonRequirementEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<PackagePythonRequirementEntity>(PackagePythonRequirements)

    var distribution by DistributionEntity referencedOn PackagePythonRequirements.distribution

    var version by VersionEntity referencedOn PackagePythonRequirements.version

    var operator by PackagePythonRequirements.operator

    var wildcard by PackagePythonRequirements.wildcard

}