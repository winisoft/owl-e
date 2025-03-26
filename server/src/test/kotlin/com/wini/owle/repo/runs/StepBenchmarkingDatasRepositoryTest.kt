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
import org.jetbrains.exposed.dao.id.EntityID
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class StepBenchmarkingDatasRepositoryTest: DbLayerIntegrationTests<Long, StepBenchmarkingDataDao>() {

    private val realRepo by KoinJavaComponent.inject<StepBenchmarkingDataRepository>(StepBenchmarkingDataRepository::class.java)

    override fun getRepo() = realRepo

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

}