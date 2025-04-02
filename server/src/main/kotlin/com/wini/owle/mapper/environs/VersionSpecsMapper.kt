package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class VersionSpecsMapper(
    private val daoFactory: DaoFactory<VersionSpecDao, Long>
) : DbMapper<VersionSpecDto, VersionSpecDao>() {

    override fun toEntity(dto: VersionSpecDto): VersionSpecDao {
        val applyProps: VersionSpecDao.() -> Unit = {
            versionMin = dto.versionMin
            versionMax = dto.versionMax
            versionExact = dto.versionExact
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            VersionSpecDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("VersionSpec with id=${dto.id} not found")
    }

    override fun toDto(entity: VersionSpecDao): VersionSpecDto {
        return VersionSpecDto(
            id = entity.id.value,
            versionMin = entity.versionMin,
            versionMax = entity.versionMax,
            versionExact = entity.versionExact
        )
    }
}