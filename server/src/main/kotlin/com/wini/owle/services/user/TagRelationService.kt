package com.wini.owle.services.user

import com.wini.owle.repo.TagRelationIdFactory
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.entity.user.tags.TagRelationEntity
import com.wini.owle.mapper.user.TagRelationsMapper
import com.wini.owle.repo.user.TagRelationRepository
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class TagRelationService(
    repository: TagRelationRepository,
    mapper: TagRelationsMapper,
    entityIdFactory: TagRelationIdFactory
): BaseServiceImpl<CompositeID, TagRelation, TagRelationEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)