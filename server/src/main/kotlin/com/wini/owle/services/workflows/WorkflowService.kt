package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.workflows.WorkflowsMapper
import com.wini.owle.repo.workflows.WorkflowRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowService(
    repository: WorkflowRepository,
    mapper: WorkflowsMapper
): BaseServiceImpl<Long, WorkflowDto, WorkflowDao>(repository, mapper)