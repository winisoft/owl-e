package com.wini.owle.services.steps

import com.wini.owle.dto.ErrorWatchFnDto
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.steps.ErrorWatchFnsMapper
import com.wini.owle.repo.steps.ErrorWatchFnRepository
import com.wini.owle.services.BaseServiceImpl


class ErrorWatchFnService(
    repository: ErrorWatchFnRepository,
    mapper: ErrorWatchFnsMapper
): BaseServiceImpl<Long, ErrorWatchFnDto, ErrorWatchFnDao>(repository, mapper)