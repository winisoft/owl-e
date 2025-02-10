package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.executionLogDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.BaseOwleRepoTest
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

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class ExecutionLogRepositoryTest: UnconstrainedDeleteRepoTest<Long, ExecutionLogDao>() {
    private val realRepo by KoinJavaComponent.inject<ExecutionLogRepository>(ExecutionLogRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<ExecutionLogDao>): List<ExecutionLogDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    message = "Updated log message 1"
                }
                get(1).apply {
                    message = "Updated log message 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: ExecutionLogDao): ExecutionLogDao {
        return transaction {
            entity.apply {
                message = "Updated log message"
            }
        }
    }

    override val implicatedTables = listOf(
        ExecutionLogs,
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
            single<ExecutionLogRepository> { ExecutionLogRepository() }
        }

    override fun getInstance(): ExecutionLogDao {
        val user = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        val executionLog = executionLogDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user),
                step = stepDao(tool = toolDao()),
            )
        )
        return executionLog
    }

    override fun getList(): List<ExecutionLogDao> {
        val user1 = UserDao.new {
            email = "jon@jon.com"
            password = "superSecretStuff2!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        val user2 = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        val executionLog1 = executionLogDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user1),
                step = stepDao(tool = toolDao())
            ),
            logLevel = LogLevel.INFO,
            message = "Message 1"
        )
        val executionLog2 = executionLogDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user2),
                step = stepDao(tool = toolDao())
            ),
            logLevel = LogLevel.WARNING,
            message = "Message 2"
        )
        return listOf(
            executionLog1, executionLog2
        )
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, ExecutionLogs)
    }


}