package com.wini.owle.services.runs

import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.mapper.runs.ExecutionLogMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.ExecutionLogsIdFactory
import com.wini.owle.repo.runs.ExecutionLogRepository
import com.wini.owle.services.BaseServiceImpl


class ExecutionLogService(
    repository: ExecutionLogRepository,
    mapper: ExecutionLogMapper,
    entityIdFactory: ExecutionLogsIdFactory
): BaseServiceImpl<Long, ExecutionLogDto, ExecutionLogDao>(
    repository,
    mapper,
    entityIdFactory
)