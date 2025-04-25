package com.wini.owle.entity.composteid

import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.entity.ToolPackageDependencies
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column


object ToolPackageDepIdFactory : CompositeIdFactoryImpl<ToolPyPackDep>() {

    override fun fromDtoInstance(dto: ToolPyPackDep): CompositeID {
        return CompositeID {
            it[ToolPackageDependencies.tool] = dto.packageId
            it[ToolPackageDependencies.packageEntity] = dto.packageId
            it[ToolPackageDependencies.versionSpec] = dto.versionSpecId
        }
    }

    override val fkReferences: Map<Column<EntityID<Long>>, String> = buildMap {
        ToolPackageDependencies.tool to "toolId"
        ToolPackageDependencies.packageEntity to "packageId"
        ToolPackageDependencies.versionSpec to "versionSpecId"
    }
}