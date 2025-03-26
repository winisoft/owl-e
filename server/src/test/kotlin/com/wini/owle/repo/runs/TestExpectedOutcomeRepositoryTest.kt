package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.testExpectedOutcomeDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
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


open class TestExpectedOutcomeRepositoryTest: DbLayerIntegrationTests<Long, TestExpectedOutcomeDao>() {

    private val realRepo by KoinJavaComponent.inject<TestExpectedOutcomeRepository>(TestExpectedOutcomeRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(TestExpectedOutcomes, TestCases, Workflows, Users)

    override val testModule: Module
        get() = module {
            single<TestExpectedOutcomeRepository> { TestExpectedOutcomeRepository() }
        }

    override fun getInstance(): TestExpectedOutcomeDao {
        return testExpectedOutcomeDao()
    }

}