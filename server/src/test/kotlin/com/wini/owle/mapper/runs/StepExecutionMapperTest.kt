package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.runs.step.StepRun
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.entity.runs.enums.ExecutionStatusEntity
import com.wini.owle.entity.runs.step.StepRunEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.runs.enums.ExecutionStatusMapper
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class StepExecutionMapperTest {

    private lateinit var mapper: StepExecutionsMapper
    private val workflowExecutionsMapper: WorkflowExecutionsMapper = mockk()
    private val stepMapper: StepsMapper = mockk()
    private val factory: DaoFactory<StepRunEntity, Long> = mockk()
    private val executionStatusMapper: ExecutionStatusMapper = mockk()
    private val dateTimeConverter: DateTimeConverter = mockk()

    @BeforeEach
    fun setUp() {
        mapper = StepExecutionsMapper(
            factory,
            workflowExecutionsMapper,
            stepMapper,
            executionStatusMapper,
            dateTimeConverter
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val workflowExecution = mockk<WorkflowExecution>()
        val step = mockk<Step>()
        val dto = StepRun(
            id = 99L,
            workflowExecution = workflowExecution,
            step = step,
            startedAt =  "2023-10-01T10:00:00",
            completedAt = "2023-10-01T11:00:00",
            status = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(StepRunEntity)
        every { StepRunEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("StepExecution with id=99 not found", exception.message)
        unmockkObject(StepRunEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        val entity = mockk<StepRunEntity> {
            every { id.value } returns 1L
            every { workflowExecution } returns mockk()
            every { step } returns mockk()
            every { startedAt } returns mockk()
            every { completedAt } returns mockk()
            every { status } returns mockk()
        }

        every { executionStatusMapper.toDto(any()) } returns ExecutionStatus.Running

        // Mock the behavior of workflowExecutionsMapper and stepMapper
        val workflowExecution = mockk<WorkflowExecution>()
        val step = mockk<Step>()
        val mockStatus = mockk<ExecutionStatus>()
        val startedAtStr = "2025-04-04T12:34:56Z"
        val completedAtStr = "2025-05-04T12:34:56Z"

        every { workflowExecutionsMapper.toDto(entity.workflowExecution) } returns workflowExecution
        every { stepMapper.toDto(entity.step) } returns step
        every { dateTimeConverter.toString(entity.startedAt) } returns startedAtStr
        every { dateTimeConverter.toString(entity.completedAt!!) } returns completedAtStr
        every { executionStatusMapper.toDto(entity.status) } returns mockStatus

        // Call the method under test
        val result = mapper.toDto(entity)

        verify(exactly = 1) { workflowExecutionsMapper.toDto(entity.workflowExecution) }
        verify(exactly = 1) { stepMapper.toDto(entity.step) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.startedAt) }
        verify(exactly = 1) { dateTimeConverter.toString(entity.completedAt!!) }
        verify(exactly = 1) { executionStatusMapper.toDto(entity.status) }

        // Verify result contains values from collaborators
        assertEquals(1L, result.id)
        assertEquals(workflowExecution, result.workflowExecution)
        assertEquals(startedAtStr, result.startedAt)
        assertEquals(completedAtStr, result.completedAt)
        assertEquals(mockStatus, result.status)

    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create mock DTOs and DAOs
        val workflowExecution = mockk<WorkflowExecution>()
        val step = mockk<Step>()
        val statusDto = ExecutionStatus.Running
        
        val dto = StepRun(
            id = 42L,
            workflowExecution = workflowExecution,
            step = step,
            startedAt = "2023-10-01T10:00:00",
            completedAt = "2023-10-01T11:00:00",
            status = statusDto
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<StepRunEntity>(relaxed = true)

        // Create the mocked DAOs that will be returned by the mappers
        val workflowRunEntity = mockk<WorkflowRunEntity>()
        val stepEntity = mockk<StepEntity>()
        val status = ExecutionStatusEntity.RUNNING

        // Mock the dependency mappers
        every { workflowExecutionsMapper.toEntity(workflowExecution) } returns workflowRunEntity
        every { stepMapper.toEntity(step) } returns stepEntity
        every { executionStatusMapper.toEntity(any()) } returns status
        every { dateTimeConverter.fromString(any()) } returns mockk()

        // Set up mocks for finding existing entity
        mockkObject(StepRunEntity)
        every { StepRunEntity.findById(42L) } returns existingEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { StepRunEntity.findById(42L) }
        verify { existingEntity.workflowExecution = workflowRunEntity }
        verify { existingEntity.step = stepEntity }
        verify { existingEntity.status = status }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(StepRunEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create mock DTOs and DAOs
        val workflowExecution = mockk<WorkflowExecution>()
        val step = mockk<Step>()
        val statusDto = ExecutionStatus.Completed
        
        val dto = StepRun(
            id = 0L,
            workflowExecution = workflowExecution,
            step = step,
            startedAt = "2023-10-01T12:00:00",
            completedAt = "2023-10-01T13:00:00",
            status = statusDto
        )

        // Mock the new entity that will be created
        val newEntity = mockk<StepRunEntity>(relaxed = true)

        // Create the mocked DAOs that will be returned by the mappers
        val workflowRunEntity = mockk<WorkflowRunEntity>()
        val stepEntity = mockk<StepEntity>()
        val status = ExecutionStatusEntity.COMPLETED

        // Mock the dependency mappers
        every { workflowExecutionsMapper.toEntity(workflowExecution) } returns workflowRunEntity
        every { stepMapper.toEntity(step) } returns stepEntity
        every { executionStatusMapper.toEntity(any()) } returns status
        every { dateTimeConverter.fromString(any()) } returns mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflowExecution = workflowRunEntity }
        verify { newEntity.step = stepEntity }
        verify { newEntity.status = status }

        // Verify the result is the new entity
        assertEquals(newEntity, result)

    }
}