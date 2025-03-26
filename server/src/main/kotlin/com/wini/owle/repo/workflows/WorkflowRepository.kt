package com.wini.owle.repo.workflows

import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction


class WorkflowRepository : OwleLongIdRepoImpl<WorkflowDao>() {

    override val entityClass: LongEntityClass<WorkflowDao>
        get() = WorkflowDao

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