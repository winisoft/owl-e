package com.wini.owle.repo.steps

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.provisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolParamDao
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.StepRepository
import com.wini.owle.repo.steps.ToolParamRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class ProvisionDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.StepsModule.Provision)
    }

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to Provision`() {
        transaction {
            val step = stepDao()
            val provision = provisionDao(step = step)
            val provisionId = provision.id.value

            StepRepository().delete(step.id)

            val fetchedProvision = ProvisionRepository().getById(EntityID(provisionId, Provisions))
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

            val fetchedProvision = ProvisionRepository().getById(EntityID(provisionId, Provisions))
            assertNull(fetchedProvision, "Provision should have been deleted on cascade from ToolParam delete")
        }
    }

}