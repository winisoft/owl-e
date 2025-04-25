package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class VersionSpecsMapper(
    private val daoFactory: DaoFactory<VersionSpecEntity, Long>
) : DbMapper<VersionSpec, VersionSpecEntity>() {

    override fun toEntity(dto: VersionSpec): VersionSpecEntity {
        val applyProps: VersionSpecEntity.() -> Unit = {
            versionMin = dto.versionMin
            versionMax = dto.versionMax
            versionExact = dto.versionExact
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            VersionSpecEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("VersionSpec with id=${dto.id} not found")
    }

    override fun toDto(entity: VersionSpecEntity): VersionSpec {
        return VersionSpec(
            id = entity.id.value,
            versionMin = entity.versionMin,
            versionMax = entity.versionMax,
            versionExact = entity.versionExact
        )
    }
}