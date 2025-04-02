package com.wini.owle.mapper.user

import com.wini.owle.dto.user.TagDto
import com.wini.owle.entity.user.TagDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TagsMapper(
    private val daoFactory: DaoFactory<TagDao, Long>,
    private val usersMapper: UsersMapper
): DbMapper<TagDto, TagDao>() {

    override fun toEntity(dto: TagDto): TagDao {
        val applyProps: TagDao.() -> Unit = {
            name = dto.name
            description = dto.description
            user = usersMapper.toEntity(dto.user) // Updated call
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TagDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Tag with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TagDao): TagDto {
        return TagDto(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            user = usersMapper.toDto(entity.user) // Updated call
        )
    }
}