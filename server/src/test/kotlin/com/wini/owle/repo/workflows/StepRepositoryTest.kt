package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.repo.BaseOwleRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class StepRepositoryTest: UnconstrainedDeleteRepoTest<Long, StepDao>() {
    private val realRepo by KoinJavaComponent.inject<StepRepository>(StepRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<StepDao>): List<StepDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Step Name 1"
                }
                get(1).apply {
                    name = "Updated Step Name 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: StepDao): StepDao {
        return transaction {
            entity.apply {
                name = "Updated Step Name"
            }
        }
    }

    override val implicatedTables = listOf(Steps, Workflows, Tools, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule = module {
        single<StepRepository> { StepRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): StepDao {
        return stepDao()
    }

    override fun getList(): List<StepDao> {
        return listOf(stepDao(), stepDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Steps)
    }



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
}