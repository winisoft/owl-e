package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import io.mockk.*
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class WorkflowsMapperTest {

    private lateinit var mapper: WorkflowsMapper
    private val workflowFoldersMapper: WorkflowFoldersMapper = mockk()
    private val factory: DaoFactory<WorkflowEntity, Long> = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()

    @BeforeEach
    fun setUp() {
        mapper = WorkflowsMapper(
            factory,
            workflowFoldersMapper,
            dateTimeConverter
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = Workflow(
            id = 99L,
            name = "workflowName",
            description = "description",
            pythonVersion = "3.8",
            parentFolder = null,
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(WorkflowEntity)
        every { WorkflowEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Workflow with id=99 not found", exception.message)
        unmockkObject(WorkflowEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<WorkflowEntity> {
            every { id.value } returns 1L
            every { name } returns "workflowName"
            every { description } returns "description"
            every { pythonVersion } returns "3.8"
            every { parentFolder } returns mockk()
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Setup mock responses from collaborators
        val parentFolderDto = mockk<WorkflowFolder>()
        val createdAtStr = "2023-01-01T00:00:00"
        val updatedAtStr = "2023-01-02T00:00:00"
        
        every { workflowFoldersMapper.toDto(entity.parentFolder!!) } returns parentFolderDto
        every { dateTimeConverter.toString(entity.createdAt) } returns createdAtStr
        every { dateTimeConverter.toString(entity.updatedAt) } returns updatedAtStr

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowFoldersMapper.toDto(entity.parentFolder!!) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.createdAt) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.updatedAt) }

        // Verify result contains values from collaborators
        assertEquals(parentFolderDto, result.parentFolder)
        assertEquals(createdAtStr, result.createdAt)
        assertEquals(updatedAtStr, result.updatedAt)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
        assertEquals(entity.name, result.name)
        assertEquals(entity.description, result.description)
        assertEquals(entity.pythonVersion, result.pythonVersion)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = Workflow(
            id = 42L,
            name = "workflowName",
            description = "description",
            pythonVersion = "3.8",
            parentFolder = mockk(),
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Create mock existing entity to update
        val existingEntity = mockk<WorkflowEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val parentFolderDao = mockk<WorkflowFolderEntity>()
        val createdAt = mockk<Instant>()
        val updatedAt = mockk<Instant>()
        
        every { workflowFoldersMapper.toEntity(dto.parentFolder!!) } returns parentFolderDao
        every { dateTimeConverter.fromString(dto.createdAt) } returns createdAt
        every { dateTimeConverter.fromString(dto.updatedAt) } returns updatedAt

        // Mock finding existing entity
        mockkObject(WorkflowEntity)
        every { WorkflowEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowFoldersMapper.toEntity(dto.parentFolder!!) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.createdAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.updatedAt) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.parentFolder = parentFolderDao }
        verify { existingEntity.createdAt = createdAt }
        verify { existingEntity.updatedAt = updatedAt }
        
        // Verify other fields were set correctly
        verify { existingEntity.name = dto.name }
        verify { existingEntity.description = dto.description }
        verify { existingEntity.pythonVersion = dto.pythonVersion }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = Workflow(
            id = 0L,
            name = "workflowName",
            description = "description",
            pythonVersion = "3.8",
            parentFolder = mockk(),
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-02T00:00:00"
        )

        // Create mock new entity
        val newEntity = mockk<WorkflowEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val parentFolderDao = mockk<WorkflowFolderEntity>()
        val createdAt = mockk<Instant>()
        val updatedAt = mockk<Instant>()
        
        every { workflowFoldersMapper.toEntity(dto.parentFolder!!) } returns parentFolderDao
        every { dateTimeConverter.fromString(dto.createdAt) } returns createdAt
        every { dateTimeConverter.fromString(dto.updatedAt) } returns updatedAt
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowFoldersMapper.toEntity(dto.parentFolder!!) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.createdAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.updatedAt) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.parentFolder = parentFolderDao }
        verify { newEntity.createdAt = createdAt }
        verify { newEntity.updatedAt = updatedAt }
        
        // Verify other fields were set correctly
        verify { newEntity.name = dto.name }
        verify { newEntity.description = dto.description }
        verify { newEntity.pythonVersion = dto.pythonVersion }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}