package com.wini.owle.services.runs

import com.wini.owle.dto.runs.step.StepRun
import com.wini.owle.entity.runs.step.StepRunEntity
import com.wini.owle.mapper.runs.StepExecutionsMapper
import com.wini.owle.repo.StepExecutionIdFactory
import com.wini.owle.repo.runs.StepExecutionRepository
import com.wini.owle.services.BaseServiceImpl


class StepExecutionService(
    repository: StepExecutionRepository,
    mapper: StepExecutionsMapper,
    entityIdFactory: StepExecutionIdFactory
): BaseServiceImpl<Long, StepRun, StepRunEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)