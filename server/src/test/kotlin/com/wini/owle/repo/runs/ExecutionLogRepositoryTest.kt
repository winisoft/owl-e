package com.wini.owle.repo.runs

import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory.executionLogDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowExecutionDao
import com.wini.owle.entity.ExecutionLogs
import com.wini.owle.entity.LogLevel
import com.wini.owle.entity.SubscriptionStatus
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.entity.user.UserDao
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class ExecutionLogRepositoryTest: DbLayerIntegrationTests<Long, ExecutionLogDao>() {

    private val realRepo by KoinJavaComponent.inject<ExecutionLogRepository>(ExecutionLogRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables: List<IdTable<*>> = ImplicatedTables.RunsModule.ExecutionLog

    override val testModule: Module
        get() = module {
            single<ExecutionLogRepository> { ExecutionLogRepository() }
        }

    override fun getInstance(): ExecutionLogDao {
        val user = UserDao.new {
            email = "jim@jim.com"
            password = "superSecretStuff1!"
            subscriptionStatus = SubscriptionStatus.REGISTERED
        }
        val executionLog = executionLogDao(
            stepExecution = stepExecutionDao(
                workflowExecution = workflowExecutionDao(workflow = workflowDao(), user = user),
                step = stepDao(tool = toolDao()),
            )
        )
        return executionLog
    }

}