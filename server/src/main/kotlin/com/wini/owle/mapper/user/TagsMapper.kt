package com.wini.owle.mapper.user

import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.entity.user.tags.TagEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TagsMapper(
    private val daoFactory: DaoFactory<TagEntity, Long>,
    private val usersMapper: UsersMapper
): DbMapper<Tag, TagEntity>() {

    override fun toEntity(dto: Tag): TagEntity {
        val applyProps: TagEntity.() -> Unit = {
            name = dto.name
            description = dto.description
            userEntity = usersMapper.toEntity(dto.user) // Updated call
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TagEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Tag with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TagEntity): Tag {
        return Tag(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            user = usersMapper.toDto(entity.userEntity) // Updated call
        )
    }
}