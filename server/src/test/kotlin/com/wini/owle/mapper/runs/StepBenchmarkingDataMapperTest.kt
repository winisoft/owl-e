package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.StepBenchmarkingDataDto
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.entity.runs.StepBenchmarkingDataDao
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import kotlin.test.assertEquals


open class StepBenchmarkingDataMapperTest {

    private lateinit var mapper: StepBenchmarkingDataMapper
    private val stepExecutionsMapper: StepExecutionsMapper = mockk()
    private val factory: DaoFactory<StepBenchmarkingDataDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = StepBenchmarkingDataMapper(factory, stepExecutionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = StepBenchmarkingDataDto(
            id = 99L,
            stepExecution = mockk(),
            executionTime = 100.123
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(StepBenchmarkingDataDao)
        every { StepBenchmarkingDataDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("StepBenchmarkingData with id=99 not found", exception.message)
        unmockkObject(StepBenchmarkingDataDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<StepBenchmarkingDataDao> {
            every { id.value } returns 1L
            every { stepExecution } returns mockk()
            every { executionTime.toDouble() } returns 100.123
        }

        // Mock the behavior of stepExecutionsMapper
        val stepExecutionDto = mockk<StepExecutionDto>()
        every { stepExecutionsMapper.toDto(entity.stepExecution) } returns stepExecutionDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(stepExecutionDto, result.stepExecution)
        assertEquals(100.123, result.executionTime)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = StepBenchmarkingDataDto(
            id = 42L,
            stepExecution = mockk(),
            executionTime = 200.234
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<StepBenchmarkingDataDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepExecutionDao: StepExecutionDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(StepBenchmarkingDataDao)
        every { StepBenchmarkingDataDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val stepExecutionSlot = slot<StepExecutionDao>()
        val executionTimeSlot = slot<BigDecimal>()

        every { existingEntity.stepExecution = capture(stepExecutionSlot) } just runs
        every { existingEntity.executionTime = capture(executionTimeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { StepBenchmarkingDataDao.findById(42L) }
        verify { existingEntity.stepExecution = stepExecutionDao }
        verify { existingEntity.executionTime = dto.executionTime.toBigDecimal() }

        // Verify the captured values
        assertEquals(stepExecutionDao, stepExecutionSlot.captured)
        assertEquals(dto.executionTime.toBigDecimal(), executionTimeSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(StepBenchmarkingDataDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = StepBenchmarkingDataDto(
            id = 0L,
            stepExecution = mockk(),
            executionTime = 300.345
        )

        // Mock the new entity that will be created
        val newEntity = mockk<StepBenchmarkingDataDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepExecutionDao: StepExecutionDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stepExecutionSlot = slot<StepExecutionDao>()
        val executionTimeSlot = slot<BigDecimal>()

        every { newEntity.stepExecution = capture(stepExecutionSlot) } just runs
        every { newEntity.executionTime = capture(executionTimeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.stepExecution = stepExecutionDao }
        verify { newEntity.executionTime = dto.executionTime.toBigDecimal() }

        // Verify the captured values
        assertEquals(stepExecutionDao, stepExecutionSlot.captured)
        assertEquals(dto.executionTime.toBigDecimal(), executionTimeSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}