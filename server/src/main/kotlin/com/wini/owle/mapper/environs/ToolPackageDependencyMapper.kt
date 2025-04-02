package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyMapper(
    private val daoFactory: DaoFactory<ToolPackageDependencyDao, CompositeID>
): DbMapper<ToolPackageDependencyDto, ToolPackageDependencyDao>() {

    // Note: This mapper handles a join table entity.
    // toEntity doesn't create/update in the standard way, but uses a specific DAO method.
    // Assuming ToolPackageDependencyDao.create handles finding/creating the link.
    override fun toEntity(dto: ToolPackageDependencyDto): ToolPackageDependencyDao {
        // This assumes ToolPackageDependencyDao.create finds or creates the relationship
        // based on the provided IDs. It might need adjustment based on actual DAO logic.
        return ToolPackageDependencyDao.create(dto.toolId, dto.packageId, dto.versionSpecId)
    }

    override fun toDto(entity: ToolPackageDependencyDao): ToolPackageDependencyDto {
        return ToolPackageDependencyDto(
            toolId = entity.tool.id.value,
            packageId = entity.packageEntity.id.value,
            versionSpecId = entity.versionSpec.id.value
        )
    }
}