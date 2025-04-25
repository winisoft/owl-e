package com.wini.owle.services.user

import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.entity.user.tags.TagEntity
import com.wini.owle.mapper.user.TagsMapper
import com.wini.owle.repo.TagIdFactory
import com.wini.owle.repo.user.TagRepository
import com.wini.owle.services.BaseServiceImpl


class TagService(
    repository: TagRepository,
    mapper: TagsMapper,
    entityIdFactory: TagIdFactory
): BaseServiceImpl<Long, Tag, TagEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)