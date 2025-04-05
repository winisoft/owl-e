package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ErrorWatchFnDto
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.steps.ErrorWatchFnsMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.ErrorWatchFnIdFactory
import com.wini.owle.repo.ErrorWatchIdFactory
import com.wini.owle.repo.steps.ErrorWatchFnRepository
import com.wini.owle.services.BaseServiceImpl


class ErrorWatchFnService(
    repository: ErrorWatchFnRepository,
    mapper: ErrorWatchFnsMapper,
    entityIdFactory: ErrorWatchFnIdFactory
): BaseServiceImpl<Long, ErrorWatchFnDto, ErrorWatchFnDao>(
    repository,
    mapper,
    entityIdFactory
)