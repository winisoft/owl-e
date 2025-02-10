package com.wini.owle.services.steps

import com.wini.owle.dto.WorkflowJunctionDto
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.mapper.steps.WorkflowJunctionsMapper
import com.wini.owle.repo.steps.WorkflowJunctionRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowJunctionService(
    repository: WorkflowJunctionRepository,
    mapper: WorkflowJunctionsMapper
): BaseServiceImpl<Long, WorkflowJunctionDto, WorkflowJunctionDao>(repository, mapper)