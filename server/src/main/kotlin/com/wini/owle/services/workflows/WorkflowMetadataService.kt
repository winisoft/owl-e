package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.WorkflowMetadataDto
import com.wini.owle.entity.workflows.WorkflowMetadataDao
import com.wini.owle.mapper.workflows.WorkflowMetadataMapper
import com.wini.owle.repo.WorkflowMetadataIdFactory
import com.wini.owle.repo.workflows.WorkflowMetadataRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowMetadataService(
    repository: WorkflowMetadataRepository,
    mapper: WorkflowMetadataMapper,
    entityIdFactory: WorkflowMetadataIdFactory
): BaseServiceImpl<Long, WorkflowMetadataDto, WorkflowMetadataDao>(
    repository,
    mapper,
    entityIdFactory
)