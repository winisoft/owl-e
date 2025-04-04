package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows

open class ErrorWatchMapperTest {

    private lateinit var mapper: ErrorWatchesMapper
    private val stepsMapper: StepsMapper = mockk()
    private val factory: DaoFactory<ErrorWatchDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ErrorWatchesMapper(factory, stepsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ErrorWatchDto(id = 99L, step = mockk(), errorType = "testError", triggerStep = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(ErrorWatchDao)
        every { ErrorWatchDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ErrorWatch with id=99 not found", exception.message)
        unmockkObject(ErrorWatchDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ErrorWatchDao> {
            every { id.value } returns 1L
            every { step } returns mockk()
            every { errorType } returns "testError"
            every { onMetStep } returns mockk()
        }

        // Mock the behavior of stepsMapper
        val stepDto = mockk<StepDto>()
        val triggerStepDto = mockk<StepDto>()
        every { stepsMapper.toDto(entity.step) } returns stepDto
        every { stepsMapper.toDto(entity.onMetStep) } returns triggerStepDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(stepDto, result.step)
        assertEquals("testError", result.errorType)
        assertEquals(triggerStepDto, result.triggerStep)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ErrorWatchDto(
            id = 42L,
            step = mockk(),
            errorType = "updatedError",
            triggerStep = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ErrorWatchDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepDao: StepDao = mockk()
        val onMetStepDao: StepDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(ErrorWatchDao)
        every { ErrorWatchDao.findById(42L) } returns existingEntity
        every { stepsMapper.toEntity(any()) }.returnsMany(stepDao, onMetStepDao)

        // Capture the property updates
        val stepSlot = slot<StepDao>()
        val errorTypeSlot = slot<String>()
        val triggerStepSlot = slot<StepDao>()

        every { existingEntity.step = capture(stepSlot) } just runs
        every { existingEntity.errorType = capture(errorTypeSlot) } just runs
        every { existingEntity.onMetStep = capture(triggerStepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ErrorWatchDao.findById(42L) }
        verify { existingEntity.step = stepDao }
        verify { existingEntity.errorType = dto.errorType }
        verify { existingEntity.onMetStep = onMetStepDao }

        // Verify the captured values
        assertEquals(stepDao, stepSlot.captured)
        assertEquals(dto.errorType, errorTypeSlot.captured)
        assertEquals(onMetStepDao, triggerStepSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ErrorWatchDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ErrorWatchDto(
            id = 0L,
            step = mockk(),
            errorType = "newError",
            triggerStep = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ErrorWatchDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepDao: StepDao = mockk()
        val onMetStepDao: StepDao = mockk()
        every { stepsMapper.toEntity(any()) }.returnsMany(stepDao, onMetStepDao)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stepSlot = slot<StepDao>()
        val errorTypeSlot = slot<String>()
        val triggerStepSlot = slot<StepDao>()

        every { newEntity.step = capture(stepSlot) } just runs
        every { newEntity.errorType = capture(errorTypeSlot) } just runs
        every { newEntity.onMetStep = capture(triggerStepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.step = stepDao }
        verify { newEntity.errorType = dto.errorType }
        verify { newEntity.onMetStep = onMetStepDao }

        // Verify the captured values
        assertEquals(stepDao, stepSlot.captured)
        assertEquals(dto.errorType, errorTypeSlot.captured)
        assertEquals(onMetStepDao, triggerStepSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}