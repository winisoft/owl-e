package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class PackagesMapper(
    private val daoFactory: DaoFactory<PackageDao, Long>,
    private val versionSpecMapper: VersionSpecsMapper
) : DbMapper<PackageDto, PackageDao>() {

    override fun toEntity(dto: PackageDto): PackageDao {
        val applyProps: PackageDao.() -> Unit = {
            name = dto.name
            disambiguation = dto.disambiguation
            versionSpec = versionSpecMapper.toEntity(dto.versionSpec)
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            PackageDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Package with id=${dto.id} not found")
    }

    override fun toDto(entity: PackageDao): PackageDto {
        return PackageDto(
            id = entity.id.value,
            name = entity.name,
            disambiguation = entity.disambiguation,
            versionSpec = versionSpecMapper.toDto(entity.versionSpec)
        )
    }
}