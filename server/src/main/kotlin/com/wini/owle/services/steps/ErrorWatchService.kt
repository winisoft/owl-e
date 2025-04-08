package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.mapper.steps.ErrorWatchesMapper
import com.wini.owle.repo.ErrorWatchIdFactory
import com.wini.owle.repo.steps.ErrorWatchRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class ErrorWatchService(
    repository: ErrorWatchRepository,
    mapper: ErrorWatchesMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, ErrorWatchDto, ErrorWatchDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)