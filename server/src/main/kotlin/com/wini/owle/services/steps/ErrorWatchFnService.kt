package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.mapper.steps.ErrorWatchFnsMapper
import com.wini.owle.repo.ErrorWatchFnIdFactory
import com.wini.owle.repo.steps.ErrorWatchFnRepository
import com.wini.owle.services.BaseServiceImpl


class ErrorWatchFnService(
    repository: ErrorWatchFnRepository,
    mapper: ErrorWatchFnsMapper,
    entityIdFactory: ErrorWatchFnIdFactory
): BaseServiceImpl<Long, ErrorWatchFn, ErrorWatchFnEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)