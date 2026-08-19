package com.wini.owle.entity.workflows


import com.wini.owle.TestHelper
import com.wini.owle.entity.InstanceDbFakeEntityFactory.workflowFolderEntity
import com.wini.owle.repo.GenericRepoImpl
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


@Tag("integration")
abstract class WorkflowFolderDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.resetAll()
    }

    /**
     * GIVEN a folder with a child folder
     * WHEN the parent is deleted
     * THEN the database refuses, and the child is still there
     *
     * This asserted a cascade until now, and passed only because it built both folders with the
     * *mock* factory - nothing was ever inserted, so no constraint could fire and the assertion
     * ran against objects the database had never seen. Against real rows it fails: the foreign key
     * on `parent_folder_id` is NO ACTION, in MySQL and in the Exposed declaration alike, while
     * sibling references (`workflow_id`, `parent_grouper_id`, `metadata_id`) name CASCADE
     * explicitly.
     *
     * What *should* happen to a folder's children is an open question and deliberately not settled
     * here: it depends on a user-level "keep child folders" setting, which makes it the DLL layer's
     * decision rather than the schema's, and possibly a soft delete rather than a delete. Until
     * that is decided this test pins the behaviour that actually exists, so a change to it is a
     * deliberate one rather than a surprise.
     */
    @Test
    @DisplayName("Deleting a parent folder is refused while it has children")
    fun `test self-referential hierarchical delete is refused`() {
        val childId = transaction {
            val parent = workflowFolderEntity(id = 1UL)
            workflowFolderEntity(
                id = 2UL,
                user = { parent.user },
                parentFolder = { parent }
            ).id.value
        }

        assertThrows<ExposedSQLException> {
            transaction { GenericRepoImpl(WorkflowFolderEntity).delete(EntityID(1UL, WorkflowFolders)) }
        }

        transaction {
            assertNotNull(
                GenericRepoImpl(WorkflowFolderEntity).getById(EntityID(childId, WorkflowFolders)),
                "the child should survive a refused delete of its parent"
            )
        }
    }
}
