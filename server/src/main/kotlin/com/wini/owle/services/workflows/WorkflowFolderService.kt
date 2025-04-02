package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.mapper.workflows.WorkflowFoldersMapper
import com.wini.owle.repo.workflows.WorkflowFolderRepository
import com.wini.owle.services.BaseServiceImpl


class WorkflowFolderService(
    repository: WorkflowFolderRepository,
    mapper: WorkflowFoldersMapper
): BaseServiceImpl<Long, WorkflowFolderDto, WorkflowFolderDao>(repository, mapper)