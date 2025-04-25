package com.wini.owle.mapper.steps

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.workflows.Step
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class InquisitionEntityMapperTest {

    private lateinit var mapper: InquisitionsMapper
    private val stepsMapper: StepsMapper = mockk()
    private val dataPointsMapper: DataPointsMapper = mockk()
    private val factory: DaoFactory<InquisitionEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = InquisitionsMapper(factory, stepsMapper, dataPointsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = Inquisition(
            id = 99L,
            step = mockk(),
            className = "testClass",
            dataPoint = mockk(),
            onMetWorkflowStep = mockk(),
            executionOrder = 1
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(InquisitionEntity)
        every { InquisitionEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Inquisition with id=99 not found", exception.message)
        unmockkObject(InquisitionEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<InquisitionEntity> {
            every { id.value } returns 1L
            every { workflowStep } returns mockk()
            every { className } returns "testClass"
            every { dataPointEntity } returns mockk()
            every { onMetWorkflowStep } returns mockk()
            every { executionOrder } returns 1
        }

        // Mock the behavior of stepsMapper and dataPointsMapper
        val step = mockk<Step>()
        val dataPoint = mockk<DataPoint>()
        val onMetWorkflowStep = mockk<Step>()
        every { stepsMapper.toDto(entity.workflowStep) } returns step
        every { dataPointsMapper.toDto(entity.dataPointEntity) } returns dataPoint
        every { stepsMapper.toDto(entity.onMetWorkflowStep) } returns onMetWorkflowStep

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(step, result.step)
        assertEquals("testClass", result.className)
        assertEquals(dataPoint, result.dataPoint)
        assertEquals(onMetWorkflowStep, result.onMetWorkflowStep)
        assertEquals(1, result.executionOrder)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = Inquisition(
            id = 42L,
            step = mockk(),
            className = "updatedClass",
            dataPoint = mockk(),
            onMetWorkflowStep = mockk(),
            executionOrder = 2
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<InquisitionEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val dataPointEntity: DataPointEntity = mockk()
        val onMetWorkflowStepEntity: StepEntity = mockk()
        every { stepsMapper.toEntity(any<Step>()) }.returnsMany(stepEntity, onMetWorkflowStepEntity)
        every { dataPointsMapper.toEntity(any<DataPoint>()) } returns dataPointEntity

        // Set up mocks for finding existing entity
        mockkObject(InquisitionEntity)
        every { InquisitionEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowStepSlot = slot<StepEntity>()
        val classNameSlot = slot<String>()
        val dataPointSlot = slot<DataPointEntity>()
        val onMetWorkflowStepSlot = slot<StepEntity>()
        val executionOrderSlot = slot<Int>()

        every { existingEntity.workflowStep = capture(workflowStepSlot) } just runs
        every { existingEntity.className = capture(classNameSlot) } just runs
        every { existingEntity.dataPointEntity = capture(dataPointSlot) } just runs
        every { existingEntity.onMetWorkflowStep = capture(onMetWorkflowStepSlot) } just runs
        every { existingEntity.executionOrder = capture(executionOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { InquisitionEntity.findById(42L) }
        verify { existingEntity.workflowStep = stepEntity }
        verify { existingEntity.className = dto.className }
        verify { existingEntity.dataPointEntity = dataPointEntity }
        verify { existingEntity.onMetWorkflowStep = onMetWorkflowStepEntity }
        verify { existingEntity.executionOrder = dto.executionOrder }

        // Verify the captured values
        assertEquals(stepEntity, workflowStepSlot.captured)
        assertEquals(dto.className, classNameSlot.captured)
        assertEquals(dataPointEntity, dataPointSlot.captured)
        assertEquals(onMetWorkflowStepEntity, onMetWorkflowStepSlot.captured)
        assertEquals(dto.executionOrder, executionOrderSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(InquisitionEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = Inquisition(
            id = 0L,
            step = mockk(),
            className = "newClass",
            dataPoint = mockk(),
            onMetWorkflowStep = mockk(),
            executionOrder = 3
        )

        // Mock the new entity that will be created
        val newEntity = mockk<InquisitionEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val dataPointEntity: DataPointEntity = mockk()
        val onMetWorkflowStepEntity: StepEntity = mockk()

        every { stepsMapper.toEntity(any<Step>()) }.returnsMany(stepEntity, onMetWorkflowStepEntity)
        every { dataPointsMapper.toEntity(any<DataPoint>()) } returns dataPointEntity
        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowStepSlot = slot<StepEntity>()
        val classNameSlot = slot<String>()
        val dataPointSlot = slot<DataPointEntity>()
        val onMetWorkflowStepSlot = slot<StepEntity>()
        val executionOrderSlot = slot<Int>()

        every { newEntity.workflowStep = capture(workflowStepSlot) } just runs
        every { newEntity.className = capture(classNameSlot) } just runs
        every { newEntity.dataPointEntity = capture(dataPointSlot) } just runs
        every { newEntity.onMetWorkflowStep = capture(onMetWorkflowStepSlot) } just runs
        every { newEntity.executionOrder = capture(executionOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflowStep = stepEntity }
        verify { newEntity.className = dto.className }
        verify { newEntity.dataPointEntity = dataPointEntity }
        verify { newEntity.onMetWorkflowStep = onMetWorkflowStepEntity }
        verify { newEntity.executionOrder = dto.executionOrder }

        // Verify the captured values
        assertEquals(stepEntity, workflowStepSlot.captured)
        assertEquals(dto.className, classNameSlot.captured)
        assertEquals(dataPointEntity, dataPointSlot.captured)
        assertEquals(onMetWorkflowStepEntity, onMetWorkflowStepSlot.captured)
        assertEquals(dto.executionOrder, executionOrderSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}