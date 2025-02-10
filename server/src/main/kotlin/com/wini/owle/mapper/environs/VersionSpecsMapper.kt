package com.wini.owle.mapper.environs

import com.wini.owle.dto.VersionSpecDto
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.DbMapper


class VersionSpecsMapper : DbMapper<VersionSpecDto, VersionSpecDao>() {

    override val applyProps: VersionSpecDao.(VersionSpecDto) -> Unit = { dto ->
        this.versionMin = dto.versionMin
        this.versionMax = dto.versionMax
        this.versionExact = dto.versionExact
    }

    override fun VersionSpecDto.toEntity(): VersionSpecDao {
        return if (id == 0L) VersionSpecDao.new {
            applyProps(this@toEntity)
        } else
            VersionSpecDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("VersionSpec with id=$id not found")
    }

    override fun VersionSpecDao.toDto(): VersionSpecDto {
        return VersionSpecDto(
            id = id.value,
            versionMin = versionMin,
            versionMax = versionMax,
            versionExact = versionExact
        )
    }
}