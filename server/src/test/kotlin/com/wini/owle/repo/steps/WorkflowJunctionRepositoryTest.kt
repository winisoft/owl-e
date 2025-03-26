package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.workflows.StepRepository
import com.wini.owle.repo.workflows.WorkflowRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class WorkflowJunctionRepositoryTest : DbLayerIntegrationTests<Long, WorkflowJunctionDao>() {


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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, WorkflowJunctionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): WorkflowJunctionDao {
        TODO("Not yet implemented")
    }
}