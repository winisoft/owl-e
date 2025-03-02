package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepBenchmarkingDataDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.runs.StepBenchmarkingDataDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import java.math.BigDecimal

open class StepBenchmarkingDatasRepositoryTest: UnconstrainedDeleteRepoTest<Long, StepBenchmarkingDataDao>() {
    private val realRepo by KoinJavaComponent.inject<StepBenchmarkingDataRepository>(StepBenchmarkingDataRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<StepBenchmarkingDataDao>): List<StepBenchmarkingDataDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    this.executionTime = BigDecimal.valueOf(10000.0)
                }
                get(1).apply {
                    this.executionTime = BigDecimal.valueOf(20000.0)
                }
            }
        }
    }

    override fun modifyEntity(entity: StepBenchmarkingDataDao): StepBenchmarkingDataDao {
        return transaction {
            entity.apply {
                this.executionTime = BigDecimal.valueOf(10000.0)
            }
        }
    }

    override val implicatedTables = listOf(
        StepBenchmarkingDatas,
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
            single<StepBenchmarkingDataRepository> { StepBenchmarkingDataRepository() }
        }

    override fun getInstance(): StepBenchmarkingDataDao {
        val user = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        return stepBenchmarkingDataDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user),
                step = stepDao(tool = toolDao(returnTypeDeclaration = typeDeclarationDao()), workflow = workflowDao())
            )
        )
    }

    override fun getList(): List<StepBenchmarkingDataDao> {
        val user1 = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
        }
        val user2 = UserDao.new {
            email = "jon@jon.com"
            password = "superSecretStuff2!"
        }
        val stepBenchmarkingData1 = stepBenchmarkingDataDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user1),
                step = stepDao(tool = toolDao(returnTypeDeclaration = typeDeclarationDao()), workflow = workflowDao())
            )
        )

        val stepBenchmarkingData2 = stepBenchmarkingDataDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user2),
                step = stepDao(
                    tool = toolDao(returnTypeDeclaration = typeDeclarationDao()),
                    workflow = workflowDao()
                )
            )
        )

        return listOf(stepBenchmarkingData1, stepBenchmarkingData2)
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, StepBenchmarkingDatas)
    }


}