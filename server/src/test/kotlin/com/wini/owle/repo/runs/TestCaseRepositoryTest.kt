package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.runs.TestCaseDao
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class TestCaseRepositoryTest : DbLayerIntegrationTests<Long, TestCaseDao>() {

    private val realRepo by KoinJavaComponent.inject<TestCaseRepository>(TestCaseRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(TestCases, Workflows, Users)

    override val testModule = module {
        single<TestCaseRepository> { TestCaseRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): TestCaseDao {
        return testCaseDao()
    }

}