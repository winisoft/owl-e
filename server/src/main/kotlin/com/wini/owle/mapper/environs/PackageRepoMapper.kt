package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.enums.PackageRepoTypeMapper


class PackageRepoMapper(
    private val daoFactory: DaoFactory<PackageRepoDao, Long>,
    private val packageRepoTypeMapper: PackageRepoTypeMapper
): DbMapper<PackageRepoDto, PackageRepoDao>() {

    override fun toEntity(dto: PackageRepoDto): PackageRepoDao {
        val applyProps: PackageRepoDao.() -> Unit = {
            sourceType = packageRepoTypeMapper.toEntity(dto.sourceType)
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
            sourceType = packageRepoTypeMapper.toDto(entity.sourceType),
            sourceUrl = entity.sourceUrl
        )
    }
}