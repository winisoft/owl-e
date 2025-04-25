package com.wini.owle.repo.workflows

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.repo.OwleRepoImplTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class DataPointEntityDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.WorkflowsModule.DataPoint)
    }

    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to DataPoints`() {
        transaction {
            val workflow = EntityTestInstanceFactory.workflowDao()
            val dataPoint = EntityTestInstanceFactory.dataPointDao(workflow = workflow)
            val dataPointId = dataPoint.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedDataPoint = DataPointRepository().getById(EntityID(dataPointId, DataPoints))
            assertNull(fetchedDataPoint, "DataPoint should have been deleted on cascade from parent Workflow delete")
        }
    }

}