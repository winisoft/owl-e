package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.steps.InquisitionDao
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


open class InquisitionRepositoryTest : DbLayerIntegrationTests<Long, InquisitionDao>() {

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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, InquisitionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): InquisitionDao {
        TODO("Not yet implemented")
    }
}