package com.wini.owle.repo.steps

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.revisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.workflows.DataPointRepository
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class RevisionDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.StepsModule.Revision)
    }

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to Revision`() {
        transaction {
            val step = stepDao()
            val revision = revisionDao(step = step)
            val revisionId = revision.id.value

            StepRepository().delete(step.id)

            val fetchedRevision = RevisionRepository().getById(EntityID(revisionId, Revisions))
            assertNull(fetchedRevision, "Revision should have been deleted on cascade from Step delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From DataPoint")
    fun `test 'on delete' of dependent DataPoint cascades to Revision`() {
        transaction {
            val dataPoint = dataPointDao()
            val revision = revisionDao(dataPoint = dataPoint)
            val revisionId = revision.id.value

            DataPointRepository().delete(dataPoint.id)

            val fetchedRevision = RevisionRepository().getById(EntityID(revisionId, Revisions))
            assertNull(fetchedRevision, "Revision should have been deleted on cascade from DataPoint delete")
        }
    }

}