package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.entity.environs.PackageRepoType
import com.wini.owle.entity.environs.PackageRepoType.Companion.toDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class PackageRepoMapper(
    private val daoFactory: DaoFactory<PackageRepoDao, Long>
): DbMapper<PackageRepoDto, PackageRepoDao>() {

    override fun toEntity(dto: PackageRepoDto): PackageRepoDao {
        val applyProps: PackageRepoDao.() -> Unit = {
            sourceType = PackageRepoType.fromDto(dto.sourceType)
            sourceUrl = dto.sourceUrl
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else PackageRepoDao.findById(dto.id)
            ?.apply(applyProps)
            ?: throw IllegalStateException("PackageRepo with id=${dto.id} not found")
    }

    override fun toDto(entity: PackageRepoDao): PackageRepoDto {
        return PackageRepoDto(
            id = entity.id.value,
            sourceType = entity.sourceType.toDto(),
            sourceUrl = entity.sourceUrl
        )
    }
}