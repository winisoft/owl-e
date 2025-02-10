package com.wini.owle.entity.environs

import com.wini.owle.entity.Packages
import com.wini.owle.entity.ToolPackageDependencies
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
class PackageDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PackageDao>(Packages)

    /**
     * The name of the Python library package (e.g., 'numpy', 'pandas').
     * Required.
     */
    var name by Packages.name

    /**
     * Optional field to distinguish between packages with similar names.
     * Useful if the package is a fork or variant.
     */
    var disambiguation by Packages.disambiguation

    /**
     * Defines the exact version details (min, max, or exact version) available for this package.
     */
    var versionSpec by VersionSpecDao referencedOn Packages.versionSpec

    /**
     * The tool and version this package is relevant to
     */
    val toolDependencies by ToolPackageDependencyDao referrersOn ToolPackageDependencies.packageEntity
}