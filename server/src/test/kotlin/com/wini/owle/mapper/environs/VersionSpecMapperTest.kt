package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.mapper.ExposedDaoFactory
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

open class VersionSpecsMapperTest {

    private lateinit var mapper: VersionSpecsMapper
    private val factory: ExposedDaoFactory<VersionSpecEntity, Long, VersionSpecEntity.Companion> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = VersionSpecsMapper(factory)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        val dto = VersionSpec(id = 99L, versionMin = "2.0", versionMax = "3.0", versionExact = "2.5")

        mockkObject(VersionSpecEntity)
        every { VersionSpecEntity.findById(99L) } returns null

        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("VersionSpec with id=99 not found", exception.message)
        unmockkObject(VersionSpecEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        val entity = mockk<VersionSpecEntity> {
            every { id.value } returns 1L
            every { versionMin } returns "1.0"
            every { versionMax } returns "2.0"
            every { versionExact } returns "1.5"
        }

        val result = mapper.toDto(entity)

        assertEquals(1L, result.id)
        assertEquals("1.0", result.versionMin)
        assertEquals("2.0", result.versionMax)
        assertEquals("1.5", result.versionExact)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create a DTO with non-zero ID
        val dto = VersionSpec(id = 42L, versionMin = "1.5", versionMax = "3.0", versionExact = "2.0")

        // Mock the existing entity that will be updated
        val existingEntity = mockk<VersionSpecEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()

        // Set up mocks
        mockkObject(VersionSpecEntity)
        every { VersionSpecEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val minVersionSlot = slot<String>()
        val maxVersionSlot = slot<String>()
        val exactVersionSlot = slot<String>()

        every { existingEntity.versionMin = capture(minVersionSlot) } just runs
        every { existingEntity.versionMax = capture(maxVersionSlot) } just runs
        every { existingEntity.versionExact = capture(exactVersionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { VersionSpecEntity.findById(42L) }
        verify { existingEntity.versionMin = "1.5" }
        verify { existingEntity.versionMax = "3.0" }
        verify { existingEntity.versionExact = "2.0" }

        // Verify the captured values
        assertEquals("1.5", minVersionSlot.captured)
        assertEquals("3.0", maxVersionSlot.captured)
        assertEquals("2.0", exactVersionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(VersionSpecEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create a DTO with zero ID (indicating new entity)
        val dto = VersionSpec(id = 0L, versionMin = "2.0", versionMax = "4.0", versionExact = "3.0")

        // Mock the new entity that will be created
        val newEntity = mockk<VersionSpecEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Slots to capture property assignments
        val minVersionSlot = slot<String>()
        val maxVersionSlot = slot<String>()
        val exactVersionSlot = slot<String>()

        // Set up property capture
        every { newEntity.versionMin = capture(minVersionSlot) } just runs
        every { newEntity.versionMax = capture(maxVersionSlot) } just runs
        every { newEntity.versionExact = capture(exactVersionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.versionMin = "2.0" }
        verify { newEntity.versionMax = "4.0" }
        verify { newEntity.versionExact = "3.0" }

        // Verify the captured values
        assertEquals("2.0", minVersionSlot.captured)
        assertEquals("4.0", maxVersionSlot.captured)
        assertEquals("3.0", exactVersionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}
