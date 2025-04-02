package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class WorkflowFolderMapperTest {

    private lateinit var mapper: WorkflowFoldersMapper
    private val factory: DaoFactory<WorkflowFolderDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = WorkflowFoldersMapper(factory)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = WorkflowFolderDto(
            id = 99L,
            name = "folderName",
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(WorkflowFolderDao)
        every { WorkflowFolderDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("WorkflowFolder with id=99 not found", exception.message)
        unmockkObject(WorkflowFolderDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<WorkflowFolderDao> {
            every { id.value } returns 1L
            every { name } returns "folderName"
            every { description } returns "description"
            every { createdAt } returns LocalDateTime.parse("2023-01-01T00:00:00")
            every { updatedAt } returns LocalDateTime.parse("2023-01-02T00:00:00")
        }

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("folderName", result.name)
        assertEquals("description", result.description)
        assertEquals("2023-01-01T00:00:00", result.createdAt)
        assertEquals("2023-01-02T00:00:00", result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = WorkflowFolderDto(id = 42L, name = "folderName", description = "description", createdAt = "2023-01-01T00:00:00", updatedAt = "2023-01-02T00:00:00")

        // Mock the existing entity that will be updated
        val existingEntity = mockk<WorkflowFolderDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowExecutionDao: WorkflowExecutionDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(WorkflowFolderDao)
        every { WorkflowFolderDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.createdAt = capture(createdAtSlot) } just runs
        every { existingEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { WorkflowFolderDao.findById(42L) }
        verify { existingEntity.name = "folderName" }
        verify { existingEntity.description = "description" }
        verify { existingEntity.createdAt = LocalDateTime.parse("2023-01-01T00:00:00") }
        verify { existingEntity.updatedAt = LocalDateTime.parse("2023-01-02T00:00:00") }

        // Verify the captured values
        assertEquals("folderName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-02T00:00:00"), updatedAtSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowFolderDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = WorkflowFolderDto(
            id = 0L,
            name = "folderName",
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<WorkflowFolderDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowExecutionDao: WorkflowExecutionDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val createdAtSlot = slot<LocalDateTime>()
        val updatedAtSlot = slot<LocalDateTime>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.createdAt = capture(createdAtSlot) } just runs
        every { newEntity.updatedAt = capture(updatedAtSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "folderName" }
        verify { newEntity.description = "description" }
        verify { newEntity.createdAt = LocalDateTime.parse("2023-01-01T00:00:00") }
        verify { newEntity.updatedAt = LocalDateTime.parse("2023-01-02T00:00:00") }

        // Verify the captured values
        assertEquals("folderName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), createdAtSlot.captured)
        assertEquals(LocalDateTime.parse("2023-01-02T00:00:00"), updatedAtSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}