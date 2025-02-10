package com.wini.owle.repo.workflows

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.BaseOwleRepoTest
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


open class WorkflowRepositoryTest : UnconstrainedDeleteRepoTest<Long, WorkflowDao>() {

    private val realRepo by KoinJavaComponent.inject<WorkflowRepository>(WorkflowRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<WorkflowDao>): List<WorkflowDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    description = "Updated workflow description 1"
                }
                get(1).apply {
                    description = "Updated workflow description 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: WorkflowDao): WorkflowDao {
        return transaction {
            entity.apply {
                description = "Updated workflow description"
            }
        }
    }

    override val implicatedTables = listOf(Workflows)

    override val testModule = module {
        single<WorkflowRepository> { WorkflowRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): WorkflowDao {
        return transaction {
            workflowDao()
        }
    }

    override fun getList(): List<WorkflowDao> {
        return listOf(workflowDao(), workflowDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Workflows)
    }


}