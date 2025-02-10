package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.testExpectedOutcomeDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.repo.BaseOwleRepoTest
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

open class TestExpectedOutcomeRepositoryTest: UnconstrainedDeleteRepoTest<Long, TestExpectedOutcomeDao>() {
    private val realRepo by KoinJavaComponent.inject<TestExpectedOutcomeRepository>(TestExpectedOutcomeRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TestExpectedOutcomeDao>): List<TestExpectedOutcomeDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    expectedValue = "Updated Expected Value 1"
                }
                get(1).apply {
                    expectedValue = "Updated Expected Value 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TestExpectedOutcomeDao): TestExpectedOutcomeDao {
        return transaction {
            entity.apply {
                expectedValue = "Updated Expected Value"
            }
        }
    }

    override val implicatedTables = listOf(TestExpectedOutcomes, TestCases, Workflows, Users)

    override val testModule: Module
        get() = module {
            single<TestExpectedOutcomeRepository> { TestExpectedOutcomeRepository() }
        }

    override fun getInstance(): TestExpectedOutcomeDao {
        return testExpectedOutcomeDao()
    }

    override fun getList(): List<TestExpectedOutcomeDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        val testCase1 = testCaseDao(user = user1)
        val testCase2 = testCaseDao(user = user2)
        return listOf(testExpectedOutcomeDao(testCase = testCase1), testExpectedOutcomeDao(testCase = testCase2))
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TestExpectedOutcomes)
    }


}