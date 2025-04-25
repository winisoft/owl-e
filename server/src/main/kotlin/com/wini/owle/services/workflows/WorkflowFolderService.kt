package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.mapper.workflows.WorkflowFoldersMapper
import com.wini.owle.repo.WorkflowFolderIdFactory
import com.wini.owle.repo.workflows.WorkflowFolderRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowFolderService(
    repository: WorkflowFolderRepository,
    mapper: WorkflowFoldersMapper,
    entityIdFactory: WorkflowFolderIdFactory
): BaseServiceImpl<Long, WorkflowFolder, WorkflowFolderEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)