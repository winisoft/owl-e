package com.wini.owle.mapper.environs

import com.wini.owle.dto.ToolPackageDependencyDto
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.mapper.DbMapper


class ToolPackageDependencyMapper: DbMapper<ToolPackageDependencyDto, ToolPackageDependencyDao>() {

    override val applyProps: ToolPackageDependencyDao.(ToolPackageDependencyDto) -> Unit = {}

    override fun ToolPackageDependencyDto.toEntity(): ToolPackageDependencyDao {
        return ToolPackageDependencyDao.create(toolId, packageId, versionSpecId)
    }

    override fun ToolPackageDependencyDao.toDto(): ToolPackageDependencyDto {
        return ToolPackageDependencyDto(
            toolId = tool.id.value,
            packageId = packageEntity.id.value,
            versionSpecId = versionSpec.id.value
        )
    }
}