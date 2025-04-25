package com.wini.owle.entity.environs

import com.wini.owle.entity.ToolPackageDependencies
import com.wini.owle.entity.workflows.ToolEntity
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


/**
 * Links a tool to the packages it depends on, with specific version constraints.
 * Each dependency is associated with a version specification to ensure compatibility.
 */
class ToolPyPackDepEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<ToolPyPackDepEntity>(ToolPackageDependencies) {

        fun buildId(toolId: Long, pkgId: Long, versionId: Long): CompositeID {
            return CompositeID {
                it[ToolPackageDependencies.tool] = toolId
                it[ToolPackageDependencies.packageEntity] = pkgId
                it[ToolPackageDependencies.versionSpec] = versionId
            }
        }

        /**
         * Creates a new TagRelationDao by building the CompositeID from the
         * two TagDao objects. This matches typical Exposed usage for composite keys.
         */
        fun create(toolId: Long, pkgId: Long, versionId: Long): ToolPyPackDepEntity {
            val compositeId = buildId(toolId, pkgId, versionId)
            return ToolPyPackDepEntity(EntityID(compositeId, ToolPackageDependencies))
        }
    }

    /**
     * The tool associated with this package dependency.
     */
    var tool by ToolEntity referencedOn ToolPackageDependencies.tool

    /**
     * The package this dependency points to.
     */
    var packageEntity by PyPackageEntity referencedOn ToolPackageDependencies.packageEntity

    /**
     * The version specification associated with this package dependency.
     */
    var versionSpec by VersionSpecEntity referencedOn ToolPackageDependencies.versionSpec
}