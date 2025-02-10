package com.wini.owle.mapper.user

import com.wini.owle.dto.TagRelationDto
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.mapper.DbMapper


class TagRelationsMapper : DbMapper<TagRelationDto, TagRelationDao>() {

    override val applyProps: TagRelationDao.(TagRelationDto) -> Unit = {}

    override fun TagRelationDto.toEntity(): TagRelationDao {
        return TagRelationDao.create(parentTagId = parentTagId, childTagId = childTagId)
    }

    override fun TagRelationDao.toDto(): TagRelationDto {
        return TagRelationDto(
            parentTagId = parentTag.id.value,
            childTagId = childTag.id.value
        )
    }
}