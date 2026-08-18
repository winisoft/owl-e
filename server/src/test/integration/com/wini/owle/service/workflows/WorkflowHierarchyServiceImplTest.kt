package com.wini.owle.service.workflows

import com.wini.owle.TestHelper
import com.wini.owle.allModules
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.InstanceDbFakeEntityFactory.versionEntity
import com.wini.owle.entity.InstanceDbFakeEntityFactory.workflowFolderEntity
import com.wini.owle.entity.environs.version.PythonVersionEntity
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


@Tag("integration")
abstract class WorkflowHierarchyServiceImplTest {

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

        val folder = transaction {
            workflowFolderEntity(id = 200UL, name = "Folder Name")
        }
        transaction {
            val version = transaction { versionEntity() }
            val pyVersion = PythonVersionEntity.new(version.id.value) {}
            WorkflowEntity.new(100UL) {
                this.name = "WF1"
                this.parentFolder = folder
                this.pythonVersion = pyVersion
            }
            WorkflowEntity.new(200UL) {
                this.name = "WF2"
                this.parentFolder = folder
                this.pythonVersion = pyVersion
            }
        }

        val result = service.getByFolder(folderId = folder.id.value)
        assertTrue { result is AsyncResult.Success<*> }


        val names = (result as AsyncResult.Success<List<Workflow>>).data
            .map { it.name }

        assertEquals(2, names.size)
        assertTrue("WF1" in names && "WF2" in names)

    }

//
//    @Test
//    fun `GIVEN root folder with child and workflow WHEN getWorkflowHierarchy THEN returns expected structure`() {
//        // GIVEN
//        val rootFolderEntity = mockk<WorkflowFolderEntity>(relaxed = true)
//        val childFolderEntity = mockk<WorkflowFolderEntity>(relaxed = true)
//        val workflowEntity = mockk<WorkflowEntity>(relaxed = true)
//
//        every { rootFolderEntity.id.value } returns 1UL
//        every { rootFolderEntity.parentFolder } returns null
//        every { childFolderEntity.id.value } returns 2UL
//        every { childFolderEntity.parentFolder } returns rootFolderEntity
//        every { workflowEntity.parentFolder } returns rootFolderEntity
//
//        val rootDto = WorkflowFolder(id = 1UL, parentFolder = null, name = "root", description = null, createdAt = "", updatedAt = "")
//        val childDto = WorkflowFolder(id = 2UL, parentFolder = rootDto, name = "child", description = null, createdAt = "", updatedAt = "")
//        val workflowDto = Workflow(id = 10UL, name = "workflow", description = null, pythonVersion = mockk(), parentFolder = rootDto, createdAt = "", updatedAt = "")
//
//        every { folderRepo.getWhere(any()) } answers {
//            val p = arg<(WorkflowFolderEntity) -> Boolean>(0)
//            listOfNotNull(rootFolderEntity.takeIf(p), childFolderEntity.takeIf(p))
//        }
//        every { workflowRepo.getWhere(any()) } answers {
//            val p = arg<(WorkflowEntity) -> Boolean>(0)
//            listOfNotNull(workflowEntity.takeIf(p))
//        }
//
//        every { workflowFolderMapper.toDto(rootFolderEntity) } returns rootDto
//        every { workflowFolderMapper.toDto(childFolderEntity) } returns childDto
//        every { workflowMapper.toDto(workflowEntity) } returns workflowDto
//
//        // WHEN
//        val result = service.getHierarchy(5)
//
//        // THEN
//        val data = (result as AsyncResult.Success<List<WorkflowFolderNode>>).data
//
//        assertEquals(1, data.size)
//        with(data.first()) {
//            assertEquals("root", folder.name)
//            assertEquals(1, workflows.size)
//            assertEquals("workflow", workflows.first().name)
//            assertEquals(1, childFolders.size)
//            assertEquals("child", childFolders.first().folder.name)
//        }
//    }

}