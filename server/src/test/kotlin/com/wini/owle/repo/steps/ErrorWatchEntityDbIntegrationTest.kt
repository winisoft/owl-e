package com.wini.owle.repo.steps

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.ErrorWatches
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class ErrorWatchEntityDbIntegrationTest {


    private val implicatedTables = TestHelper.ImplicatedTables.StepsModule.ErrorWatch

    @BeforeEach
    fun setUp() {
        TestHelper.refreshTestDbData(implicatedTables)
        TestHelper.connectToTestDb()
    }

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to ErrorWatchFn`() {
        transaction {
            val step = stepDao()
            val errorWatch = errorWatchDao(step = step)

            StepRepository().delete(step.id)

            val fetchedErrorWatch = ErrorWatchRepository().getById(EntityID(errorWatch.id.value, ErrorWatches))
            assertNull(fetchedErrorWatch, "ErrorWatch should have been deleted on cascade from Step delete")
        }
    }

}