package com.wini.owle.mapper.environs

import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.entity.PackageRepoType
import com.wini.owle.entity.PackageRepoType.Companion.toDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.DbMapper


class PackageRepoMapper: DbMapper<PackageRepoDto, PackageRepoDao>() {

    override val applyProps: PackageRepoDao.(PackageRepoDto) -> Unit = { dto ->
        this.sourceType = PackageRepoType.fromDto(dto.sourceType)
        this.sourceUrl = dto.sourceUrl
    }

    override fun PackageRepoDto.toEntity(): PackageRepoDao {
        return if (id == 0L) PackageRepoDao.new {
            applyProps(this@toEntity)
        } else
            PackageRepoDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("VersionSpec with id=$id not found")
    }

    override fun PackageRepoDao.toDto(): PackageRepoDto {
        return PackageRepoDto(
            id = id.value,
            sourceType = sourceType.toDto(),
            sourceUrl = sourceUrl
        )
    }
}