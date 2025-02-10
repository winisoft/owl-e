package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.repo.BaseOwleRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class DataPointRepositoryTest : UnconstrainedDeleteRepoTest<Long, DataPointDao>() {

    private val realRepo by KoinJavaComponent.inject<DataPointRepository>(DataPointRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<DataPointDao>): List<DataPointDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    description = "Updated data point description 1"
                }
                get(1).apply {
                    description = "Updated data point description 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: DataPointDao): DataPointDao {
        return transaction {
            entity.apply {
                description = "Updated data point description"
            }
        }
    }

    override val implicatedTables = listOf(DataPoints, Workflows, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule = module {
        single<DataPointRepository> { DataPointRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): DataPointDao {
        return dataPointDao()
    }

    override fun getList(): List<DataPointDao> {
        return listOf(dataPointDao(), dataPointDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, DataPoints)
    }



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
}