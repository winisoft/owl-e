package com.wini.owle.mapper.user

import com.wini.owle.dto.TagDto
import com.wini.owle.entity.Tags
import com.wini.owle.entity.Users
import com.wini.owle.entity.user.TagDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TagsMapper(
    private val usersMapper: UsersMapper
) : DbMapper<TagDto, TagDao>() {

    override val applyProps: TagDao.(TagDto) -> Unit = { dto ->
        name = dto.name
        description = dto.description
        user = usersMapper.run { dto.user.toEntity() }
    }

    override fun TagDto.toEntity(): TagDao {
        return if (id == 0L) {
            TagDao.new { applyProps(this@toEntity) }
        } else {
            TagDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Tag with id=$id not found")
        }
    }

    override fun TagDao.toDto(): TagDto {
        return TagDto(
            id = id.value,
            name = name,
            description = description,
            user = usersMapper.run { user.toDto() }
        )
    }
}