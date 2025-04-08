package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.workflows.WorkflowsMapper
import com.wini.owle.repo.WorkflowIdFactory
import com.wini.owle.repo.workflows.WorkflowRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class WorkflowService(
    repository: WorkflowRepository,
    mapper: WorkflowsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, WorkflowDto, WorkflowDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)