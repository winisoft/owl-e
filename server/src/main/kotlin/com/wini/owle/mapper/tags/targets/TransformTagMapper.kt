package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.ToolTag
import com.wini.owle.dto.tags.targets.TransformTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.ToolTagEntity
import com.wini.owle.entity.tags.targets.TransformTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TransformTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TransformTag, TransformTagEntity>
): DbMapper<CompositeID, TransformTag, TransformTagEntity> {

    override fun toEntity(dto: TransformTag): TransformTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: TransformTagEntity): TransformTag {
        TODO("Not yet implemented")
    }

}