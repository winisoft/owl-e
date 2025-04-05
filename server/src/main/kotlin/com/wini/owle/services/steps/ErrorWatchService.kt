package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.mapper.steps.ErrorWatchesMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.ErrorWatchFnIdFactory
import com.wini.owle.repo.ErrorWatchIdFactory
import com.wini.owle.repo.steps.ErrorWatchRepository
import com.wini.owle.services.BaseServiceImpl


class ErrorWatchService(
    repository: ErrorWatchRepository,
    mapper: ErrorWatchesMapper,
    entityIdFactory: ErrorWatchIdFactory
): BaseServiceImpl<Long, ErrorWatchDto, ErrorWatchDao>(
    repository,
    mapper,
    entityIdFactory
)