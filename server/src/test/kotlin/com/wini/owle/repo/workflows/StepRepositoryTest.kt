package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class StepRepositoryTest: DbLayerIntegrationTests<Long, StepDao>() {


    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to Step`() {
        transaction {
            val workflow = EntityTestInstanceFactory.workflowDao()
            val step = EntityTestInstanceFactory.stepDao(workflow = workflow)
            val stepId = step.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedStep = getRepo().getById(EntityID(stepId, Steps))
            assertNull(fetchedStep, "Step should have been deleted on cascade from parent Workflow delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade DefaultNext Set Null ")
    fun `test 'on delete' of defaultNext reference for a Step sets its defaultNext value null`() {
        transaction {
            val subsequentStep = stepDao()
            val erstwhileRunStep = stepDao(defaultNext = subsequentStep)

            val repo = getRepo()
            repo.delete(subsequentStep.id)

            val erstwhileStepDefaultNext = repo.getById(erstwhileRunStep.id)?.defaultNext
            assertNull(
                erstwhileStepDefaultNext,
                "DefaultNext Step reference should have been made null on delete of referent Step"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, StepDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): StepDao {
        TODO("Not yet implemented")
    }
}