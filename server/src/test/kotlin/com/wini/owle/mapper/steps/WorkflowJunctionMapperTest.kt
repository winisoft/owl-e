package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
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
    private val factory: DaoFactory<WorkflowJunctionEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = WorkflowJunctionsMapper(factory, stepsMapper, workflowsMapper, inquisitionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = WorkflowJunction(
            id = 99L,
            sourceStep = mockk(),
            targetWorkflow = mockk(),
            triggerInquisition = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(WorkflowJunctionEntity)
        every { WorkflowJunctionEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("WorkflowJunction with id=99 not found", exception.message)
        unmockkObject(WorkflowJunctionEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<WorkflowJunctionEntity> {
            every { id.value } returns 1L
            every { sourceStep } returns mockk()
            every { targetWorkflow } returns mockk()
            every { triggerInquisitionEntity } returns mockk()
        }

        // Mock the behavior of stepsMapper, workflowsMapper, and inquisitionsMapper
        val sourceStep = mockk<Step>()
        val targetWorkflow = mockk<Workflow>()
        val triggerInquisitionDto = mockk<Inquisition>()
        every { stepsMapper.toDto(entity.sourceStep) } returns sourceStep
        every { workflowsMapper.toDto(entity.targetWorkflow) } returns targetWorkflow
        every { entity.triggerInquisitionEntity?.let { inquisitionsMapper.toDto(it) } } returns triggerInquisitionDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(sourceStep, result.sourceStep)
        assertEquals(targetWorkflow, result.targetWorkflow)
        assertEquals(triggerInquisitionDto, result.triggerInquisition)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = WorkflowJunction(id = 42L, sourceStep = mockk(), targetWorkflow = mockk(), triggerInquisition = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<WorkflowJunctionEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val workflowEntity: WorkflowEntity = mockk()
        val inquisitionEntity: InquisitionEntity = mockk()

        every { stepsMapper.toEntity(any<Step>()) } returns stepEntity
        every { workflowsMapper.toEntity(any<Workflow>()) } returns workflowEntity
        every { inquisitionsMapper.toEntity(any<Inquisition>()) } returns inquisitionEntity

        // Set up mocks for finding existing entity
        mockkObject(WorkflowJunctionEntity)
        every { WorkflowJunctionEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val sourceStepSlot = slot<StepEntity>()
        val targetWorkflowSlot = slot<WorkflowEntity>()
        val triggerInquisitionEntitySlot = slot<InquisitionEntity>()

        every { existingEntity.sourceStep = capture(sourceStepSlot) } just runs
        every { existingEntity.targetWorkflow = capture(targetWorkflowSlot) } just runs
        every { existingEntity.triggerInquisitionEntity = capture(triggerInquisitionEntitySlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { WorkflowJunctionEntity.findById(42L) }
        verify { existingEntity.sourceStep = stepEntity }
        verify { existingEntity.targetWorkflow = workflowEntity }
        verify { existingEntity.triggerInquisitionEntity = inquisitionEntity }

        // Verify the captured values
        assertEquals(stepEntity, sourceStepSlot.captured)
        assertEquals(workflowEntity, targetWorkflowSlot.captured)
        assertEquals(inquisitionEntity, triggerInquisitionEntitySlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(WorkflowJunctionEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = WorkflowJunction(
            id = 0L,
            sourceStep = mockk<Step>(),
            targetWorkflow = mockk<Workflow>(),
            triggerInquisition = mockk<Inquisition>()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<WorkflowJunctionEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val workflowEntity: WorkflowEntity = mockk()
        val inquisitionEntity: InquisitionEntity = mockk()

        every { stepsMapper.toEntity(any<Step>()) } returns stepEntity
        every { workflowsMapper.toEntity(any<Workflow>()) } returns workflowEntity
        every { inquisitionsMapper.toEntity(any<Inquisition>()) } returns inquisitionEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val sourceStepSlot = slot<StepEntity>()
        val targetWorkflowSlot = slot<WorkflowEntity>()
        val triggerInquisitionEntitySlot = slot<InquisitionEntity>()

        every { newEntity.sourceStep = capture(sourceStepSlot) } just runs
        every { newEntity.targetWorkflow = capture(targetWorkflowSlot) } just runs
        every { newEntity.triggerInquisitionEntity = capture(triggerInquisitionEntitySlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.sourceStep = stepEntity }
        verify { newEntity.targetWorkflow = workflowEntity }
        verify { newEntity.triggerInquisitionEntity = inquisitionEntity }

        // Verify the captured values
        assertEquals(stepEntity, sourceStepSlot.captured)
        assertEquals(workflowEntity, targetWorkflowSlot.captured)
        assertEquals(inquisitionEntity, triggerInquisitionEntitySlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}