package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.revisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.workflows.DataPointRepository
import com.wini.owle.repo.workflows.StepRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class RevisionRepositoryTest : DbLayerIntegrationTests<Long, RevisionDao>() {

    @Test
    @DisplayName("Delete Cascade From Step")
    fun `test 'on delete' of dependent Step cascades to Revision`() {
        transaction {
            val step = stepDao()
            val revision = revisionDao(step = step)
            val revisionId = revision.id.value

            StepRepository().delete(step.id)

            val fetchedRevision = getRepo().getById(EntityID(revisionId, Revisions))
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

            val fetchedRevision = getRepo().getById(EntityID(revisionId, Revisions))
            assertNull(fetchedRevision, "Revision should have been deleted on cascade from DataPoint delete")
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, RevisionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): RevisionDao {
        TODO("Not yet implemented")
    }
}