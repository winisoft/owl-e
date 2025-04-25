package com.wini.owle.services.steps

import com.wini.owle.dto.steps.DataPointSave
import com.wini.owle.entity.steps.DataPointSaveEntity
import com.wini.owle.mapper.steps.RevisionsMapper
import com.wini.owle.repo.steps.RevisionRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.RevisionIdFactory


class RevisionService(
    repository: RevisionRepository,
    mapper: RevisionsMapper,
    entityIdFactory: RevisionIdFactory
): BaseServiceImpl<Long, DataPointSave, DataPointSaveEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)