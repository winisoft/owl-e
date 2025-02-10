package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.WorkflowRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class WorkflowExecutionRepositoryTest : UnconstrainedDeleteRepoTest<Long, WorkflowExecutionDao>() {
    private val realRepo by KoinJavaComponent.inject<WorkflowExecutionRepository>(WorkflowExecutionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<WorkflowExecutionDao>): List<WorkflowExecutionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    status = ExecutionStatus.CANCELLED
                }
                get(1).apply {
                    status = ExecutionStatus.RUNNING
                }
            }
        }
    }

    override fun modifyEntity(entity: WorkflowExecutionDao): WorkflowExecutionDao {
        return transaction {
            entity.apply {
                status = ExecutionStatus.CANCELLED
            }
        }
    }

    override val implicatedTables = listOf(WorkflowExecutions, Workflows, Users)

    override val testModule: Module
        get() = module {
            single<WorkflowExecutionRepository> { WorkflowExecutionRepository() }
        }

    override fun getInstance(): WorkflowExecutionDao {
        return workflowExecutionDao()
    }

    override fun getList(): List<WorkflowExecutionDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        return listOf(workflowExecutionDao(user = user1), workflowExecutionDao(user = user2))
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, WorkflowExecutions)
    }



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
}