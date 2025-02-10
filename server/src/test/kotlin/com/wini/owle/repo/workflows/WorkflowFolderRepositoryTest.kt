package com.wini.owle.repo.workflows

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.workflowFolderDao
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.repo.BaseOwleRepoTest
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

open class WorkflowFolderRepositoryTest: UnconstrainedDeleteRepoTest<Long, WorkflowFolderDao>() {

    private val realRepo by KoinJavaComponent.inject<WorkflowFolderRepository>(WorkflowFolderRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<WorkflowFolderDao>): List<WorkflowFolderDao> {
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

    override fun modifyEntity(entity: WorkflowFolderDao): WorkflowFolderDao {
        return transaction {
            entity.apply {
                name = "Modified"
            }
        }
    }

    override val implicatedTables = listOf(WorkflowFolders)

    override val testModule: Module
        get() = module {
            single<WorkflowFolderRepository> { WorkflowFolderRepository() }
        }

    override fun getInstance(): WorkflowFolderDao {
        return workflowFolderDao()
    }

    override fun getList(): List<WorkflowFolderDao> {
        return listOf(workflowFolderDao(), workflowFolderDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, WorkflowFolders)
    }



    @Test
    @DisplayName("Delete Cascade From Parent Folder")
    fun `test self-referential cascaded hierarchical delete`() {
        transaction {
            val parentFolder = workflowFolderDao()
            val childFolder = workflowFolderDao(parentFolder = parentFolder)
            val childFolderId = childFolder.id.value

            val repo = getRepo()
            repo.delete(parentFolder.id)

            val fetchedWorkflowFolder = repo.getById(EntityID(childFolderId, WorkflowFolders))
            assertNull(
                fetchedWorkflowFolder,
                "WorkflowFolder child should have been deleted on cascade from parent WorkflowFolder delete"
            )
        }
    }
}