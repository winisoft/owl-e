package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.workflows.Step
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows

open class ErrorWatchEntityMapperTest {

    private lateinit var mapper: ErrorWatchesMapper
    private val stepsMapper: StepsMapper = mockk()
    private val factory: DaoFactory<ErrorWatchEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ErrorWatchesMapper(factory, stepsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ErrorWatch(id = 99L, step = mockk(), errorType = "testError", triggerStep = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(ErrorWatchEntity)
        every { ErrorWatchEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ErrorWatch with id=99 not found", exception.message)
        unmockkObject(ErrorWatchEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ErrorWatchEntity> {
            every { id.value } returns 1L
            every { step } returns mockk()
            every { errorType } returns "testError"
            every { onMetStep } returns mockk()
        }

        // Mock the behavior of stepsMapper
        val step = mockk<Step>()
        val triggerStep = mockk<Step>()
        every { stepsMapper.toDto(entity.step) } returns step
        every { stepsMapper.toDto(entity.onMetStep) } returns triggerStep

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(step, result.step)
        assertEquals("testError", result.errorType)
        assertEquals(triggerStep, result.triggerStep)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ErrorWatch(
            id = 42L,
            step = mockk(),
            errorType = "updatedError",
            triggerStep = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ErrorWatchEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val onMetStepEntity: StepEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(ErrorWatchEntity)
        every { ErrorWatchEntity.findById(42L) } returns existingEntity
        every { stepsMapper.toEntity(any()) }.returnsMany(stepEntity, onMetStepEntity)

        // Capture the property updates
        val stepSlot = slot<StepEntity>()
        val errorTypeSlot = slot<String>()
        val triggerStepSlot = slot<StepEntity>()

        every { existingEntity.step = capture(stepSlot) } just runs
        every { existingEntity.errorType = capture(errorTypeSlot) } just runs
        every { existingEntity.onMetStep = capture(triggerStepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ErrorWatchEntity.findById(42L) }
        verify { existingEntity.step = stepEntity }
        verify { existingEntity.errorType = dto.errorType }
        verify { existingEntity.onMetStep = onMetStepEntity }

        // Verify the captured values
        assertEquals(stepEntity, stepSlot.captured)
        assertEquals(dto.errorType, errorTypeSlot.captured)
        assertEquals(onMetStepEntity, triggerStepSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ErrorWatchEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ErrorWatch(
            id = 0L,
            step = mockk(),
            errorType = "newError",
            triggerStep = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ErrorWatchEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val onMetStepEntity: StepEntity = mockk()
        every { stepsMapper.toEntity(any()) }.returnsMany(stepEntity, onMetStepEntity)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stepSlot = slot<StepEntity>()
        val errorTypeSlot = slot<String>()
        val triggerStepSlot = slot<StepEntity>()

        every { newEntity.step = capture(stepSlot) } just runs
        every { newEntity.errorType = capture(errorTypeSlot) } just runs
        every { newEntity.onMetStep = capture(triggerStepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.step = stepEntity }
        verify { newEntity.errorType = dto.errorType }
        verify { newEntity.onMetStep = onMetStepEntity }

        // Verify the captured values
        assertEquals(stepEntity, stepSlot.captured)
        assertEquals(dto.errorType, errorTypeSlot.captured)
        assertEquals(onMetStepEntity, triggerStepSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}