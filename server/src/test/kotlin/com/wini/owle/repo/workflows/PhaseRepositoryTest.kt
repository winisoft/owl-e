package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.phaseDao
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class PhaseRepositoryTest: UnconstrainedDeleteRepoTest<Long, PhaseDao>() {

    override val implicatedTables: List<IdTable<*>>
        get() = listOf(WorkflowFolders, Workflows, Steps, DataPoints)

    override val testModule: Module
        get() = module { }

    override fun getInstance(): PhaseDao {
        return phaseDao()
    }

    override fun getList(): List<PhaseDao> {
        return listOf(phaseDao(), phaseDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Phases)
    }

    override fun getRepo(): OwleRepo<Long, PhaseDao> {
        return PhaseRepository()
    }

    override fun modifyEntities(entities: List<PhaseDao>): List<PhaseDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Modified 1"
                }
                get(1).apply {
                    name = "Modified 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: PhaseDao): PhaseDao {
        return transaction {
            entity.apply {
                name = "Modified"
            }
        }
    }



    @Test
    @DisplayName("Delete Cascade From WorkflowFolder")
    fun `test 'on delete' of dependent WorkflowFolder cascades to Phase delete`() {
        transaction {
            val parentFolder = EntityTestInstanceFactory.workflowFolderDao()
            val workflow = EntityTestInstanceFactory.workflowDao(parentFolder = parentFolder)
            val phase = EntityTestInstanceFactory.phaseDao(workflow = workflow)
            val phaseId = phase.id.value

            WorkflowFolderRepository().delete(parentFolder.id)

            val fetchedPhase = getRepo().getById(EntityID(phaseId, Phases))
            assertNull(fetchedPhase, "Phase should have been deleted on cascade from parent WorkflowFolder delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From Workflow")
    fun `test 'on delete' of dependent Workflow cascades to Phase delete`() {
        transaction {
            val workflow = EntityTestInstanceFactory.workflowDao()
            val phase = EntityTestInstanceFactory.phaseDao(workflow = workflow)
            val phaseId = phase.id.value

            WorkflowRepository().delete(workflow.id)

            val fetchedPhase = getRepo().getById(EntityID(phaseId, Phases))
            assertNull(fetchedPhase, "Phase should have been deleted on cascade from parent Workflow delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From Parent Phase")
    fun `test self-referential cascaded hierarchical delete`() {
        transaction {
            val parentPhase = EntityTestInstanceFactory.phaseDao()
            val childPhase = EntityTestInstanceFactory.phaseDao(parentPhase = parentPhase)
            val childPhaseId = childPhase.id.value

            val repo = getRepo()
            repo.delete(parentPhase.id)

            val fetchedPhase = repo.getById(EntityID(childPhaseId, Phases))
            assertNull(fetchedPhase, "Phase child should have been deleted on cascade from parent Phase delete")
        }
    }
}