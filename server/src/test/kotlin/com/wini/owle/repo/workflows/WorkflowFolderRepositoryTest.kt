package com.wini.owle.repo.workflows

import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory.workflowFolderDao
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class WorkflowFolderRepositoryTest: DbLayerIntegrationTests<Long, WorkflowFolderDao>() {

    @Test
    @DisplayName("Delete Cascade From Parent Folder")
    fun `test self-referential cascaded hierarchical delete`() {
        transaction {
            val parentFolder = workflowFolderDao()
            val childFolder = workflowFolderDao(parentFolder = parentFolder)
            val childFolderId = childFolder.id.value

            val repo = getRepo()
            repo.delete(parentFolder.id)

            val fetchedWorkflowFolder = repo.getById(EntityID(childFolderId, WorkflowFolders))
            assertNull(
                fetchedWorkflowFolder,
                "WorkflowFolder child should have been deleted on cascade from parent WorkflowFolder delete"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, WorkflowFolderDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): WorkflowFolderDao {
        TODO("Not yet implemented")
    }
}