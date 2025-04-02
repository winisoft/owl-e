package com.wini.owle.services.user

import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.mapper.user.TagRelationsMapper
import com.wini.owle.repo.user.TagRelationRepository
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class TagRelationService(
    repository: TagRelationRepository,
    mapper: TagRelationsMapper
): BaseServiceImpl<CompositeID, TagRelationDto, TagRelationDao>(repository, mapper)