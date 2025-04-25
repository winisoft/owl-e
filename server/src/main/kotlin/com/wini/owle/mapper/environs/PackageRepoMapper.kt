package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.entity.environs.PyPackageRepoEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.enums.PackageRepoTypeMapper


class PackageRepoMapper(
    private val daoFactory: DaoFactory<PyPackageRepoEntity, Long>,
    private val packageRepoTypeMapper: PackageRepoTypeMapper
): DbMapper<PyPackageRepo, PyPackageRepoEntity>() {

    override fun toEntity(dto: PyPackageRepo): PyPackageRepoEntity {
        val applyProps: PyPackageRepoEntity.() -> Unit = {
            sourceType = packageRepoTypeMapper.toEntity(dto.sourceType)
            sourceUrl = dto.sourceUrl
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else PyPackageRepoEntity.findById(dto.id)
            ?.apply(applyProps)
            ?: throw IllegalStateException("PackageRepo with id=${dto.id} not found")
    }

    override fun toDto(entity: PyPackageRepoEntity): PyPackageRepo {
        return PyPackageRepo(
            id = entity.id.value,
            sourceType = packageRepoTypeMapper.toDto(entity.sourceType),
            sourceUrl = entity.sourceUrl
        )
    }
}