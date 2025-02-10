package com.wini.owle.entity.environs

import com.wini.owle.entity.ToolPackageDependencies
import com.wini.owle.entity.VersionSpecs
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
class VersionSpecDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<VersionSpecDao>(VersionSpecs)

    /**
     * Minimum version of the package, inclusive.
     */
    var versionMin by VersionSpecs.versionMin

    /**
     * Maximum version of the package, inclusive.
     */
    var versionMax by VersionSpecs.versionMax

    /**
     * Exact version of the package, if specified. Overrides min and max if present.
     */
    var versionExact by VersionSpecs.versionExact

    /**
     * The tool package combinations for which this version applies
     */
    val toolDependencies by ToolPackageDependencyDao referrersOn ToolPackageDependencies.versionSpec

}