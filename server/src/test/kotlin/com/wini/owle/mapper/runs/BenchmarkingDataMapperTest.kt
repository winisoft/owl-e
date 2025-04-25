package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal


open class BenchmarkingDataMapperTest {

    private lateinit var mapper: BenchmarkingDataMapper
    private val workflowExecutionsMapper: WorkflowExecutionsMapper = mockk()
    private val factory: DaoFactory<BenchmarkReportEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = BenchmarkingDataMapper(factory, workflowExecutionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val workflowExecution = mockk<WorkflowExecution>()
        val dto = BenchmarkReport(
            id = 99L,
            workflowExecution = workflowExecution,
            totalExecutionTime = 100.012,
            totalSteps = 10,
            aiDeterminedSteps = 5,
            predeterminedSteps = 5
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(BenchmarkReportEntity)
        every { BenchmarkReportEntity.findById(99L) } returns null
        
        // Mock the dependency mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()
        every { workflowExecutionsMapper.toEntity(any()) } returns workflowRunEntity

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("BenchmarkingData with id=99 not found", exception.message)
        unmockkObject(BenchmarkReportEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<BenchmarkReportEntity> {
            every { id.value } returns 1L
            every { workflowExecution } returns mockk()
            every { totalExecutionTime } returns 123.456.toBigDecimal()
            every { totalSteps } returns 10
            every { aiDeterminedSteps } returns 5
            every { predeterminedSteps } returns 5
        }

        // Mock the behavior of workflowExecutionsMapper
        val workflowExecution = mockk<WorkflowExecution>()
        every { workflowExecutionsMapper.toDto(entity.workflowExecution) } returns workflowExecution

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowExecution, result.workflowExecution)
        assertEquals(123.456, result.totalExecutionTime)
        assertEquals(10, result.totalSteps)
        assertEquals(5, result.aiDeterminedSteps)
        assertEquals(5, result.predeterminedSteps)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val workflowExecution = mockk<WorkflowExecution>()
        val dto = BenchmarkReport(
            id = 42L,
            workflowExecution = workflowExecution,
            totalExecutionTime = 200.456,
            totalSteps = 20,
            aiDeterminedSteps = 10,
            predeterminedSteps = 10
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<BenchmarkReportEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(BenchmarkReportEntity)
        every { BenchmarkReportEntity.findById(42L) } returns existingEntity
        
        // Set up the workflowExecutionsMapper to return the mocked workflowExecutionDao
        every { workflowExecutionsMapper.toEntity(workflowExecution) } returns workflowRunEntity

        // Capture the property updates
        val workflowExecutionSlot = slot<WorkflowRunEntity>()
        val totalExecutionTimeSlot = slot<BigDecimal>()
        val totalStepsSlot = slot<Int>()
        val aiDeterminedStepsSlot = slot<Int>()
        val predeterminedStepsSlot = slot<Int>()

        every { existingEntity.workflowExecution = capture(workflowExecutionSlot) } just runs
        every { existingEntity.totalExecutionTime = capture(totalExecutionTimeSlot) } just runs
        every { existingEntity.totalSteps = capture(totalStepsSlot) } just runs
        every { existingEntity.aiDeterminedSteps = capture(aiDeterminedStepsSlot) } just runs
        every { existingEntity.predeterminedSteps = capture(predeterminedStepsSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { BenchmarkReportEntity.findById(42L) }
        verify { existingEntity.workflowExecution = workflowRunEntity }
        verify { existingEntity.totalExecutionTime = dto.totalExecutionTime.toBigDecimal() }
        verify { existingEntity.totalSteps = dto.totalSteps }
        verify { existingEntity.aiDeterminedSteps = dto.aiDeterminedSteps }
        verify { existingEntity.predeterminedSteps = dto.predeterminedSteps }

        // Verify the captured values
        assertEquals(workflowRunEntity, workflowExecutionSlot.captured)
        assertEquals(dto.totalExecutionTime.toBigDecimal(), totalExecutionTimeSlot.captured)
        assertEquals(dto.totalSteps, totalStepsSlot.captured)
        assertEquals(dto.aiDeterminedSteps, aiDeterminedStepsSlot.captured)
        assertEquals(dto.predeterminedSteps, predeterminedStepsSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(BenchmarkReportEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val workflowExecution = mockk<WorkflowExecution>()
        val dto = BenchmarkReport(
            id = 0L,
            workflowExecution = workflowExecution,
            totalExecutionTime = 300.987,
            totalSteps = 30,
            aiDeterminedSteps = 15,
            predeterminedSteps = 15
        )

        // Mock the new entity that will be created
        val newEntity = mockk<BenchmarkReportEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity
        
        // Set up the workflowExecutionsMapper to return the mocked workflowExecutionDao
        every { workflowExecutionsMapper.toEntity(workflowExecution) } returns workflowRunEntity

        // Capture property assignments
        val workflowExecutionSlot = slot<WorkflowRunEntity>()
        val totalExecutionTimeSlot = slot<BigDecimal>()
        val totalStepsSlot = slot<Int>()
        val aiDeterminedStepsSlot = slot<Int>()
        val predeterminedStepsSlot = slot<Int>()

        every { newEntity.workflowExecution = capture(workflowExecutionSlot) } just runs
        every { newEntity.totalExecutionTime = capture(totalExecutionTimeSlot) } just runs
        every { newEntity.totalSteps = capture(totalStepsSlot) } just runs
        every { newEntity.aiDeterminedSteps = capture(aiDeterminedStepsSlot) } just runs
        every { newEntity.predeterminedSteps = capture(predeterminedStepsSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflowExecution = workflowRunEntity }
        verify { newEntity.totalExecutionTime = dto.totalExecutionTime.toBigDecimal() }
        verify { newEntity.totalSteps = dto.totalSteps }
        verify { newEntity.aiDeterminedSteps = dto.aiDeterminedSteps }
        verify { newEntity.predeterminedSteps = dto.predeterminedSteps }

        // Verify the captured values
        assertEquals(workflowRunEntity, workflowExecutionSlot.captured)
        assertEquals(dto.totalExecutionTime.toBigDecimal(), totalExecutionTimeSlot.captured)
        assertEquals(dto.totalSteps, totalStepsSlot.captured)
        assertEquals(dto.aiDeterminedSteps, aiDeterminedStepsSlot.captured)
        assertEquals(dto.predeterminedSteps, predeterminedStepsSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}