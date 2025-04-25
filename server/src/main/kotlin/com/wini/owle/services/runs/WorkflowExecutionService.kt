package com.wini.owle.services.runs

import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.mapper.runs.WorkflowExecutionsMapper
import com.wini.owle.repo.WorkflowExecutionIdFactory
import com.wini.owle.repo.runs.WorkflowExecutionRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowExecutionService(
    repository: WorkflowExecutionRepository,
    mapper: WorkflowExecutionsMapper,
    entityIdFactory: WorkflowExecutionIdFactory
): BaseServiceImpl<Long, WorkflowExecution, WorkflowRunEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)