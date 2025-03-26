package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.testResultDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.TestResultDao
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


open class TestResultRepositoryTest: DbLayerIntegrationTests<Long, TestResultDao>() {

    private val realRepo by KoinJavaComponent.inject<TestResultRepository>(TestResultRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(TestResults, TestCases, Workflows, Users, WorkflowExecutions)

    override val testModule: Module
        get() = module {
            single<TestResultRepository> { TestResultRepository() }
        }

    override fun getInstance(): TestResultDao {
        val user = userDao()
        return testResultDao(testCase = testCaseDao(user = user), workflowExecution = workflowExecutionDao(user = user))
    }

}