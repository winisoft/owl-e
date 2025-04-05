package com.wini.owle.services.runs

import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.runs.WorkflowExecutionsMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.WorkflowExecutionIdFactory
import com.wini.owle.repo.WorkflowIdFactory
import com.wini.owle.repo.runs.WorkflowExecutionRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowExecutionService(
    repository: WorkflowExecutionRepository,
    mapper: WorkflowExecutionsMapper,
    entityIdFactory: WorkflowExecutionIdFactory
): BaseServiceImpl<Long, WorkflowExecutionDto, WorkflowExecutionDao>(
    repository,
    mapper,
    entityIdFactory
)