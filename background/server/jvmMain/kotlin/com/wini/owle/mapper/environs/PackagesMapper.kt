package com.wini.owle.mapper.environs

import com.wini.owle.dto.PackageDto
import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.entity.Packages
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class PackagesMapper(
    private val versionSpecMapper: VersionSpecsMapper
) : DbMapper<PackageDto, PackageDao>() {

    override val applyProps: PackageDao.(PackageDto) -> Unit = { dto ->
        this.name = dto.name
        this.disambiguation = dto.disambiguation
        this.versionSpec = versionSpecMapper.run {
            dto.versionSpec.toEntity()
        }
    }

    override fun PackageDto.toEntity(): PackageDao {
        return if (id == 0L) PackageDao.new {
            applyProps(this@toEntity)
        } else
            PackageDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("VersionSpec with id=$id not found")
    }

    override fun PackageDao.toDto(): PackageDto {
        return PackageDto(
            id = id.value,
            name = name,
            disambiguation = disambiguation,
            versionSpec = versionSpecMapper.run { versionSpec.toDto() }
        )
    }
}