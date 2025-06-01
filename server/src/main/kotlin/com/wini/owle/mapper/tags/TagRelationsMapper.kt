package com.wini.owle.mapper.tags

import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TagRelationsMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TagRelation, TagRelationEntity>,
): DbMapper<CompositeID, TagRelation, TagRelationEntity> {

    override fun toEntity(dto: TagRelation): TagRelationEntity {
        val applyProps: TagRelationEntity.() -> Unit = {

        }

        return daoDelegate.findOrCreate(dto, applyProps)
    }

    override fun toDto(entity: TagRelationEntity): TagRelation {
        return TagRelation(
            parentTagId = entity.parentTagEntity.id.value,
            childTagId = entity.childTagEntity.id.value
        )
    }
}