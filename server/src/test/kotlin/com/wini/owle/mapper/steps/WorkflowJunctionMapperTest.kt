package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.dto.steps.WorkflowJunctionDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class WorkflowJunctionMapperTest {

    private lateinit var mapper: WorkflowJunctionsMapper
    private val stepsMapper: StepsMapper = mockk()
    private val workflowsMapper: WorkflowsMapper = mockk()
    private val inquisitionsMapper: InquisitionsMapper = mockk()
    private val factory: DaoFactory<WorkflowJunctionDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = WorkflowJunctionsMapper(factory, stepsMapper, workflowsMapper, inquisitionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = WorkflowJunctionDto(
            id = 99L,
            sourceStep = mockk(),
            targetWorkflow = mockk(),
            triggerInquisition = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(WorkflowJunctionDao)
        every { WorkflowJunctionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("WorkflowJunction with id=99 not found", exception.message)
        unmockkObject(WorkflowJunctionDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<WorkflowJunctionDao> {
            every { id.value } returns 1L
            every { sourceStep } returns mockk()
            every { targetWorkflow } returns mockk()
            every { triggerInquisition } returns mockk()
        }

        // Mock the behavior of stepsMapper, workflowsMapper, and inquisitionsMapper
        val sourceStepDto = mockk<StepDto>()
        val targetWorkflowDto = mockk<WorkflowDto>()
        val triggerInquisitionDto = mockk<InquisitionDto>()
        every { stepsMapper.toDto(entity.sourceStep) } returns sourceStepDto
        every { workflowsMapper.toDto(entity.targetWorkflow) } returns targetWorkflowDto
        every { entity.triggerInquisition?.let { inquisitionsMapper.toDto(it) } } returns triggerInquisitionDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(sourceStepDto, result.sourceStep)
        assertEquals(targetWorkflowDto, result.targetWorkflow)
        assertEquals(triggerInquisitionDto, result.triggerInquisition)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = WorkflowJunctionDto(id = 42L, sourceStep = mockk(), targetWorkflow = mockk(), triggerInquisition = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<WorkflowJunctionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepDao: StepDao = mockk()
        val workflowDao: WorkflowDao = mockk()
        val inquisitionDao: InquisitionDao = mockk()

        every { stepsMapper.toEntity(any<StepDto>()) } returns stepDao
        every { workflowsMapper.toEntity(any<WorkflowDto>()) } returns workflowDao
        every { inquisitionsMapper.toEntity(any<InquisitionDto>()) } returns inquisitionDao

        // Set up mocks for finding existing entity
        mockkObject(WorkflowJunctionDao)
        every { WorkflowJunctionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val sourceStepSlot = slot<StepDao>()
        val targetWorkflowSlot = slot<WorkflowDao>()
        val triggerInquisitionSlot = slot<InquisitionDao>()

        every { existingEntity.sourceStep = capture(sourceStepSlot) } just runs
        every { existingEntity.targetWorkflow = capture(targetWorkflowSlot) } just runs
        every { existingEntity.triggerInquisition = capture(triggerInquisitionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { WorkflowJunctionDao.findById(42L) }
        verify { existingEntity.sourceStep = stepDao }
        verify { existingEntity.targetWorkflow = workflowDao }
        verify { existingEntity.triggerInquisition = inquisitionDao }

        // Verify the captured values
        assertEquals(stepDao, sourceStepSlot.captured)
        assertEquals(workflowDao, targetWorkflowSlot.captured)
        assertEquals(inquisitionDao, triggerInquisitionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowJunctionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = WorkflowJunctionDto(
            id = 0L,
            sourceStep = mockk<StepDto>(),
            targetWorkflow = mockk<WorkflowDto>(),
            triggerInquisition = mockk<InquisitionDto>()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<WorkflowJunctionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepDao: StepDao = mockk()
        val workflowDao: WorkflowDao = mockk()
        val inquisitionDao: InquisitionDao = mockk()

        every { stepsMapper.toEntity(any<StepDto>()) } returns stepDao
        every { workflowsMapper.toEntity(any<WorkflowDto>()) } returns workflowDao
        every { inquisitionsMapper.toEntity(any<InquisitionDto>()) } returns inquisitionDao

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val sourceStepSlot = slot<StepDao>()
        val targetWorkflowSlot = slot<WorkflowDao>()
        val triggerInquisitionSlot = slot<InquisitionDao>()

        every { newEntity.sourceStep = capture(sourceStepSlot) } just runs
        every { newEntity.targetWorkflow = capture(targetWorkflowSlot) } just runs
        every { newEntity.triggerInquisition = capture(triggerInquisitionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.sourceStep = stepDao }
        verify { newEntity.targetWorkflow = workflowDao }
        verify { newEntity.triggerInquisition = inquisitionDao }

        // Verify the captured values
        assertEquals(stepDao, sourceStepSlot.captured)
        assertEquals(workflowDao, targetWorkflowSlot.captured)
        assertEquals(inquisitionDao, triggerInquisitionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}