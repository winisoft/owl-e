package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.entity.environs.PyPackageEntity
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class PyPackageMapperTest {

    private lateinit var mapper: PackagesMapper
    private val versionSpecsMapper: VersionSpecsMapper = mockk()
    private val factory: DaoFactory<PyPackageEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PackagesMapper(factory, versionSpecsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val versionSpec = VersionSpec(id = 1L, versionMin = "1.0", versionMax = "2.0", versionExact = "1.5")
        val pyPackage =
            PyPackage(id = 99L, name = "numpy", disambiguation = "test", versionSpec = versionSpec)

        // Mock the VersionSpecDao that would be created from the DTO
        val versionSpecEntity = mockk<VersionSpecEntity>()
        every { versionSpecsMapper.toEntity(versionSpec) } returns versionSpecEntity

        // Mock the behavior of findById to return null (not found)
        mockkObject(PyPackageEntity)
        every { PyPackageEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(pyPackage)
        }

        assertEquals("Package with id=99 not found", exception.message)
        unmockkObject(PyPackageEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock objects
        val versionSpecEntity = mockk<VersionSpecEntity>()
        val packageDao = mockk<PyPackageEntity> {
            every { id.value } returns 1L
            every { name } returns "pandas"
            every { disambiguation } returns "data-analysis"
            every { versionSpec } returns versionSpecEntity
        }

        // Mock the behavior of versionSpecsMapper
        val versionSpec = VersionSpec(id = 2L, versionMin = "2.0", versionMax = "3.0", versionExact = "2.5")
        every { versionSpecsMapper.toDto(versionSpecEntity) } returns versionSpec

        // Call the method under test
        val result = mapper.toDto(packageDao)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("pandas", result.name)
        assertEquals("data-analysis", result.disambiguation)
        assertEquals(versionSpec, result.versionSpec)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val versionSpec = VersionSpec(id = 3L, versionMin = "1.5", versionMax = "3.0", versionExact = "2.0")
        val pyPackage =
            PyPackage(id = 42L, name = "scikit-learn", disambiguation = "ml", versionSpec = versionSpec)

        // Mock the existing entity that will be updated
        val existingEntity = mockk<PyPackageEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()

        // Mock the VersionSpecDao that would be returned from the mapper
        val versionSpecEntity = mockk<VersionSpecEntity>()
        every { versionSpecsMapper.toEntity(versionSpec) } returns versionSpecEntity

        // Set up mocks for finding existing entity
        mockkObject(PyPackageEntity)
        every { PyPackageEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val disambiguationSlot = slot<String>()
        val versionSpecSlot = slot<VersionSpecEntity>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.disambiguation = capture(disambiguationSlot) } just runs
        every { existingEntity.versionSpec = capture(versionSpecSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(pyPackage)

        // Verify that the existing entity was found and updated
        verify { PyPackageEntity.findById(42L) }
        verify { existingEntity.name = "scikit-learn" }
        verify { existingEntity.disambiguation = "ml" }
        verify { existingEntity.versionSpec = versionSpecEntity }
        verify { versionSpecsMapper.toEntity(versionSpec) }

        // Verify the captured values
        assertEquals("scikit-learn", nameSlot.captured)
        assertEquals("ml", disambiguationSlot.captured)
        assertEquals(versionSpecEntity, versionSpecSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PyPackageEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val versionSpec = VersionSpec(id = 4L, versionMin = "2.0", versionMax = "4.0", versionExact = "3.0")
        val pyPackage =
            PyPackage(id = 0L, name = "tensorflow", disambiguation = "ml", versionSpec = versionSpec)

        // Mock the new entity that will be created
        val newEntity = mockk<PyPackageEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowRunEntity: WorkflowRunEntity = mockk()

        // Mock the VersionSpecDao that would be returned from the mapper
        val versionSpecEntity = mockk<VersionSpecEntity>()
        every { versionSpecsMapper.toEntity(versionSpec) } returns versionSpecEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Slots to capture property assignments
        val nameSlot = slot<String>()
        val disambiguationSlot = slot<String>()
        val versionSpecSlot = slot<VersionSpecEntity>()

        // Set up property capture
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.disambiguation = capture(disambiguationSlot) } just runs
        every { newEntity.versionSpec = capture(versionSpecSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(pyPackage)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }
        verify { versionSpecsMapper.toEntity(versionSpec) }

        // Verify the properties were set correctly
        verify { newEntity.name = "tensorflow" }
        verify { newEntity.disambiguation = "ml" }
        verify { newEntity.versionSpec = versionSpecEntity }

        // Verify the captured values
        assertEquals("tensorflow", nameSlot.captured)
        assertEquals("ml", disambiguationSlot.captured)
        assertEquals(versionSpecEntity, versionSpecSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}