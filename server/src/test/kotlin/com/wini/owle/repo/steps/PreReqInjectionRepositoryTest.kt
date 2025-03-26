package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.preReqInjectionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class PreReqInjectionRepositoryTest : DbLayerIntegrationTests<Long, PreReqInjectionDao>() {

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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, PreReqInjectionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): PreReqInjectionDao {
        TODO("Not yet implemented")
    }
}