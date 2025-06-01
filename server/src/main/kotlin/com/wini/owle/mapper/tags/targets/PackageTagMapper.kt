package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.PackageTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.PackageTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class PackageTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, PackageTag, PackageTagEntity>
): DbMapper<CompositeID, PackageTag, PackageTagEntity> {

    override fun toEntity(dto: PackageTag): PackageTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: PackageTagEntity): PackageTag {
        TODO("Not yet implemented")
    }

}