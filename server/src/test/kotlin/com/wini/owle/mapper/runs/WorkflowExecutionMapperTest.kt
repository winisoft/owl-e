package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ExecutionStatusDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.enums.ExecutionStatus
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.runs.enums.ExecutionStatusMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import io.mockk.*
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class WorkflowExecutionMapperTest {

    private lateinit var mapper: WorkflowExecutionsMapper
    private val workflowsMapper: WorkflowsMapper = mockk()
    private val usersMapper: UsersMapper = mockk()
    private val stepExecutionMapper: StepExecutionsMapper = mockk()
    private val factory: DaoFactory<WorkflowExecutionDao, Long> = mockk()
    private val executionStatusMapper: ExecutionStatusMapper = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()

    @BeforeEach
    fun setUp() {
        mapper = WorkflowExecutionsMapper(
            factory,
            workflowsMapper,
            usersMapper,
            stepExecutionMapper,
            executionStatusMapper,
            dateTimeConverter
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTO
        val dto = WorkflowExecutionDto(
            id = 99L,
            workflow = mockk(),
            user = mockk(),
            startedAt = "2023-10-01T10:00:00",
            completedAt = "2023-10-01T11:00:00",
            status = mockk(),
            stepExecutions = emptyList()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(WorkflowExecutionDao)
        every { WorkflowExecutionDao.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("WorkflowExecution with id=99 not found", exception.message)
        unmockkObject(WorkflowExecutionDao)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<WorkflowExecutionDao> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { user } returns mockk()
            every { startedAt } returns mockk()
            every { completedAt } returns mockk()
            every { status } returns mockk()
        }

        // Setup mock responses from collaborators
        val workflowDto = mockk<WorkflowDto>()
        val userDto = mockk<UserDto>()
        val startedAtStr = "2025-04-04T12:34:56Z"
        val completedAtStr = "2025-05-04T12:34:56Z"
        val statusDto = ExecutionStatusDto.Failed
        
        every { workflowsMapper.toDto(entity.workflow) } returns workflowDto
        every { usersMapper.toDto(entity.user) } returns userDto
        every { dateTimeConverter.toString(entity.startedAt) } returns startedAtStr
        every { dateTimeConverter.toString(entity.completedAt!!) } returns completedAtStr
        every { executionStatusMapper.toDto(entity.status) } returns statusDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowsMapper.toDto(entity.workflow) }
        verify(exactly = 1) { usersMapper.toDto(entity.user) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.startedAt) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.completedAt!!) }
        verify(exactly = 1) { executionStatusMapper.toDto(entity.status) }

        // Verify result contains values from collaborators
        assertEquals(workflowDto, result.workflow)
        assertEquals(userDto, result.user)
        assertEquals(startedAtStr, result.startedAt)
        assertEquals(completedAtStr, result.completedAt)
        assertEquals(statusDto, result.status)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = WorkflowExecutionDto(
            id = 42L,
            workflow = mockk(),
            user = mockk(),
            startedAt = "2025-04-04T12:34:56Z",
            completedAt = "2025-05-04T12:34:56Z",
            status = ExecutionStatusDto.Failed,
            stepExecutions = emptyList()
        )

        // Create mock existing entity to update
        val existingEntity = mockk<WorkflowExecutionDao>(relaxed = true)

        // Setup mock responses from collaborators
        val workflowDao = mockk<WorkflowDao>()
        val userDao = mockk<UserDao>()
        val startedAt = mockk<Instant>(name = "startedAt")
        val completedAt = mockk<Instant>(name = "completedAt")
        val status = ExecutionStatus.FAILED
        
        every { workflowsMapper.toEntity(dto.workflow) } returns workflowDao
        every { usersMapper.toEntity(dto.user) } returns userDao
        every { dateTimeConverter.fromString(dto.startedAt) } returns startedAt
        every { dateTimeConverter.fromString(dto.completedAt!!) } returns completedAt
        every { executionStatusMapper.toEntity(dto.status) } returns status

        // Mock finding existing entity
        mockkObject(WorkflowExecutionDao)
        every { WorkflowExecutionDao.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowsMapper.toEntity(dto.workflow) }
        verify(exactly = 1) { usersMapper.toEntity(dto.user) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.startedAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.completedAt!!) }
        verify(exactly = 1) { executionStatusMapper.toEntity(dto.status) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.workflow = workflowDao }
        verify { existingEntity.user = userDao }
        verify { existingEntity.startedAt = startedAt }
        verify { existingEntity.completedAt = completedAt }
        verify { existingEntity.status = status }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowExecutionDao)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = WorkflowExecutionDto(
            id = 0L,
            workflow = mockk(),
            user = mockk(),
            startedAt = "2025-04-04T12:34:56Z",
            completedAt = "2025-05-04T12:34:56Z",
            status = ExecutionStatusDto.Failed,
            stepExecutions = emptyList()
        )

        // Create mock new entity
        val newEntity = mockk<WorkflowExecutionDao>(relaxed = true)

        // Setup mock responses from collaborators
        val workflowDao = mockk<WorkflowDao>()
        val userDao = mockk<UserDao>()
        val startedAt = mockk<Instant>(name = "startedAt")
        val completedAt = mockk<Instant>(name = "completedAt")
        val status = ExecutionStatus.FAILED
        
        every { workflowsMapper.toEntity(dto.workflow) } returns workflowDao
        every { usersMapper.toEntity(dto.user) } returns userDao
        every { dateTimeConverter.fromString(dto.startedAt) } returns startedAt
        every { dateTimeConverter.fromString(dto.completedAt!!) } returns completedAt
        every { executionStatusMapper.toEntity(dto.status) } returns status
        
        // Setup factory mock
        every { factory.create() } returns newEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify factory was used to create new entity
        verify(exactly = 1) { factory.create() }

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowsMapper.toEntity(dto.workflow) }
        verify(exactly = 1) { usersMapper.toEntity(dto.user) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.startedAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.completedAt!!) }
        verify(exactly = 1) { executionStatusMapper.toEntity(dto.status) }

        // Verify entity fields were set with values from collaborators
        verify { newEntity.workflow = workflowDao }
        verify { newEntity.user = userDao }
        verify { newEntity.startedAt = startedAt }
        verify { newEntity.completedAt = completedAt }
        verify { newEntity.status = status }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}