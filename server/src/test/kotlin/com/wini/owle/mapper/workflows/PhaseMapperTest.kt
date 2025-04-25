package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Phase
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.mapper.DaoFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class PhaseMapperTest {

    private lateinit var mapper: PhasesMapper
    private val workflowMapper: WorkflowsMapper = mockk()
    private val factory: DaoFactory<PhaseEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PhasesMapper(factory, workflowMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = Phase(
            id = 99L,
            workflow = mockk(),
            parentPhase = null,
            name = "phaseName",
            description = "description",
            displayOrder = 1
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(PhaseEntity)
        every { PhaseEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Phase with id=99 not found", exception.message)
        unmockkObject(PhaseEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<PhaseEntity> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { parentPhase } returns null
            every { name } returns "phaseName"
            every { description } returns "description"
            every { displayOrder } returns 1
        }

        // Mock the behavior of workflowMapper
        val workflow = mockk<Workflow>()
        every { workflowMapper.toDto(entity.workflow) } returns workflow

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflow, result.workflow)
        assertEquals(null, result.parentPhase)
        assertEquals("phaseName", result.name)
        assertEquals("description", result.description)
        assertEquals(1, result.displayOrder)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = Phase(
            id = 42L,
            workflow = mockk(),
            parentPhase = null,
            name = "phaseName",
            description = "description",
            displayOrder = 1
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<PhaseEntity>(relaxed = true)

        // Mock the workflow DAOs that would be returned from the mapper
        val workflowEntity: WorkflowEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(PhaseEntity)
        every { PhaseEntity.findById(42L) } returns existingEntity
        every { workflowMapper.toEntity(dto.workflow) } returns workflowEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { PhaseEntity.findById(42L) }
        verify { existingEntity.workflow = workflowEntity }
        verify { existingEntity.parentPhase = null }
        verify { existingEntity.name = "phaseName" }
        verify { existingEntity.description = "description" }
        verify { existingEntity.displayOrder = 1 }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PhaseEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create a mock workflow and DTO
        val workflow = mockk<Workflow>()
        val workflowEntity = mockk<WorkflowEntity>()
        
        // Create a DTO with no parent phase initially
        val dto = Phase(
            id = 0L,
            workflow = workflow,
            parentPhase = null,  // No parent phase to avoid recursion issues
            name = "phaseName",
            description = "description",
            displayOrder = 1
        )

        // Mock the new entity that will be created
        val newEntity = mockk<PhaseEntity>(relaxed = true)

        // Set up the factory and workflow mapper mocks
        every { factory.create() } returns newEntity
        every { workflowMapper.toEntity(workflow) } returns workflowEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify the properties were set correctly
        verify(exactly = 1) { factory.create() }
        verify(exactly = 1) { workflowMapper.toEntity(workflow) }
        verify(exactly = 1) { newEntity.workflow = workflowEntity }
        verify(exactly = 1) { newEntity.parentPhase = null }
        verify(exactly = 1) { newEntity.name = "phaseName" }
        verify(exactly = 1) { newEntity.description = "description" }
        verify(exactly = 1) { newEntity.displayOrder = 1 }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }

    @Test
    fun `toEntity should correctly handle parent phase recursion`() {
        // Create a mock child DTO with a mock parent
        val parentWorkflow = mockk<Workflow>()
        val childWorkflow = mockk<Workflow>()
        val parentWorkflowEntity = mockk<WorkflowEntity>()
        val childWorkflowEntity = mockk<WorkflowEntity>()
        
        // Create the parent phase first (will be found by ID)
        val parentDto = Phase(
            id = 42L,
            workflow = parentWorkflow,
            parentPhase = null, 
            name = "parentPhaseName",
            description = "parentDescription",
            displayOrder = 1
        )
        
        // The existing parent entity that will be found
        val parentEntity = mockk<PhaseEntity>(relaxed = true)
        
        // Create the child phase that has the parent
        val childDto = Phase(
            id = 0L,
            workflow = childWorkflow,
            parentPhase = parentDto,
            name = "childPhaseName",
            description = "childDescription",
            displayOrder = 2
        )
        
        // The new child entity that will be created
        val childEntity = mockk<PhaseEntity>(relaxed = true)
        
        // Set up all the mocks
        mockkObject(PhaseEntity)
        every { PhaseEntity.findById(42L) } returns parentEntity
        every { factory.create() } returns childEntity
        every { workflowMapper.toEntity(parentWorkflow) } returns parentWorkflowEntity
        every { workflowMapper.toEntity(childWorkflow) } returns childWorkflowEntity
        
        // Execute the method for the child DTO
        val result = mapper.toEntity(childDto)
        
        // Verify parent lookup and workflow mapping
        verify { PhaseEntity.findById(42L) }
        verify { workflowMapper.toEntity(parentWorkflow) }
        verify { parentEntity.workflow = parentWorkflowEntity }
        
        // Verify child properties including parent assignment
        verify { factory.create() }
        verify { childEntity.workflow = childWorkflowEntity }
        verify { childEntity.parentPhase = parentEntity }
        verify { childEntity.name = "childPhaseName" }
        verify { childEntity.description = "childDescription" }
        verify { childEntity.displayOrder = 2 }
        
        // Verify the result is the child entity
        assertEquals(childEntity, result)
        
        unmockkObject(PhaseEntity)
    }
}