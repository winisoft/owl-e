package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class PhaseRepositoryTest: DbLayerIntegrationTests<Long, PhaseDao>() {


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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")

    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, PhaseDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): PhaseDao {
        TODO("Not yet implemented")
    }
}