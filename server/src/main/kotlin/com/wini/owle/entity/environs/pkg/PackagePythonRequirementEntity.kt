package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.PackagePythonRequirements
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * A package's `Requires-Python` constraint, resolved against a known Python version.
 */
class PackagePythonRequirementEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<PackagePythonRequirementEntity>(PackagePythonRequirements)

    var distribution by DistributionEntity referencedOn PackagePythonRequirements.distribution

    /**
     * The version half of the constrained package's key.
     */
    var packageVersion by VersionEntity referencedOn PackagePythonRequirements.packageVersion

    /**
     * The Python version being constrained against.
     */
    var pythonVersion by PythonVersionEntity referencedOn PackagePythonRequirements.pythonVersion

    var operator by PackagePythonRequirements.operator

    var wildcard by PackagePythonRequirements.wildcard
}
