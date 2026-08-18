package com.wini.owle.service.workflows

import com.wini.owle.TestHelper
import com.wini.owle.allModules
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.model.workflows.WorkflowFolderNode
import com.wini.owle.services.workflows.WorkflowHierarchyService
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.junit.jupiter.api.Tag

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
@Tag("integration")
class WorkflowFolderServiceTest {

    lateinit var service: WorkflowHierarchyService

    @BeforeEach
    fun setUp() {
        stopKoin()
        val koin = startKoin {
            printLogger()
            modules(allModules)
            allowOverride(false)
        }.koin

        TestHelper.connectToTestDb()
        TestHelper.resetAll()

        service = koin.get<WorkflowHierarchyService>()
    }


    /**
     * GIVEN an empty database
     * WHEN getWorkflowHierarchy
     * THEN returns empty list
     */
    @Test
    fun `test successfully returns empty result set`() = runBlocking {

        val result = service.getHierarchy(5) // needs to suspend

        val data = (result as AsyncResult.Success<List<WorkflowFolderNode>>).data

        assertTrue(data.isEmpty())

    }

    /**
     * GIVEN two workflows in the same root folder
     * WHEN getByFolder
     * THEN returns those two workflows
     */
    @Test
    fun `GIVEN two workflows in the same root folder WHEN getByFolder THEN returns those workflows`() = runBlocking {

        var folder: WorkflowFolderEntity? = null
        var version: VersionEntity? = null
        var pythonVersion: PythonVersionEntity? = null

        transaction {
            version = VersionEntity.new(100UL) {
                this.epoch = 0UL
                this.major = 1UL
                this.minor = 0UL
                this.micro = 0UL
                this.phase = PrereleasePhase.FINAL
                this.phaseNumber = null
                this.postNumber = null
                this.devNumber = null
                this.local = null
                this.hasOverflow = false
            }
        }
        transaction {
            folder = WorkflowFolderEntity.new(200UL) {
                this.name = "Folder Name"
            }
            pythonVersion = PythonVersionEntity.new(version!!.id.value) {}
        }

        transaction {
            WorkflowEntity.new(100UL) {
                this.name = "WF1"
                this.parentFolder = folder
                this.pythonVersion = pythonVersion!!
            }
            WorkflowEntity.new(200UL) {
                this.name = "WF2"
                this.parentFolder = folder
                this.pythonVersion = pythonVersion!!
            }
        }

        val result = service.getByFolder(folderId = folder!!.id.value)
        assertTrue { result is AsyncResult.Success }

        val names = (result as AsyncResult.Success<List<Workflow>>).data.map { it.name }
        assertEquals(2, names.size)
        assertTrue("WF1" in names && "WF2" in names)

    }

}

