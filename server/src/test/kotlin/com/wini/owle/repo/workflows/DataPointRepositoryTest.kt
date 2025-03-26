package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class DataPointRepositoryTest : DbLayerIntegrationTests<Long, DataPointDao>() {


    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to DataPoints`() {
        transaction {
            val workflow = EntityTestInstanceFactory.workflowDao()
            val dataPoint = EntityTestInstanceFactory.dataPointDao(workflow = workflow)
            val dataPointId = dataPoint.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedDataPoint = getRepo().getById(EntityID(dataPointId, DataPoints))
            assertNull(fetchedDataPoint, "DataPoint should have been deleted on cascade from parent Workflow delete")
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, DataPointDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): DataPointDao {
        TODO("Not yet implemented")
    }
}