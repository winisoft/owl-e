package com.wini.owle.mapper.user

import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TagRelationsMapper(
    private val daoFactory: DaoFactory<TagRelationDao, CompositeID>
) : DbMapper<TagRelationDto, TagRelationDao>() {

    // No properties to apply for this simple relation entity beyond the ID
//    val applyProps: TagRelationDao.(TagRelationDto) -> TagRelationDao = {
//    }

    override fun toEntity(dto: TagRelationDto): TagRelationDao {
        // Assuming TagRelationDao.create handles finding or creating.
        // If it only creates, this might lead to duplicates or errors
        // if the relation already exists. Check TagRelationDao implementation.
        // Based on original code `TagRelationDao.create(...)`
        return TagRelationDao.create(parentTagId = dto.parentTagId, childTagId = dto.childTagId)
    }

    override fun toDto(entity: TagRelationDao): TagRelationDto {
        return TagRelationDto(
            parentTagId = entity.parentTag.id.value,
            childTagId = entity.childTag.id.value
        )
    }
}