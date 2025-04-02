package com.wini.owle.mapper.steps

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class InquisitionMapperTest {

    private lateinit var mapper: InquisitionsMapper
    private val stepsMapper: StepsMapper = mockk()
    private val dataPointsMapper: DataPointsMapper = mockk()
    private val factory: DaoFactory<InquisitionDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = InquisitionsMapper(factory, stepsMapper, dataPointsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = InquisitionDto(
            id = 99L,
            step = mockk(),
            className = "testClass",
            dataPoint = mockk(),
            onMetWorkflowStep = mockk(),
            executionOrder = 1
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(InquisitionDao)
        every { InquisitionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Inquisition with id=99 not found", exception.message)
        unmockkObject(InquisitionDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<InquisitionDao> {
            every { id.value } returns 1L
            every { workflowStep } returns mockk()
            every { className } returns "testClass"
            every { dataPoint } returns mockk()
            every { onMetWorkflowStep } returns mockk()
            every { executionOrder } returns 1
        }

        // Mock the behavior of stepsMapper and dataPointsMapper
        val stepDto = mockk<StepDto>()
        val dataPointDto = mockk<DataPointDto>()
        val onMetWorkflowStepDto = mockk<StepDto>()
        every { stepsMapper.toDto(entity.workflowStep) } returns stepDto
        every { dataPointsMapper.toDto(entity.dataPoint) } returns dataPointDto
        every { stepsMapper.toDto(entity.onMetWorkflowStep) } returns onMetWorkflowStepDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(stepDto, result.step)
        assertEquals("testClass", result.className)
        assertEquals(dataPointDto, result.dataPoint)
        assertEquals(onMetWorkflowStepDto, result.onMetWorkflowStep)
        assertEquals(1, result.executionOrder)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = InquisitionDto(
            id = 42L,
            step = mockk(),
            className = "updatedClass",
            dataPoint = mockk(),
            onMetWorkflowStep = mockk(),
            executionOrder = 2
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<InquisitionDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(InquisitionDao)
        every { InquisitionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowStepSlot = slot<StepDao>()
        val classNameSlot = slot<String>()
        val dataPointSlot = slot<DataPointDao>()
        val onMetWorkflowStepSlot = slot<StepDao>()
        val executionOrderSlot = slot<Int>()

        every { existingEntity.workflowStep = capture(workflowStepSlot) } just runs
        every { existingEntity.className = capture(classNameSlot) } just runs
        every { existingEntity.dataPoint = capture(dataPointSlot) } just runs
        every { existingEntity.onMetWorkflowStep = capture(onMetWorkflowStepSlot) } just runs
        every { existingEntity.executionOrder = capture(executionOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { InquisitionDao.findById(42L) }
        verify { existingEntity.workflowStep = dto.step }
        verify { existingEntity.className = dto.className }
        verify { existingEntity.dataPoint = dto.dataPoint }
        verify { existingEntity.onMetWorkflowStep = dto.onMetWorkflowStep }
        verify { existingEntity.executionOrder = dto.executionOrder }

        // Verify the captured values
        assertEquals(dto.step, workflowStepSlot.captured)
        assertEquals(dto.className, classNameSlot.captured)
        assertEquals(dto.dataPoint, dataPointSlot.captured)
        assertEquals(dto.onMetWorkflowStep, onMetWorkflowStepSlot.captured)
        assertEquals(dto.executionOrder, executionOrderSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(InquisitionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = InquisitionDto(
            id = 0L,
            step = mockk(),
            className = "newClass",
            dataPoint = mockk(),
            onMetWorkflowStep = mockk(),
            executionOrder = 3
        )

        // Mock the new entity that will be created
        val newEntity = mockk<InquisitionDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowStepSlot = slot<StepDao>()
        val classNameSlot = slot<String>()
        val dataPointSlot = slot<DataPointDao>()
        val onMetWorkflowStepSlot = slot<StepDao>()
        val executionOrderSlot = slot<Int>()

        every { newEntity.workflowStep = capture(workflowStepSlot) } just runs
        every { newEntity.className = capture(classNameSlot) } just runs
        every { newEntity.dataPoint = capture(dataPointSlot) } just runs
        every { newEntity.onMetWorkflowStep = capture(onMetWorkflowStepSlot) } just runs
        every { newEntity.executionOrder = capture(executionOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflowStep = dto.step }
        verify { newEntity.className = dto.className }
        verify { newEntity.dataPoint = dto.dataPoint }
        verify { newEntity.onMetWorkflowStep = dto.onMetWorkflowStep }
        verify { newEntity.executionOrder = dto.executionOrder }

        // Verify the captured values
        assertEquals(dto.step, workflowStepSlot.captured)
        assertEquals(dto.className, classNameSlot.captured)
        assertEquals(dto.dataPoint, dataPointSlot.captured)
        assertEquals(dto.onMetWorkflowStep, onMetWorkflowStepSlot.captured)
        assertEquals(dto.executionOrder, executionOrderSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}