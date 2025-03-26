package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.testInputDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.runs.TestInputDao
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


open class TestInputRepositoryTest: DbLayerIntegrationTests<Long, TestInputDao>() {

    private val realRepo by KoinJavaComponent.inject<TestInputRepository>(TestInputRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(TestInputs, TestCases, Workflows, Users)

    override val testModule: Module
        get() = module {
            single<TestInputRepository> { TestInputRepository() }
        }

    override fun getInstance(): TestInputDao {
        return testInputDao()
    }

}