package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.inquisitionDao
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.StepRepository
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


open class InquisitionRepositoryTest : UnconstrainedDeleteRepoTest<Long, InquisitionDao>() {
    private val realRepo by KoinJavaComponent.inject<InquisitionRepository>(InquisitionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<InquisitionDao>): List<InquisitionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    executionOrder = 2
                }
                get(1).apply {
                    executionOrder = 3
                }

            }
        }
    }

    override fun modifyEntity(entity: InquisitionDao): InquisitionDao {
        return transaction {
            entity.apply {
                executionOrder = 2
            }
        }
    }

    override val implicatedTables =
        listOf(Inquisitions, Steps, Workflows, Tools, TypeDeclarations, TypeDefinitions, VersionSpecs, DataPoints)

    override val testModule: Module
        get() = module {
            single<InquisitionRepository> { InquisitionRepository() }
        }

    override fun getInstance(): InquisitionDao {
        return inquisitionDao()
    }

    override fun getList(): List<InquisitionDao> {
        return listOf(inquisitionDao(), inquisitionDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Inquisitions)
    }



    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to Inquisition`() {
        transaction {
            val step = EntityTestInstanceFactory.stepDao()
            val inquisition = EntityTestInstanceFactory.inquisitionDao(workflowStep = step)
            val inquisitionId = inquisition.id.value

            StepRepository().delete(step.id)

            val fetchedInquisition = getRepo().getById(EntityID(inquisitionId, WorkflowJunctions))
            assertNull(fetchedInquisition, "Inquisition should have been deleted on cascade from Step delete")
        }
    }
}