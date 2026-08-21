package com.wini.owle.entity.environs.pkg

import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
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
     * Version half of the package key. Read-only: it arrives with the composite id at
     * construction, and the database enforces it only as part of the (distribution, version)
     * foreign key into `packages`.
     */
    val packageVersion: VersionEntity
        get() = VersionEntity[id.value[PackagePythonRequirements.packageVersion].value]

    /**
     * The Python version being constrained against.
     */
    var pythonVersion by PythonVersionEntity referencedOn PackagePythonRequirements.pythonVersion

    /**
     * Read from the composite id for the same reason [packageVersion] is: widening the key to the
     * whole clause made these identity rather than payload. Changing one does not edit a row, it
     * names a different one - `>=3.9` and `<3.13` are two rows of the same package.
     */
    val operator: VersionOperator
        get() = id.value[PackagePythonRequirements.operator].value

    val wildcard: WildcardSegment
        get() = id.value[PackagePythonRequirements.wildcard].value
}
