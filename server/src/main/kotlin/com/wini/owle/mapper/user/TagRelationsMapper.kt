package com.wini.owle.mapper.user

import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TagRelationIdFactory
import com.wini.owle.entity.user.tags.TagRelationEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TagRelationsMapper(
    private val daoFactory: DaoFactory<TagRelationEntity, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<TagRelation> = TagRelationIdFactory
) : DbMapper<TagRelation, TagRelationEntity>() {

    override fun toEntity(dto: TagRelation): TagRelationEntity {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            TagRelationEntity.findById(id)
        } ?: TagRelationEntity.create(dto.parentTagId, dto.childTagId)
    }

    override fun toDto(entity: TagRelationEntity): TagRelation {
        return TagRelation(
            parentTagId = entity.parentTagEntity.id.value,
            childTagId = entity.childTagEntity.id.value
        )
    }
}