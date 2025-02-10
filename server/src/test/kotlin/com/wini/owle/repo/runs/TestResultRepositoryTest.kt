package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.testResultDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.TestResultDao
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

open class TestResultRepositoryTest: UnconstrainedDeleteRepoTest<Long, TestResultDao>() {
    private val realRepo by KoinJavaComponent.inject<TestResultRepository>(TestResultRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TestResultDao>): List<TestResultDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    this.executedAt = Instant.parse("2024-12-01T10:00:00Z").toLocalDateTime(TimeZone.UTC)
                }
                get(1).apply {
                    this.executedAt = Instant.parse("2024-12-02T10:00:00Z").toLocalDateTime(TimeZone.UTC)
                }
            }
        }
    }

    override fun modifyEntity(entity: TestResultDao): TestResultDao {
        return transaction {
            entity.apply {
                passed = false
            }
        }
    }

    override val implicatedTables = listOf(TestResults, TestCases, Workflows, Users, WorkflowExecutions)

    override val testModule: Module
        get() = module {
            single<TestResultRepository> { TestResultRepository() }
        }

    override fun getInstance(): TestResultDao {
        val user = userDao()
        return testResultDao(testCase = testCaseDao(user = user), workflowExecution = workflowExecutionDao(user = user))
    }

    override fun getList(): List<TestResultDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        val testResult1 = testResultDao(testCase = testCaseDao(user = user1), workflowExecution = workflowExecutionDao(user = user1))
        val testResult2 = testResultDao(testCase = testCaseDao(user = user2), workflowExecution = workflowExecutionDao(user = user2))
        return listOf(testResult1, testResult2)
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TestResults)
    }


}