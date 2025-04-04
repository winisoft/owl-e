package com.wini.owle.repo.runs

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class StepExecutionDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.RunsModule.StepExecution)
    }

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to StepExecutions`() {
        transaction {
            val step = stepDao()
            val stepExecution = stepExecutionDao(step = step)
            val stepExecutionId = stepExecution.id.value

            StepRepository().delete(step.id)

            val fetchedStepExecution = StepExecutionRepository().getById(EntityID(stepExecutionId, StepExecutions))
            assertNull(
                fetchedStepExecution,
                "StepExecution should have been deleted on cascade from parent Step delete"
            )
        }
    }

}