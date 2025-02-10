package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.preReqInjectionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.OwleRepo
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

open class PreReqInjectionRepositoryTest : UnconstrainedDeleteRepoTest<Long, PreReqInjectionDao>() {
    private val realRepo by KoinJavaComponent.inject<PreReqInjectionRepository>(PreReqInjectionRepository::class.java)
    override fun getRepo(): OwleRepo<Long, PreReqInjectionDao> = realRepo
    override fun modifyEntities(entities: List<PreReqInjectionDao>): List<PreReqInjectionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Injection Name 1"
                }
                get(1).apply {
                    name = "Updated Injection Name 2"
                }

            }
        }
    }

    override fun modifyEntity(entity: PreReqInjectionDao): PreReqInjectionDao {
        return transaction {
            entity.apply {
                name = "Updated Injection Name"
            }
        }
    }

    override val implicatedTables = listOf(
        PrereqInjections,
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
            single<PreReqInjectionRepository> { PreReqInjectionRepository() }
        }

    override fun getInstance(): PreReqInjectionDao {
        return preReqInjectionDao()
    }

    override fun getList(): List<PreReqInjectionDao> {
        return listOf(preReqInjectionDao(), preReqInjectionDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, PrereqInjections)
    }



    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to PreReqInjection`() {
        transaction {
            val step = stepDao()
            val workflowJunction = workflowJunctionDao(sourceStep = step)
            val preReqInjection = preReqInjectionDao(junction = workflowJunction)
            val preReqInjectionId = preReqInjection.id.value

            StepRepository().delete(step.id)

            val fetchedPreReqInjectionId = getRepo().getById(EntityID(preReqInjectionId, PrereqInjections))
            assertNull(fetchedPreReqInjectionId, "PrereqInjection should have been deleted on cascade from Step delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From WorkflowJunction")
    fun `test 'on delete' of dependent WorkflowJunction cascades to PreReqInjection`() {
        transaction {
            val workflowJunction = workflowJunctionDao()
            val preReqInjection = preReqInjectionDao(junction = workflowJunction)
            val preReqInjectionId = preReqInjection.id.value

            WorkflowJunctionRepository().delete(workflowJunction.id)

            val fetchedPreReqInjectionId = getRepo().getById(EntityID(preReqInjectionId, PrereqInjections))
            assertNull(
                fetchedPreReqInjectionId,
                "PreReqInjection should have been deleted on cascade from workflowJunction delete"
            )
        }
    }
}