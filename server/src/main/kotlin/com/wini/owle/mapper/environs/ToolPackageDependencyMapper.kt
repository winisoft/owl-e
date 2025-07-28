package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.ToolPyPackDependency
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import com.wini.owle.entity.environs.ToolPyPackDepEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyMapper(
    private val daoFactory: DaoFactory<ToolPyPackDepEntity, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<ToolPyPackDependency> = ToolPackageDepIdFactory
): DbMapper<ToolPyPackDependency, ToolPyPackDepEntity>() {

    override fun toEntity(dto: ToolPyPackDependency): ToolPyPackDepEntity {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            ToolPyPackDepEntity.findById(id)
        } ?: ToolPyPackDepEntity.create(toolId = dto.toolId, pkgId = dto.packageId, versionId = dto.versionSpecId)
    }

    override fun toDto(entity: ToolPyPackDepEntity): ToolPyPackDependency {
        return ToolPyPackDependency(
            toolId = entity.tool.id.value,
            packageId = entity.packageEntity.id.value,
            versionSpecId = entity.versionSpec.id.value
        )
    }
}