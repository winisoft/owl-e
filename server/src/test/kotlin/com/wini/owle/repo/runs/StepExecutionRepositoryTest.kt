package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowFolderDao
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.StepRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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

open class StepExecutionRepositoryTest : UnconstrainedDeleteRepoTest<Long, StepExecutionDao>() {
    private val realRepo by KoinJavaComponent.inject<StepExecutionRepository>(StepExecutionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<StepExecutionDao>): List<StepExecutionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    status = ExecutionStatus.COMPLETED
                }
                get(1).apply {
                    status = ExecutionStatus.FAILED
                }
            }
        }
    }

    override fun modifyEntity(entity: StepExecutionDao): StepExecutionDao {
        return transaction {
            entity.apply {
                status = ExecutionStatus.COMPLETED
            }
        }
    }

    override val implicatedTables = listOf(
        StepExecutions,
        WorkflowExecutions,
        Workflows,
        Users,
        Steps,
        Tools,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs
    )

    override val testModule: Module
        get() = module {
            single<StepExecutionRepository> { StepExecutionRepository() }
        }

    override fun getInstance(): StepExecutionDao {
        val user = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        return stepExecutionDao(
            workflowExecution = workflowExecutionDao(user = user),
            step = stepDao(workflow = workflowDao(), tool = toolDao()),
            status = ExecutionStatus.RUNNING
        )
    }

    override fun getList(): List<StepExecutionDao> {
        val user1 = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        val user2 = UserDao.new {
            email = "jon@jon.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        val stepExecution1 = stepExecutionDao(
            workflowExecution = workflowExecutionDao(user = user1),
            step = stepDao(workflow = workflowDao(), tool = toolDao()),
            status = ExecutionStatus.RUNNING
        )

        val stepExecution2 = stepExecutionDao(
            workflowExecution = workflowExecutionDao(user = user2),
            step = stepDao(workflow = workflowDao(), tool = toolDao()),
            status = ExecutionStatus.CANCELLED
        )
        return listOf(stepExecution1, stepExecution2)
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, StepExecutions)
    }



    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to StepExecutions`() {
        transaction {
            val step = stepDao()
            val stepExecution = stepExecutionDao(step = step)
            val stepExecutionId = stepExecution.id.value

            StepRepository().delete(step.id)

            val fetchedStepExecution = getRepo().getById(EntityID(stepExecutionId, StepExecutions))
            assertNull(
                fetchedStepExecution,
                "StepExecution should have been deleted on cascade from parent Step delete"
            )
        }
    }
}