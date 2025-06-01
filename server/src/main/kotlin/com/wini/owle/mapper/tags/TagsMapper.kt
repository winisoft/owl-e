package com.wini.owle.mapper.tags

import com.wini.owle.dto.tags.Tag
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper


class TagsMapper(
    private val daoDelegate: DaoDelegate<ULong, Tag, TagEntity>,
    private val usersMapper: UsersMapper
): DbMapper<ULong, Tag, TagEntity> {

    override fun toEntity(dto: Tag): TagEntity {
        return daoDelegate.findOrCreate(dto) {
            name = dto.name
            description = dto.description
        }
    }

    override fun toDto(entity: TagEntity): Tag {
        return Tag(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            source = entity.source
        )
    }
}