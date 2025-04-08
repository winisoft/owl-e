package com.wini.owle.services.steps

import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.mapper.steps.RevisionsMapper
import com.wini.owle.repo.steps.RevisionRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class RevisionService(
    repository: RevisionRepository,
    mapper: RevisionsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, RevisionDto, RevisionDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)