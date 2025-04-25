package com.wini.owle.services.steps

import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.mapper.steps.WorkflowJunctionsMapper
import com.wini.owle.repo.WorkflowJunctionIdFactory
import com.wini.owle.repo.steps.WorkflowJunctionRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowJunctionService(
    repository: WorkflowJunctionRepository,
    mapper: WorkflowJunctionsMapper,
    entityIdFactory: WorkflowJunctionIdFactory
): BaseServiceImpl<Long, WorkflowJunction, WorkflowJunctionEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)