package com.wini.owle.repo.runs

import com.wini.owle.entity.BenchmarkingData
import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory.benchmarkingDataDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.BenchmarkingDataDao
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class BenchmarkingDataRepositoryTest: DbLayerIntegrationTests<Long, BenchmarkingDataDao>() {

    private val realRepo by KoinJavaComponent.inject<BenchmarkingDataRepository>(BenchmarkingDataRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = ImplicatedTables.RunsModule.BenchmarkingDatas

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

}