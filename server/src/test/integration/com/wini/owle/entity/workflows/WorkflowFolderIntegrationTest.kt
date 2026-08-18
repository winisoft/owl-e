package com.wini.owle.entity.workflows


import com.wini.owle.TestHelper
import com.wini.owle.entity.InstanceMockEntityFactory.workflowFolderDao
import com.wini.owle.repo.GenericRepoImpl
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull
import org.junit.jupiter.api.Tag



@Tag("integration")
open class WorkflowFolderDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.resetAll()
    }

    @Test
    @DisplayName("Delete Cascade From Parent Folder")
    fun `test self-referential cascaded hierarchical delete`() {
        transaction {
            val parentFolder = workflowFolderDao()
            val childFolder = workflowFolderDao(parentFolder = parentFolder)
            val childFolderId = childFolder.id.value

            val repo = GenericRepoImpl(WorkflowFolderEntity)
            repo.delete(parentFolder.id)

            val fetchedWorkflowFolder = repo.getById(EntityID(childFolderId, WorkflowFolders))
            assertNull(
                fetchedWorkflowFolder,
                "WorkflowFolder child should have been deleted on cascade from parent WorkflowFolder delete"
            )
        }
    }


}