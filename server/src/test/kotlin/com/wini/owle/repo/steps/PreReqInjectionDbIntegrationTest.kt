package com.wini.owle.repo.steps

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.preReqInjectionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class PreReqInjectionDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.StepsModule.PreReqInjection)
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

            val fetchedPreReqInjectionId = PreReqInjectionRepository().getById(EntityID(preReqInjectionId, PrereqInjections))
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

            val fetchedPreReqInjectionId = PreReqInjectionRepository().getById(EntityID(preReqInjectionId, PrereqInjections))
            assertNull(
                fetchedPreReqInjectionId,
                "PreReqInjection should have been deleted on cascade from workflowJunction delete"
            )
        }
    }

}