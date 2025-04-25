package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.mapper.steps.ErrorWatchesMapper
import com.wini.owle.repo.ErrorWatchIdFactory
import com.wini.owle.repo.steps.ErrorWatchRepository
import com.wini.owle.services.BaseServiceImpl


class ErrorWatchService(
    repository: ErrorWatchRepository,
    mapper: ErrorWatchesMapper,
    entityIdFactory: ErrorWatchIdFactory
): BaseServiceImpl<Long, ErrorWatch, ErrorWatchEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)