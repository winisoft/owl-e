package com.wini.owle.services.user

import com.wini.owle.dto.TagDto
import com.wini.owle.entity.user.TagDao
import com.wini.owle.mapper.user.TagsMapper
import com.wini.owle.repo.user.TagRepository
import com.wini.owle.services.BaseServiceImpl


class TagService(
    repository: TagRepository,
    mapper: TagsMapper
): BaseServiceImpl<Long, TagDto, TagDao>(repository, mapper)