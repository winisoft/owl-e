package com.wini.owle.mapper.user

import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TagRelationIdFactory
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TagRelationsMapper(
    private val daoFactory: DaoFactory<TagRelationDao, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<TagRelationDto> = TagRelationIdFactory
) : DbMapper<TagRelationDto, TagRelationDao>() {

    override fun toEntity(dto: TagRelationDto): TagRelationDao {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            TagRelationDao.findById(id)
        } ?: TagRelationDao.create(dto.parentTagId, dto.childTagId)
    }

    override fun toDto(entity: TagRelationDao): TagRelationDto {
        return TagRelationDto(
            parentTagId = entity.parentTag.id.value,
            childTagId = entity.childTag.id.value
        )
    }
}