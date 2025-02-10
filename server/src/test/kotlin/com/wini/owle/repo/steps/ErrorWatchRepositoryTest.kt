package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchFnDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.entity.steps.ErrorWatchFnDao
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

open class ErrorWatchRepositoryTest : UnconstrainedDeleteRepoTest<Long, ErrorWatchDao>() {
    private val realRepo by KoinJavaComponent.inject<ErrorWatchRepository>(ErrorWatchRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<ErrorWatchDao>): List<ErrorWatchDao> {
        return transaction {
        entities.apply {
                get(0).apply {
                    this.errorType = "Error Type 1"
                }
                get(1).apply {
                    this.errorType = "Error Type 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: ErrorWatchDao): ErrorWatchDao {
        return transaction {
            entity.apply {
                this.errorType = "Error Type 1"
            }
        }
    }

    override val implicatedTables = listOf(
        ErrorWatchFns,
        ErrorWatches,
        Steps,
        Workflows,
        Tools,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs
    )

    override val testModule: Module
        get() = module {
            single<ErrorWatchRepository> { ErrorWatchRepository() }
        }

    override fun getInstance(): ErrorWatchDao {
        return errorWatchDao()
    }

    override fun getList(): List<ErrorWatchDao> {
        return listOf(errorWatchDao(), errorWatchDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, ErrorWatches)
    }




    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to ErrorWatchFn`() {
        transaction {
            val step = stepDao()
            val errorWatch = errorWatchDao(step = step)

            StepRepository().delete(step.id)

            val fetchedErrorWatch = getRepo().getById(makeEntityID(errorWatch.id.value))
            assertNull(fetchedErrorWatch, "ErrorWatch should have been deleted on cascade from Step delete")
        }
    }
}