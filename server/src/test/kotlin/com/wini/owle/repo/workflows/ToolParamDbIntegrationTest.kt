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


open class ToolParamDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.WorkflowsModule.ToolParam)
    }

    @Test
    @DisplayName("Delete Cascade From Tool")
    fun `test 'on delete' of dependent Tool cascades to ToolParams`() {
        transaction {
            val tool = EntityTestInstanceFactory.toolDao()
            val toolParam = EntityTestInstanceFactory.toolParamDao(tool = tool)
            val toolParamId = toolParam.id.value

            ToolRepository().delete(tool.id)

            val fetchedToolParam = ToolParamRepository().getById(EntityID(toolParamId, ToolParams))
            assertNull(fetchedToolParam, "ToolParam should have been deleted on cascade from parent Tool delete")
        }
    }

}