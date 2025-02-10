package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.provisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolParamDao
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.StepRepository
import com.wini.owle.repo.workflows.ToolParamRepository
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

open class ProvisionRepositoryTest : UnconstrainedDeleteRepoTest<Long, ProvisionDao>() {
    private val realRepo by KoinJavaComponent.inject<ProvisionRepository>(ProvisionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<ProvisionDao>): List<ProvisionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    this.toolParam = toolParamDao()
                }
                get(1).apply {
                    this.toolParam = toolParamDao()
                }
            }
        }
    }

    override fun modifyEntity(entity: ProvisionDao): ProvisionDao {
        return transaction {
            entity.apply {
                this.toolParam = toolParamDao()
            }
        }
    }

    override val implicatedTables =
        listOf(Provisions, Steps, Workflows, Tools, TypeDeclarations, TypeDefinitions, VersionSpecs, ToolParams)

    override val testModule: Module
        get() = module {
            single<ProvisionRepository> { ProvisionRepository() }
        }

    override fun getInstance(): ProvisionDao {
        return provisionDao()
    }

    override fun getList(): List<ProvisionDao> {
        return listOf(provisionDao(), provisionDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Provisions)
    }



    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to Provision`() {
        transaction {
            val step = stepDao()
            val provision = provisionDao(step = step)
            val provisionId = provision.id.value

            StepRepository().delete(step.id)

            val fetchedProvision = getRepo().getById(EntityID(provisionId, Provisions))
            assertNull(fetchedProvision, "Provision should have been deleted on cascade from Step delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From ToolParam")
    fun `test 'on delete' of dependent ToolParam cascades to Provision`() {
        transaction {
            val toolParam = toolParamDao()
            val provision = provisionDao(toolParam = toolParam)
            val provisionId = provision.id.value

            ToolParamRepository().delete(toolParam.id)

            val fetchedProvision = getRepo().getById(EntityID(provisionId, Provisions))
            assertNull(fetchedProvision, "Provision should have been deleted on cascade from ToolParam delete")
        }
    }
}