package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.entity.environs.PyPackageEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class PackagesMapper(
    private val daoFactory: DaoFactory<PyPackageEntity, Long>,
    private val versionSpecMapper: VersionSpecsMapper
) : DbMapper<PyPackage, PyPackageEntity>() {

    override fun toEntity(dto: PyPackage): PyPackageEntity {
        val applyProps: PyPackageEntity.() -> Unit = {
            name = dto.name
            disambiguation = dto.disambiguation
            versionSpec = versionSpecMapper.toEntity(dto.versionSpec)
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            PyPackageEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Package with id=${dto.id} not found")
    }

    override fun toDto(entity: PyPackageEntity): PyPackage {
        return PyPackage(
            id = entity.id.value,
            name = entity.name,
            disambiguation = entity.disambiguation,
            versionSpec = versionSpecMapper.toDto(entity.versionSpec)
        )
    }
}