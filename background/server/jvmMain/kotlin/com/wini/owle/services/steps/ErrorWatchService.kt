package com.wini.owle.services.steps

import com.wini.owle.dto.ErrorWatchDto
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.mapper.steps.ErrorWatchesMapper
import com.wini.owle.repo.steps.ErrorWatchRepository
import com.wini.owle.services.BaseServiceImpl


class ErrorWatchService(
    repository: ErrorWatchRepository,
    mapper: ErrorWatchesMapper
): BaseServiceImpl<Long, ErrorWatchDto, ErrorWatchDao>(repository, mapper)