package com.wini.owle.services.user

import com.wini.owle.dto.user.TagDto
import com.wini.owle.entity.user.TagDao
import com.wini.owle.mapper.user.TagsMapper
import com.wini.owle.repo.TagIdFactory
import com.wini.owle.repo.user.TagRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TagService(
    repository: TagRepository,
    mapper: TagsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, TagDto, TagDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)