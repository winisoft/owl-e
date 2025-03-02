package com.wini.owle.repo.runs

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.benchmarkingDataDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.BenchmarkingDataDao
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
import java.math.BigDecimal
import kotlin.test.assertNotNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class BenchmarkingDataRepositoryTest: UnconstrainedDeleteRepoTest<Long, BenchmarkingDataDao>() {
    private val realRepo by KoinJavaComponent.inject<BenchmarkingDataRepository>(BenchmarkingDataRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<BenchmarkingDataDao>): List<BenchmarkingDataDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    totalExecutionTime = BigDecimal.valueOf(123.45)
                }
                get(1).apply {
                    totalExecutionTime = BigDecimal.valueOf(234.56)
                }
            }
        }
    }

    override fun modifyEntity(entity: BenchmarkingDataDao): BenchmarkingDataDao {
        return transaction {
            entity.apply {
                totalExecutionTime = BigDecimal.valueOf(123.45)
            }
        }
    }

    override val implicatedTables = TestHelper.ImplicatedTables.RunsModule.BenchmarkingDatas

    override val testModule: Module
        get() = module {
            single<BenchmarkingDataRepository> { BenchmarkingDataRepository() }
        }

    override fun getInstance(): BenchmarkingDataDao {
        return transaction {
            val workflow = workflowDao()
            val user = userDao()
            val workflowExecution = workflowExecutionDao(workflow = workflow, user = user)

            benchmarkingDataDao(workflowExecution = workflowExecution)
        }
    }

    override fun getList(): List<BenchmarkingDataDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        return listOf(
            benchmarkingDataDao(
                workflowExecution = workflowExecutionDao(
                    workflow = workflowDao(),
                    user = user1
                )
            ),
            benchmarkingDataDao(
                workflowExecution = workflowExecutionDao(
                    workflow = workflowDao(),
                    user = user2
                )
            )
        )
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, BenchmarkingData)
    }

}