package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyMapper(
    private val daoFactory: DaoFactory<ToolPackageDependencyDao, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<ToolPackageDependencyDto> = ToolPackageDepIdFactory
): DbMapper<ToolPackageDependencyDto, ToolPackageDependencyDao>() {

    override fun toEntity(dto: ToolPackageDependencyDto): ToolPackageDependencyDao {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            ToolPackageDependencyDao.findById(id)
        } ?: ToolPackageDependencyDao.create(toolId = dto.toolId, pkgId = dto.packageId, versionId = dto.versionSpecId)
    }

    override fun toDto(entity: ToolPackageDependencyDao): ToolPackageDependencyDto {
        return ToolPackageDependencyDto(
            toolId = entity.tool.id.value,
            packageId = entity.packageEntity.id.value,
            versionSpecId = entity.versionSpec.id.value
        )
    }
}