package com.wini.owle.services.runs

import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.entity.runs.step.StepRunLogLineEntity
import com.wini.owle.mapper.runs.ExecutionLogMapper
import com.wini.owle.repo.ExecutionLogsIdFactory
import com.wini.owle.repo.runs.ExecutionLogRepository
import com.wini.owle.services.BaseServiceImpl


class ExecutionLogService(
    repository: ExecutionLogRepository,
    mapper: ExecutionLogMapper,
    entityIdFactory: ExecutionLogsIdFactory
): BaseServiceImpl<Long, StepRunLogLine, StepRunLogLineEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)