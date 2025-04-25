package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.mapper.workflows.WorkflowsMapper
import com.wini.owle.repo.WorkflowIdFactory
import com.wini.owle.repo.workflows.WorkflowRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowService(
    repository: WorkflowRepository,
    mapper: WorkflowsMapper,
    entityIdFactory: WorkflowIdFactory
): BaseServiceImpl<Long, Workflow, WorkflowEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)