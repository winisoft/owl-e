package com.wini.owle.mapper.runs

import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.ExecutionStatus
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DaoFactory
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
    private val factory: DaoFactory<StepExecutionDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = StepExecutionsMapper(factory, workflowExecutionsMapper, stepMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = StepExecutionDto(
            id = 99L,
            workflowExecution = mockk(),
            step = mockk(),
            startedAt = "2023-10-01T10:00:00",
            completedAt = "2023-10-01T11:00:00",
            status = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(StepExecutionDao)
        every { StepExecutionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("StepExecution with id=99 not found", exception.message)
        unmockkObject(StepExecutionDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<StepExecutionDao> {
            every { id.value } returns 1L
            every { workflowExecution } returns mockk()
            every { step } returns mockk()
            every { startedAt } returns "2023-10-01T10:00:00"
            every { completedAt } returns "2023-10-01T11:00:00"
            every { status.toDto() } returns mockk()
        }

        // Mock the behavior of workflowExecutionsMapper and stepMapper
        val workflowExecutionDto = mockk<WorkflowExecutionDto>()
        val stepDto = mockk<StepDto>()
        every { workflowExecutionsMapper.toDto(entity.workflowExecution) } returns workflowExecutionDto
        every { stepMapper.toDto(entity.step) } returns stepDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowExecutionDto, result.workflowExecution)
        assertEquals(stepDto, result.step)
        assertEquals("2023-10-01T10:00:00", result.startedAt)
        assertEquals("2023-10-01T11:00:00", result.completedAt)
        assertEquals(entity.status.toDto(), result.status)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = StepExecutionDto(
            id = 42L,
            workflowExecution = mockk(),
            step = mockk(),
            startedAt = "2023-10-01T12:00:00",
            completedAt = "2023-10-01T13:00:00",
            status = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<StepExecutionDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(StepExecutionDao)
        every { StepExecutionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowExecutionSlot = slot<WorkflowExecutions>()
        val stepSlot = slot<StepDao>()
        val startedAtSlot = slot<String>()
        val completedAtSlot = slot<String>()
        val statusSlot = slot<ExecutionStatus>()

        every { existingEntity.workflowExecution = capture(workflowExecutionSlot) } just runs
        every { existingEntity.step = capture(stepSlot) } just runs
        every { existingEntity.startedAt = capture(startedAtSlot) } just runs
        every { existingEntity.completedAt = capture(completedAtSlot) } just runs
        every { existingEntity.status = capture(statusSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { StepExecutionDao.findById(42L) }
        verify { existingEntity.workflowExecution = dto.workflowExecution }
        verify { existingEntity.step = dto.step }
        verify { existingEntity.startedAt = dto.startedAt }
        verify { existingEntity.completedAt = dto.completedAt }
        verify { existingEntity.status = ExecutionStatus.fromDto(dto.status) }

        // Verify the captured values
        assertEquals(dto.workflowExecution, workflowExecutionSlot.captured)
        assertEquals(dto.step, stepSlot.captured)
        assertEquals(dto.startedAt, startedAtSlot.captured)
        assertEquals(dto.completedAt, completedAtSlot.captured)
        assertEquals(ExecutionStatus.fromDto(dto.status), statusSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(StepExecutionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = StepExecutionDto(
            id = 0L,
            workflowExecution = mockk(),
            step = mockk(),
            startedAt = "2023-10-01T14:00:00",
            completedAt = "2023-10-01T15:00:00",
            status = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<StepExecutionDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowExecutionSlot = slot<WorkflowExecutions>()
        val stepSlot = slot<StepDao>()
        val startedAtSlot = slot<String>()
        val completedAtSlot = slot<String>()
        val statusSlot = slot<ExecutionStatus>()

        every { newEntity.workflowExecution = capture(workflowExecutionSlot) } just runs
        every { newEntity.step = capture(stepSlot) } just runs
        every { newEntity.startedAt = capture(startedAtSlot) } just runs
        every { newEntity.completedAt = capture(completedAtSlot) } just runs
        every { newEntity.status = capture(statusSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflowExecution = dto.workflowExecution }
        verify { newEntity.step = dto.step }
        verify { newEntity.startedAt = dto.startedAt }
        verify { newEntity.completedAt = dto.completedAt }
        verify { newEntity.status = ExecutionStatus.fromDto(dto.status) }

        // Verify the captured values
        assertEquals(dto.workflowExecution, workflowExecutionSlot.captured)
        assertEquals(dto.step, stepSlot.captured)
        assertEquals(dto.startedAt, startedAtSlot.captured)
        assertEquals(dto.completedAt, completedAtSlot.captured)
        assertEquals(ExecutionStatus.fromDto(dto.status), statusSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}