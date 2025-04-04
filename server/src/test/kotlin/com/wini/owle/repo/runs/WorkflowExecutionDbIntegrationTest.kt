package com.wini.owle.repo.runs

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.WorkflowRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class WorkflowExecutionDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.RunsModule.WorkflowExecution)
    }

    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to WorkflowExecutions`() {
        transaction {
            val workflow = EntityTestInstanceFactory.workflowDao()
            val workflowExecution = EntityTestInstanceFactory.workflowExecutionDao(workflow = workflow)
            val workflowExecutionId = workflowExecution.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedWorkflowExecution = WorkflowExecutionRepository().getById(EntityID(workflowExecutionId, WorkflowExecutions))
            assertNull(
                fetchedWorkflowExecution,
                "WorkflowExecution should have been deleted on cascade from parent Workflow delete"
            )
        }
    }

}