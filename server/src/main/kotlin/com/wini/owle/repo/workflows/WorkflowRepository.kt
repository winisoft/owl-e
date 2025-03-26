package com.wini.owle.repo.workflows

import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction


class WorkflowRepository: OwleRepoImpl<Long, WorkflowDao>(WorkflowDao) {

    fun getInRootWorkflowFolder(): List<WorkflowDao> {
        return getWhere {
            it.parentFolder?.id == null
        }
    }

    fun getInWorkflowFolder(workflowFolderId: Long): List<WorkflowDao> {
        return getWhere {
            it.parentFolder?.id == EntityID(workflowFolderId, WorkflowFolders)
        }
    }

}