package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.slot
import io.mockk.just
import io.mockk.runs
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class StepMapperTest {

    private lateinit var mapper: StepsMapper
    private val workflowMapper: WorkflowsMapper = mockk()
    private val toolMapper: ToolsMapper = mockk()
    private val phasesMapper: PhasesMapper = mockk()
    private val factory: DaoFactory<StepDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = StepsMapper(factory, workflowMapper, toolMapper, phasesMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = StepDto(id = 99L, workflow = mockk(), name = "stepName", description = "description", tool = mockk(), nextStep = null, isWorkflowHead = true, parentPhase = null, displayOrder = 1)

        // Mock the behavior of findById to return null (not found)
        mockkObject(StepDao)
        every { StepDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Step with id=99 not found", exception.message)
        unmockkObject(StepDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<StepDao> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { name } returns "stepName"
            every { description } returns "description"
            every { tool } returns mockk()
            every { defaultNext } returns null
            every { isWorkflowHead } returns true
            every { parentPhase } returns null
            every { displayOrder } returns 1
        }

        // Mock the behavior of workflowMapper, toolMapper, and phasesMapper
        val workflowDto = mockk<WorkflowDto>()
        val toolDto = mockk<ToolDto>()
        every { workflowMapper.toDto(entity.workflow) } returns workflowDto
        every { toolMapper.toDto(entity.tool) } returns toolDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowDto, result.workflow)
        assertEquals("stepName", result.name)
        assertEquals("description", result.description)
        assertEquals(toolDto, result.tool)
        assertEquals(null, result.nextStep)
        assertEquals(true, result.isWorkflowHead)
        assertEquals(null, result.parentPhase)
        assertEquals(1, result.displayOrder)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = StepDto(
            id = 42L,
            workflow = mockk(),
            name = "stepName",
            description = "description",
            tool = mockk(),
            nextStep = null,
            isWorkflowHead = true,
            parentPhase = null,
            displayOrder = 1
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<StepDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowDao: WorkflowDao = mockk()
        val toolDao: ToolDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(StepDao)
        every { StepDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowSlot = slot<WorkflowDao>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val toolSlot = slot<ToolDao>()
        val isWorkflowHeadSlot = slot<Boolean>()
        val displayOrderSlot = slot<Int>()

        every { existingEntity.workflow = capture(workflowSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.tool = capture(toolSlot) } just runs
        every { existingEntity.isWorkflowHead = capture(isWorkflowHeadSlot) } just runs
        every { existingEntity.displayOrder = capture(displayOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { StepDao.findById(42L) }
        verify { existingEntity.workflow = workflowDao }
        verify { existingEntity.name = "stepName" }
        verify { existingEntity.description = "description" }
        verify { existingEntity.tool = toolDao }
        verify { existingEntity.isWorkflowHead = true }
        verify { existingEntity.displayOrder = 1 }

        // Verify the captured values
        assertEquals(workflowDao, workflowSlot.captured)
        assertEquals("stepName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals(toolDao, toolSlot.captured)
        assertEquals(true, isWorkflowHeadSlot.captured)
        assertEquals(1, displayOrderSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(StepDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = StepDto(
            id = 0L,
            workflow = mockk(),
            name = "stepName",
            description = "description",
            tool = mockk(),
            nextStep = null,
            isWorkflowHead = true,
            parentPhase = null,
            displayOrder = 1
        )

        // Mock the new entity that will be created
        val newEntity = mockk<StepDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowDao: WorkflowDao = mockk()
        val toolDao: ToolDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowSlot = slot<WorkflowDao>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val toolSlot = slot<ToolDao>()
        val isWorkflowHeadSlot = slot<Boolean>()
        val displayOrderSlot = slot<Int>()

        every { newEntity.workflow = capture(workflowSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.tool = capture(toolSlot) } just runs
        every { newEntity.isWorkflowHead = capture(isWorkflowHeadSlot) } just runs
        every { newEntity.displayOrder = capture(displayOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflow = workflowDao }
        verify { newEntity.name = "stepName" }
        verify { newEntity.description = "description" }
        verify { newEntity.tool = toolDao }
        verify { newEntity.isWorkflowHead = true }
        verify { newEntity.displayOrder = 1 }

        // Verify the captured values
        assertEquals(workflowDao, workflowSlot.captured)
        assertEquals("stepName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals(toolDao, toolSlot.captured)
        assertEquals(true, isWorkflowHeadSlot.captured)
        assertEquals(1, displayOrderSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}