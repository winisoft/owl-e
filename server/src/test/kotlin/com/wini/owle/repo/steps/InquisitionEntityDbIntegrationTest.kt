package com.wini.owle.repo.steps

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class InquisitionEntityDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.StepsModule.Inquisition)
    }

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to Inquisition`() {
        transaction {
            val step = EntityTestInstanceFactory.stepDao()
            val inquisition = EntityTestInstanceFactory.inquisitionDao()
            val inquisitionId = inquisition.id.value

            StepRepository().delete(step.id)

            val fetchedInquisition = InquisitionRepository().getById(EntityID(inquisitionId, WorkflowJunctions))
            assertNull(fetchedInquisition, "Inquisition should have been deleted on cascade from Step delete")
        }
    }

}