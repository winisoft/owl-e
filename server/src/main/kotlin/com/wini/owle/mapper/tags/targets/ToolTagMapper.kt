package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.ToolTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.ToolTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class ToolTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, ToolTag, ToolTagEntity>
): DbMapper<CompositeID, ToolTag, ToolTagEntity> {

    override fun toEntity(dto: ToolTag): ToolTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: ToolTagEntity): ToolTag {
        TODO("Not yet implemented")
    }

}