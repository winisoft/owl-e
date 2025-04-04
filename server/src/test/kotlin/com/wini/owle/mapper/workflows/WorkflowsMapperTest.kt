package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class WorkflowsMapperTest {

    private lateinit var mapper: WorkflowsMapper
    private val workflowFoldersMapper: WorkflowFoldersMapper = mockk()
    private val factory: DaoFactory<WorkflowDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = WorkflowsMapper(factory, workflowFoldersMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = WorkflowDto(
            id = 99L,
            name = "workflowName",
            description = "description",
            pythonVersion = "3.8",
            parentFolder = null,
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(WorkflowDao)
        every { WorkflowDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Workflow with id=99 not found", exception.message)
        unmockkObject(WorkflowDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<WorkflowDao> {
            every { id.value } returns 1L
            every { name } returns "workflowName"
            every { description } returns "description"
            every { pythonVersion } returns "3.8"
            every { parentFolder } returns mockk()
            every { createdAt } returns LocalDateTime.parse("2023-01-01T00:00:00")
            every { updatedAt } returns LocalDateTime.parse("2023-01-02T00:00:00")
        }

        // Mock toDto behavior of the mappers
        val parentFolderDto = mockk<WorkflowFolderDto>()
        every { workflowFoldersMapper.toDto(entity.parentFolder!!) } returns parentFolderDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("workflowName", result.name)
        assertEquals("description", result.description)
        assertEquals("3.8", result.pythonVersion)
        assertEquals(parentFolderDto, result.parentFolder)
        assertEquals("2023-01-01T00:00:00", result.createdAt)
        assertEquals("2023-01-02T00:00:00", result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = WorkflowDto(
            id = 42L,
            name = "workflowName",
            description = "description",
            pythonVersion = "3.8",
            parentFolder = mockk(),
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<WorkflowDao>(relaxed = true)

        // Mock the workflowFoldersMapper
        val parentFolderDao = mockk<WorkflowFolderDao>()
        every { workflowFoldersMapper.toEntity(any()) } returns parentFolderDao

        // Set up mocks for finding existing entity
        mockkObject(WorkflowDao)
        every { WorkflowDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val pythonVersionSlot = slot<String>()
        val parentFolderSlot = slot<WorkflowFolderDao>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.pythonVersion = capture(pythonVersionSlot) } just runs
        every { existingEntity.parentFolder = capture(parentFolderSlot) } just runs
        every { existingEntity.createdAt = capture(createdAtSlot) } just runs
        every { existingEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { WorkflowDao.findById(42L) }
        verify { existingEntity.name = "workflowName" }
        verify { existingEntity.description = "description" }
        verify { existingEntity.pythonVersion = "3.8" }
        verify { existingEntity.parentFolder = parentFolderDao }
        verify { existingEntity.createdAt = LocalDateTime.parse(dto.createdAt) }
        verify { existingEntity.updatedAt = LocalDateTime.parse(dto.updatedAt) }

        // Verify the captured values
        assertEquals("workflowName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals("3.8", pythonVersionSlot.captured)
        assertEquals(parentFolderDao, parentFolderSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-02T00:00:00"), updatedAtSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = WorkflowDto(
            id = 0L,
            name = "workflowName",
            description = "description",
            pythonVersion = "3.8",
            parentFolder = mockk(),
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<WorkflowDao>(relaxed = true)

        // Mock the workflowFoldersMapper
        val parentFolderDao = mockk<WorkflowFolderDao>()
        every { workflowFoldersMapper.toEntity(any()) } returns parentFolderDao

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val pythonVersionSlot = slot<String>()
        val parentFolderSlot = slot<WorkflowFolderDao>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.pythonVersion = capture(pythonVersionSlot) } just runs
        every { newEntity.parentFolder = capture(parentFolderSlot) } just runs
        every { newEntity.createdAt = capture(createdAtSlot) } just runs
        every { newEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "workflowName" }
        verify { newEntity.description = "description" }
        verify { newEntity.pythonVersion = "3.8" }
        verify { newEntity.parentFolder = parentFolderDao }
        verify { newEntity.createdAt = LocalDateTime.parse(dto.createdAt) }
        verify { newEntity.updatedAt = LocalDateTime.parse(dto.updatedAt) }

        // Verify the captured values
        assertEquals("workflowName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals("3.8", pythonVersionSlot.captured)
        assertEquals(parentFolderDao, parentFolderSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-02T00:00:00"), updatedAtSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}