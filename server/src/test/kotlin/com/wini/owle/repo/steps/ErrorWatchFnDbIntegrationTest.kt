package com.wini.owle.repo.steps

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class ErrorWatchFnDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.StepsModule.ErrorWatchFn)
    }

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to ErrorWatch`() {
        transaction {
            val step = stepDao()
            val errorWatch = errorWatchDao(step = step)
            val errorWatchId = errorWatch.id.value

            StepRepository().delete(step.id)

            val fetchedErrorWatch = ErrorWatchFnRepository().getById(EntityID(errorWatchId, ErrorWatches))
            assertNull(fetchedErrorWatch, "ErrorWatch should have been deleted on cascade from Step delete")
        }
    }

}