package com.wini.owle.repo.runs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNull


open class StepExecutionRepositoryTest : DbLayerIntegrationTests<Long, StepExecutionDao>() {

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to StepExecutions`() {
        transaction {
            val step = stepDao()
            val stepExecution = stepExecutionDao(step = step)
            val stepExecutionId = stepExecution.id.value

            StepRepository().delete(step.id)

            val fetchedStepExecution = getRepo().getById(EntityID(stepExecutionId, StepExecutions))
            assertNull(
                fetchedStepExecution,
                "StepExecution should have been deleted on cascade from parent Step delete"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, StepExecutionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): StepExecutionDao {
        TODO("Not yet implemented")
    }
}