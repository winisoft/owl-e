package com.wini.owle.repo.workflows

import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class WorkflowFolderRepository: OwleRepoImpl<Long, WorkflowFolderEntity>(WorkflowFolderEntity) {

    fun getRootLevelFolders(): List<WorkflowFolderEntity> {
        return getWhere {
            it.parentFolder?.id == null
        }
    }

    fun getSubfoldersOf(id: Long): List<WorkflowFolderEntity> {
        return getWhere {
            it.parentFolder?.id == EntityID(id, WorkflowFolders)
        }
    }

}