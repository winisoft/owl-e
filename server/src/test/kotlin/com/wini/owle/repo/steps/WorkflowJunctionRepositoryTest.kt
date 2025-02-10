package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.inquisitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.StepRepository
import com.wini.owle.repo.workflows.WorkflowRepository
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
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class WorkflowJunctionRepositoryTest : UnconstrainedDeleteRepoTest<Long, WorkflowJunctionDao>() {
    private val realRepo by KoinJavaComponent.inject<WorkflowJunctionRepository>(WorkflowJunctionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<WorkflowJunctionDao>): List<WorkflowJunctionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    this.triggerInquisition = inquisitionDao()
                }
                get(1).apply {
                    this.triggerInquisition = inquisitionDao()
                }
            }
        }
    }

    override fun modifyEntity(entity: WorkflowJunctionDao): WorkflowJunctionDao {
        return transaction {
            entity.apply {
                this.triggerInquisition = null
            }
        }
    }

    override val implicatedTables = listOf(
        WorkflowJunctions,
        Steps,
        Workflows,
        Tools,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs,
        Inquisitions,
        DataPoints
    )

    override val testModule: Module
        get() = module {
            single<WorkflowJunctionRepository> { WorkflowJunctionRepository() }
        }

    override fun getInstance(): WorkflowJunctionDao {
        return workflowJunctionDao()
    }

    override fun getList(): List<WorkflowJunctionDao> {
        return listOf(workflowJunctionDao(), workflowJunctionDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, WorkflowJunctions)
    }



    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to WorkflowJunction`() {
        transaction {
            val step = EntityTestInstanceFactory.stepDao()
            val workflowJunction = EntityTestInstanceFactory.workflowJunctionDao(sourceStep = step)
            val workflowJunctionId = workflowJunction.id.value

            StepRepository().delete(step.id)

            val fetchedWorkflowJunction = getRepo().getById(EntityID(workflowJunctionId, WorkflowJunctions))
            assertNull(fetchedWorkflowJunction, "WorkflowJunction should have been deleted on cascade from Step delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to WorkflowJunction`() {
        transaction {
            val workflow = workflowDao()
            val workflowJunction = workflowJunctionDao(targetWorkflow = workflow)
            val workflowJunctionId = workflowJunction.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedWorkflowJunction = getRepo().getById(EntityID(workflowJunctionId, WorkflowJunctions))
            assertNull(
                fetchedWorkflowJunction,
                "WorkflowJunction should have been deleted on cascade from Workflow delete"
            )
        }
    }
}