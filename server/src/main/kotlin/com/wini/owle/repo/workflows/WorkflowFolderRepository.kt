package com.wini.owle.repo.workflows

import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class WorkflowFolderRepository: OwleRepoImpl<Long, WorkflowFolderDao>(WorkflowFolderDao) {

    fun getRootLevelFolders(): List<WorkflowFolderDao> {
        return getWhere {
            it.parentFolder?.id == null
        }
    }

    fun getSubfoldersOf(id: Long): List<WorkflowFolderDao> {
        return getWhere {
            it.parentFolder?.id == EntityID(id, WorkflowFolders)
        }
    }

}