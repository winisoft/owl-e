package com.wini.owle.repo.workflows

import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull


open class WorkflowRepositoryTest : DbLayerIntegrationTests<Long, WorkflowDao>() {

    private val realRepo by KoinJavaComponent.inject<WorkflowRepository>(WorkflowRepository::class.java)
    override fun getRepo() = realRepo
    override fun getInstance(): WorkflowDao {
        TODO("Not yet implemented")
    }

    override val implicatedTables = listOf(Workflows)

    override val testModule = module {
        single<WorkflowRepository> { WorkflowRepository() }
        single { EntityTestInstanceFactory }
    }

}