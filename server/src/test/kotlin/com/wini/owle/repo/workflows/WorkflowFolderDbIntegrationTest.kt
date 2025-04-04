package com.wini.owle.repo.workflows

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory.workflowFolderDao
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.repo.OwleRepoImplTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class WorkflowFolderDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.WorkflowsModule.WorkflowFolder)
    }

    @Test
    @DisplayName("Delete Cascade From Parent Folder")
    fun `test self-referential cascaded hierarchical delete`() {
        transaction {
            val parentFolder = workflowFolderDao()
            val childFolder = workflowFolderDao(parentFolder = parentFolder)
            val childFolderId = childFolder.id.value

            val repo = WorkflowFolderRepository()
            repo.delete(parentFolder.id)

            val fetchedWorkflowFolder = repo.getById(EntityID(childFolderId, WorkflowFolders))
            assertNull(
                fetchedWorkflowFolder,
                "WorkflowFolder child should have been deleted on cascade from parent WorkflowFolder delete"
            )
        }
    }

}