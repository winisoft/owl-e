package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.entity.runs.enums.ExecutionStatusEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.WorkflowEntity
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
    private val factory: DaoFactory<WorkflowRunEntity, Long> = mockk()
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
        val dto = WorkflowExecution(
            id = 99L,
            workflow = mockk(),
            user = mockk(),
            startedAt = "2023-10-01T10:00:00",
            completedAt = "2023-10-01T11:00:00",
            status = mockk(),
            stepRuns = emptyList()
        )

        // Mock behavior for findById to return null (not found)
        mockkObject(WorkflowRunEntity)
        every { WorkflowRunEntity.findById(99L) } returns null

        // Verify exception is thrown with correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("WorkflowExecution with id=99 not found", exception.message)
        unmockkObject(WorkflowRunEntity)
    }

    @Test
    fun `toDto should correctly delegate to collaborators`() {
        // Setup mocks
        val entity = mockk<WorkflowRunEntity> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { userEntity } returns mockk()
            every { startedAt } returns mockk()
            every { completedAt } returns mockk()
            every { status } returns mockk()
        }

        // Setup mock responses from collaborators
        val workflow = mockk<Workflow>()
        val user = mockk<User>()
        val startedAtStr = "2025-04-04T12:34:56Z"
        val completedAtStr = "2025-05-04T12:34:56Z"
        val statusDto = ExecutionStatus.Failed
        
        every { workflowsMapper.toDto(entity.workflow) } returns workflow
        every { usersMapper.toDto(entity.userEntity) } returns user
        every { dateTimeConverter.toString(entity.startedAt) } returns startedAtStr
        every { dateTimeConverter.toString(entity.completedAt!!) } returns completedAtStr
        every { executionStatusMapper.toDto(entity.status) } returns statusDto

        // Call method under test
        val result = mapper.toDto(entity)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowsMapper.toDto(entity.workflow) }
        verify(exactly = 1) { usersMapper.toDto(entity.userEntity) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.startedAt) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.completedAt!!) }
        verify(exactly = 1) { executionStatusMapper.toDto(entity.status) }

        // Verify result contains values from collaborators
        assertEquals(workflow, result.workflow)
        assertEquals(user, result.user)
        assertEquals(startedAtStr, result.startedAt)
        assertEquals(completedAtStr, result.completedAt)
        assertEquals(statusDto, result.status)
        
        // Verify direct property mappings
        assertEquals(entity.id.value, result.id)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTO
        val dto = WorkflowExecution(
            id = 42L,
            workflow = mockk(),
            user = mockk(),
            startedAt = "2025-04-04T12:34:56Z",
            completedAt = "2025-05-04T12:34:56Z",
            status = ExecutionStatus.Failed,
            stepRuns = emptyList()
        )

        // Create mock existing entity to update
        val existingEntity = mockk<WorkflowRunEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val workflowEntity = mockk<WorkflowEntity>()
        val userEntity = mockk<UserEntity>()
        val startedAt = mockk<Instant>(name = "startedAt")
        val completedAt = mockk<Instant>(name = "completedAt")
        val status = ExecutionStatusEntity.FAILED
        
        every { workflowsMapper.toEntity(dto.workflow) } returns workflowEntity
        every { usersMapper.toEntity(dto.user) } returns userEntity
        every { dateTimeConverter.fromString(dto.startedAt) } returns startedAt
        every { dateTimeConverter.fromString(dto.completedAt!!) } returns completedAt
        every { executionStatusMapper.toEntity(dto.status) } returns status

        // Mock finding existing entity
        mockkObject(WorkflowRunEntity)
        every { WorkflowRunEntity.findById(42L) } returns existingEntity

        // Execute method under test
        val result = mapper.toEntity(dto)

        // Verify collaborators were called with correct arguments
        verify(exactly = 1) { workflowsMapper.toEntity(dto.workflow) }
        verify(exactly = 1) { usersMapper.toEntity(dto.user) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.startedAt) }
        verify(exactly = 1) { dateTimeConverter.fromString(dto.completedAt!!) }
        verify(exactly = 1) { executionStatusMapper.toEntity(dto.status) }

        // Verify entity fields were set with values from collaborators
        verify { existingEntity.workflow = workflowEntity }
        verify { existingEntity.userEntity = userEntity }
        verify { existingEntity.startedAt = startedAt }
        verify { existingEntity.completedAt = completedAt }
        verify { existingEntity.status = status }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowRunEntity)
    }

    @Test
    fun `toEntity should create a new entity when id is zero`() {
        // Create test DTO
        val dto = WorkflowExecution(
            id = 0L,
            workflow = mockk(),
            user = mockk(),
            startedAt = "2025-04-04T12:34:56Z",
            completedAt = "2025-05-04T12:34:56Z",
            status = ExecutionStatus.Failed,
            stepRuns = emptyList()
        )

        // Create mock new entity
        val newEntity = mockk<WorkflowRunEntity>(relaxed = true)

        // Setup mock responses from collaborators
        val workflowEntity = mockk<WorkflowEntity>()
        val userEntity = mockk<UserEntity>()
        val startedAt = mockk<Instant>(name = "startedAt")
        val completedAt = mockk<Instant>(name = "completedAt")
        val status = ExecutionStatusEntity.FAILED
        
        every { workflowsMapper.toEntity(dto.workflow) } returns workflowEntity
        every { usersMapper.toEntity(dto.user) } returns userEntity
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
        verify { newEntity.workflow = workflowEntity }
        verify { newEntity.userEntity = userEntity }
        verify { newEntity.startedAt = startedAt }
        verify { newEntity.completedAt = completedAt }
        verify { newEntity.status = status }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}