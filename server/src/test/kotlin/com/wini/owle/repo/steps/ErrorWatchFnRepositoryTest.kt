package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchFnDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowFolderDao
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class ErrorWatchFnRepositoryTest : UnconstrainedDeleteRepoTest<Long, ErrorWatchFnDao>() {

    override fun getRepo() = ErrorWatchFnRepository()
    override fun modifyEntities(entities: List<ErrorWatchFnDao>): List<ErrorWatchFnDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    position = 998
                }
                get(1).apply {
                    position = 999
                }
            }
        }
    }

    override fun modifyEntity(entity: ErrorWatchFnDao): ErrorWatchFnDao {
        return transaction {
            entity.apply {
                position = 999
            }
        }
    }

    override val implicatedTables = listOf(
        ErrorWatches,
        ErrorWatchFns,
        Steps,
        Workflows,
        Tools,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs
    )

    override val testModule: Module
        get() = module {}

    override fun getInstance(): ErrorWatchFnDao {
        val executingStep = stepDao(workflow = workflowDao(), tool = toolDao())
        val redirectStep = stepDao(workflow = workflowDao(), tool = toolDao())
        return errorWatchFnDao(errorWatch = errorWatchDao(onMetStep = redirectStep, step = executingStep))
    }

    override fun getList(): List<ErrorWatchFnDao> {
        val executingStep1 = stepDao(workflow = workflowDao(), tool = toolDao())
        val redirectStep1 = stepDao(workflow = workflowDao(), tool = toolDao())
        val errorWatchFn1 = errorWatchFnDao(errorWatch = errorWatchDao(onMetStep = redirectStep1, step = executingStep1))
        val executingStep2 = stepDao(workflow = workflowDao(), tool = toolDao())
        val redirectStep2 = stepDao(workflow = workflowDao(), tool = toolDao())
        val errorWatchFn2 = errorWatchFnDao(errorWatch = errorWatchDao(onMetStep = redirectStep2, step = executingStep2))
        return listOf(errorWatchFn1, errorWatchFn2)
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, ErrorWatchFns)
    }



    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to ErrorWatch`() {
        transaction {
            val step = stepDao()
            val errorWatch = errorWatchDao(step = step)
            val errorWatchId = errorWatch.id.value

            StepRepository().delete(step.id)

            val fetchedErrorWatch = getRepo().getById(EntityID(errorWatchId, ErrorWatches))
            assertNull(fetchedErrorWatch, "ErrorWatch should have been deleted on cascade from Step delete")
        }
    }
}