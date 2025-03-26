package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class ErrorWatchFnRepositoryTest : DbLayerIntegrationTests<Long, ErrorWatchFnDao>() {

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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, ErrorWatchFnDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): ErrorWatchFnDao {
        TODO("Not yet implemented")
    }
}