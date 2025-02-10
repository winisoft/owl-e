package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.toolParamDao
import com.wini.owle.entity.workflows.ToolParamDao
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

open class ToolParamRepositoryTest : UnconstrainedDeleteRepoTest<Long, ToolParamDao>() {

    private val realRepo by KoinJavaComponent.inject<ToolParamRepository>(ToolParamRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<ToolParamDao>): List<ToolParamDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Tool Param 1"
                }
                get(1).apply {
                    name = "Updated Tool Param 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: ToolParamDao): ToolParamDao {
        return transaction {
            entity.apply {
                name = "Updated Tool Param"
            }
        }
    }

    override val implicatedTables = listOf(ToolParams, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule = module {
        single<ToolParamRepository> { ToolParamRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): ToolParamDao {
        return toolParamDao()
    }

    override fun getList(): List<ToolParamDao> {
        return listOf(toolParamDao(), toolParamDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, ToolParams)
    }



    @Test
    @DisplayName("Delete Cascade From Tool")
    fun `test 'on delete' of dependent Tool cascades to ToolParams`() {
        transaction {
            val tool = EntityTestInstanceFactory.toolDao()
            val toolParam = EntityTestInstanceFactory.toolParamDao(tool = tool)
            val toolParamId = toolParam.id.value

            ToolRepository().delete(tool.id)

            val fetchedToolParam = getRepo().getById(EntityID(toolParamId, ToolParams))
            assertNull(fetchedToolParam, "ToolParam should have been deleted on cascade from parent Tool delete")
        }
    }
}