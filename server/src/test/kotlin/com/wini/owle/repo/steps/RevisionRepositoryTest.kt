package com.wini.owle.repo.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.revisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.workflows.DataPointRepository
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

open class RevisionRepositoryTest : UnconstrainedDeleteRepoTest<Long, RevisionDao>() {
    private val realRepo by KoinJavaComponent.inject<RevisionRepository>(RevisionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<RevisionDao>): List<RevisionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    this.step = stepDao()
                }
                get(1).apply {
                    this.step = stepDao()
                }
            }
        }
    }

    override fun modifyEntity(entity: RevisionDao): RevisionDao {
        return transaction {
            entity.apply {
                this.step = stepDao()
            }
        }
    }

    override val implicatedTables = listOf(
        Revisions,
        DataPoints,
        Workflows,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs,
        Steps,
        Tools,
        Transformations
    )

    override val testModule: Module
        get() = module {
            single<RevisionRepository> { RevisionRepository() }
        }

    override fun getInstance(): RevisionDao {
        return revisionDao()
    }

    override fun getList(): List<RevisionDao> {
        return listOf(revisionDao(), revisionDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Revisions)
    }



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
}