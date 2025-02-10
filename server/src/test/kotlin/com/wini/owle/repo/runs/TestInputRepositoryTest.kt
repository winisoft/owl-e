package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.testInputDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.runs.TestInputDao
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

open class TestInputRepositoryTest: UnconstrainedDeleteRepoTest<Long, TestInputDao>() {
    private val realRepo by KoinJavaComponent.inject<TestInputRepository>(TestInputRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TestInputDao>): List<TestInputDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    value = "Updated Value 1"
                }
                get(1).apply {
                    value = "Updated Value 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TestInputDao): TestInputDao {
        return transaction {
            entity.apply {
                value = "Updated Value"
            }
        }
    }

    override val implicatedTables = listOf(TestInputs, TestCases, Workflows, Users)

    override val testModule: Module
        get() = module {
            single<TestInputRepository> { TestInputRepository() }
        }

    override fun getInstance(): TestInputDao {
        return testInputDao()
    }

    override fun getList(): List<TestInputDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        val testCase1 = testCaseDao(user = user1)
        val testCase2 = testCaseDao(user = user2)
        return listOf(testInputDao(testCase = testCase1), testInputDao(testCase = testCase2))
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TestInputs)
    }


}