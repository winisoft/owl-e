package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNull


open class ToolParamRepositoryTest : DbLayerIntegrationTests<Long, ToolParamDao>() {

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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, ToolParamDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): ToolParamDao {
        TODO("Not yet implemented")
    }
}