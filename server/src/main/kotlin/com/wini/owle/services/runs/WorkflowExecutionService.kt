package com.wini.owle.services.runs

import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.runs.WorkflowExecutionsMapper
import com.wini.owle.repo.WorkflowExecutionIdFactory
import com.wini.owle.repo.runs.WorkflowExecutionRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class WorkflowExecutionService(
    repository: WorkflowExecutionRepository,
    mapper: WorkflowExecutionsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, WorkflowExecutionDto, WorkflowExecutionDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)