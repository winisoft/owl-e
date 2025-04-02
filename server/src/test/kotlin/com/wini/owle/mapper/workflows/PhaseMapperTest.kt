package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.PhaseDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.workflows.PhaseDao
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


open class PhaseMapperTest {

    private lateinit var mapper: PhasesMapper
    private val workflowMapper: WorkflowsMapper = mockk()
    private val factory: DaoFactory<PhaseDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PhasesMapper(factory, workflowMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = PhaseDto(id = 99L, workflow = mockk(), parentPhase = null, name = "phaseName", description = "description", displayOrder = 1)

        // Mock the behavior of findById to return null (not found)
        mockkObject(PhaseDao)
        every { PhaseDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Phase with id=99 not found", exception.message)
        unmockkObject(PhaseDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<PhaseDao> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { parentPhase } returns null
            every { name } returns "phaseName"
            every { description } returns "description"
            every { displayOrder } returns 1
        }

        // Mock the behavior of workflowMapper
        val workflowDto = mockk<WorkflowDto>()
        every { workflowMapper.toDto(entity.workflow) } returns workflowDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowDto, result.workflow)
        assertEquals(null, result.parentPhase)
        assertEquals("phaseName", result.name)
        assertEquals("description", result.description)
        assertEquals(1, result.displayOrder)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = PhaseDto(
            id = 42L,
            workflow = mockk(),
            parentPhase = null,
            name = "phaseName",
            description = "description",
            displayOrder = 1
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<PhaseDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(PhaseDao)
        every { PhaseDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowSlot = slot<Workflows>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val displayOrderSlot = slot<Int>()

        every { existingEntity.workflow = capture(workflowSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.displayOrder = capture(displayOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { PhaseDao.findById(42L) }
        verify { existingEntity.workflow = dto.workflow }
        verify { existingEntity.name = "phaseName" }
        verify { existingEntity.description = "description" }
        verify { existingEntity.displayOrder = 1 }

        // Verify the captured values
        assertEquals(dto.workflow, workflowSlot.captured)
        assertEquals("phaseName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals(1, displayOrderSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PhaseDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = PhaseDto(
            id = 0L,
            workflow = mockk(),
            parentPhase = null,
            name = "phaseName",
            description = "description",
            displayOrder = 1
        )

        // Mock the new entity that will be created
        val newEntity = mockk<PhaseDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowSlot = slot<Workflows>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val displayOrderSlot = slot<Int>()

        every { newEntity.workflow = capture(workflowSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.displayOrder = capture(displayOrderSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflow = dto.workflow }
        verify { newEntity.name = "phaseName" }
        verify { newEntity.description = "description" }
        verify { newEntity.displayOrder = 1 }

        // Verify the captured values
        assertEquals(dto.workflow, workflowSlot.captured)
        assertEquals("phaseName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals(1, displayOrderSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}