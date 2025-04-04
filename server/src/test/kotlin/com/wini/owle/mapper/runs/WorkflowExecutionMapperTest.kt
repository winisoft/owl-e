package com.wini.owle.mapper.runs

import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.ExecutionStatus
import com.wini.owle.entity.runs.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import io.mockk.*
import kotlinx.datetime.LocalDateTime
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

    @BeforeEach
    fun setUp() {
        mapper = WorkflowExecutionsMapper(factory, workflowsMapper, usersMapper, stepExecutionMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = WorkflowExecutionDto(
            id = 99L,
            workflow = mockk(),
            user = mockk(),
            startedAt = LocalDateTime.parse("2023-10-01T10:00:00"),
            completedAt = LocalDateTime.parse("2023-10-01T11:00:00"),
            status = mockk(),
            stepExecutions = emptyList()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(WorkflowExecutionDao)
        every { WorkflowExecutionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("WorkflowExecution with id=99 not found", exception.message)
        unmockkObject(WorkflowExecutionDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<WorkflowExecutionDao> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { user } returns mockk()
            every { startedAt } returns LocalDateTime.parse("2023-10-01T10:00:00")
            every { completedAt } returns LocalDateTime.parse("2023-10-01T11:00:00")
            every { status.toDto() } returns mockk()
        }

        // Mock the behavior of workflowsMapper, usersMapper, and stepExecutionMapper
        val workflowDto = mockk<WorkflowDto>()
        val userDto = mockk<UserDto>()
        every { workflowsMapper.toDto(entity.workflow) } returns workflowDto
        every { usersMapper.toDto(entity.user) } returns userDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowDto, result.workflow)
        assertEquals(userDto, result.user)
        assertEquals(LocalDateTime.parse("2023-10-01T10:00:00"), result.startedAt)
        assertEquals(LocalDateTime.parse("2023-10-01T11:00:00"), result.completedAt)
        assertEquals(entity.status.toDto(), result.status)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = WorkflowExecutionDto(
            id = 42L,
            workflow = mockk(),
            user = mockk(),
            startedAt = LocalDateTime.parse("2023-10-01T12:00:00"),
            completedAt = LocalDateTime.parse("2023-10-01T13:00:00"),
            status = mockk(),
            stepExecutions = emptyList()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<WorkflowExecutionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowDao: WorkflowDao = mockk()
        val userDao: UserDao = mockk()

        // Mock the behavior of dependency mappers
        every { workflowsMapper.toEntity(any()) } returns workflowDao
        every { usersMapper.toEntity(any()) } returns userDao
        
        // Set up mocks for finding existing entity
        mockkObject(WorkflowExecutionDao)
        every { WorkflowExecutionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowSlot = slot<WorkflowDao>()
        val userSlot = slot<UserDao>()
        val startedAtSlot = slot<LocalDateTime>()
        val completedAtSlot = slot<LocalDateTime>()
        val statusSlot = slot<ExecutionStatus>()

        every { existingEntity.workflow = capture(workflowSlot) } just runs
        every { existingEntity.user = capture(userSlot) } just runs
        every { existingEntity.startedAt = capture(startedAtSlot) } just runs
        every { existingEntity.completedAt = capture(completedAtSlot) } just runs
        every { existingEntity.status = capture(statusSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { WorkflowExecutionDao.findById(42L) }
        verify { existingEntity.workflow = workflowDao }
        verify { existingEntity.user = userDao }
        verify { existingEntity.startedAt = dto.startedAt }
        verify { existingEntity.completedAt = dto.completedAt }
        verify { existingEntity.status = ExecutionStatus.fromDto(dto.status) }

        // Verify the captured values
        assertEquals(workflowDao, workflowSlot.captured)
        assertEquals(userDao, userSlot.captured)
        assertEquals(dto.startedAt, startedAtSlot.captured)
        assertEquals(dto.completedAt, completedAtSlot.captured)
        assertEquals(ExecutionStatus.fromDto(dto.status), statusSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowExecutionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = WorkflowExecutionDto(
            id = 0L,
            workflow = mockk(),
            user = mockk(),
            startedAt = LocalDateTime.parse("2023-10-01T14:00:00"),
            completedAt = LocalDateTime.parse("2023-10-01T15:00:00"),
            status = mockk(),
            stepExecutions = emptyList()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<WorkflowExecutionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowDao: WorkflowDao = mockk()
        val userDao: UserDao = mockk()

        // Mock the behavior of dependency mappers
        every { workflowsMapper.toEntity(any()) } returns workflowDao
        every { usersMapper.toEntity(any()) } returns userDao

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowSlot = slot<WorkflowDao>()
        val userSlot = slot<UserDao>()
        val startedAtSlot = slot<LocalDateTime>()
        val completedAtSlot = slot<LocalDateTime>()
        val statusSlot = slot<ExecutionStatus>()

        every { newEntity.workflow = capture(workflowSlot) } just runs
        every { newEntity.user = capture(userSlot) } just runs
        every { newEntity.startedAt = capture(startedAtSlot) } just runs
        every { newEntity.completedAt = capture(completedAtSlot) } just runs
        every { newEntity.status = capture(statusSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflow = workflowDao }
        verify { newEntity.user = userDao }
        verify { newEntity.startedAt = dto.startedAt }
        verify { newEntity.completedAt = dto.completedAt }
        verify { newEntity.status = ExecutionStatus.fromDto(dto.status) }

        // Verify the captured values
        assertEquals(workflowDao, workflowSlot.captured)
        assertEquals(userDao, userSlot.captured)
        assertEquals(dto.startedAt, startedAtSlot.captured)
        assertEquals(dto.completedAt, completedAtSlot.captured)
        assertEquals(ExecutionStatus.fromDto(dto.status), statusSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}