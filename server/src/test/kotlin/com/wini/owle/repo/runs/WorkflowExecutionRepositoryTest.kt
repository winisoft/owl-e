package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.workflows.WorkflowRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class WorkflowExecutionRepositoryTest : DbLayerIntegrationTests<Long, WorkflowExecutionDao>() {

    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to WorkflowExecutions`() {
        transaction {
            val workflow = EntityTestInstanceFactory.workflowDao()
            val workflowExecution = EntityTestInstanceFactory.workflowExecutionDao(workflow = workflow)
            val workflowExecutionId = workflowExecution.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedWorkflowExecution = getRepo().getById(EntityID(workflowExecutionId, WorkflowExecutions))
            assertNull(
                fetchedWorkflowExecution,
                "WorkflowExecution should have been deleted on cascade from parent Workflow delete"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, WorkflowExecutionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): WorkflowExecutionDao {
        TODO("Not yet implemented")
    }
}