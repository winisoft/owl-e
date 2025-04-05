package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ExecutionStatusDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.enums.ExecutionStatus
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.runs.enums.ExecutionStatusMapper
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows


open class StepExecutionMapperTest {

    private lateinit var mapper: StepExecutionsMapper
    private val workflowExecutionsMapper: WorkflowExecutionsMapper = mockk()
    private val stepMapper: StepsMapper = mockk()
    private val factory: DaoFactory<StepExecutionDao, Long> = mockk()
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
        val workflowExecutionDto = mockk<WorkflowExecutionDto>()
        val stepDto = mockk<StepDto>()
        val dto = StepExecutionDto(
            id = 99L,
            workflowExecution = workflowExecutionDto,
            step = stepDto,
            startedAt =  "2023-10-01T10:00:00",
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
        val entity = mockk<StepExecutionDao> {
            every { id.value } returns 1L
            every { workflowExecution } returns mockk()
            every { step } returns mockk()
            every { startedAt } returns mockk()
            every { completedAt } returns mockk()
            every { status } returns mockk()
        }

        every { executionStatusMapper.toDto(any()) } returns ExecutionStatusDto.Running

        // Mock the behavior of workflowExecutionsMapper and stepMapper
        val workflowExecutionDto = mockk<WorkflowExecutionDto>()
        val stepDto = mockk<StepDto>()
        val mockStatus = mockk<ExecutionStatusDto>()
        val startedAtStr = "2025-04-04T12:34:56Z"
        val completedAtStr = "2025-05-04T12:34:56Z"

        every { workflowExecutionsMapper.toDto(entity.workflowExecution) } returns workflowExecutionDto
        every { stepMapper.toDto(entity.step) } returns stepDto
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
        assertEquals(workflowExecutionDto, result.workflowExecution)
        assertEquals(startedAtStr, result.startedAt)
        assertEquals(completedAtStr, result.completedAt)
        assertEquals(mockStatus, result.status)

    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create mock DTOs and DAOs
        val workflowExecutionDto = mockk<WorkflowExecutionDto>()
        val stepDto = mockk<StepDto>()
        val statusDto = ExecutionStatusDto.Running
        
        val dto = StepExecutionDto(
            id = 42L,
            workflowExecution = workflowExecutionDto,
            step = stepDto,
            startedAt = "2023-10-01T10:00:00",
            completedAt = "2023-10-01T11:00:00",
            status = statusDto
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<StepExecutionDao>(relaxed = true)

        // Create the mocked DAOs that will be returned by the mappers
        val workflowExecutionDao = mockk<WorkflowExecutionDao>()
        val stepDao = mockk<StepDao>()
        val status = ExecutionStatus.RUNNING

        // Mock the dependency mappers
        every { workflowExecutionsMapper.toEntity(workflowExecutionDto) } returns workflowExecutionDao
        every { stepMapper.toEntity(stepDto) } returns stepDao
        every { executionStatusMapper.toEntity(any()) } returns status
        every { dateTimeConverter.fromString(any()) } returns mockk()

        // Set up mocks for finding existing entity
        mockkObject(StepExecutionDao)
        every { StepExecutionDao.findById(42L) } returns existingEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { StepExecutionDao.findById(42L) }
        verify { existingEntity.workflowExecution = workflowExecutionDao }
        verify { existingEntity.step = stepDao }
        verify { existingEntity.status = status }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(StepExecutionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create mock DTOs and DAOs
        val workflowExecutionDto = mockk<WorkflowExecutionDto>()
        val stepDto = mockk<StepDto>()
        val statusDto = ExecutionStatusDto.Completed
        
        val dto = StepExecutionDto(
            id = 0L,
            workflowExecution = workflowExecutionDto,
            step = stepDto,
            startedAt = "2023-10-01T12:00:00",
            completedAt = "2023-10-01T13:00:00",
            status = statusDto
        )

        // Mock the new entity that will be created
        val newEntity = mockk<StepExecutionDao>(relaxed = true)

        // Create the mocked DAOs that will be returned by the mappers
        val workflowExecutionDao = mockk<WorkflowExecutionDao>()
        val stepDao = mockk<StepDao>()
        val status = ExecutionStatus.COMPLETED

        // Mock the dependency mappers
        every { workflowExecutionsMapper.toEntity(workflowExecutionDto) } returns workflowExecutionDao
        every { stepMapper.toEntity(stepDto) } returns stepDao
        every { executionStatusMapper.toEntity(any()) } returns status
        every { dateTimeConverter.fromString(any()) } returns mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflowExecution = workflowExecutionDao }
        verify { newEntity.step = stepDao }
        verify { newEntity.status = status }

        // Verify the result is the new entity
        assertEquals(newEntity, result)

    }
}