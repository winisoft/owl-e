package com.wini.owle.repo.workflows

import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowRepository: OwleRepoImpl<Long, WorkflowEntity>(WorkflowEntity) {

    fun getInRootWorkflowFolder(): List<WorkflowEntity> {
        return getWhere {
            it.parentFolder?.id == null
        }
    }

    fun getInWorkflowFolder(workflowFolderId: Long): List<WorkflowEntity> {
        return getWhere {
            it.parentFolder?.id == EntityID(workflowFolderId, WorkflowFolders)
        }
    }

}