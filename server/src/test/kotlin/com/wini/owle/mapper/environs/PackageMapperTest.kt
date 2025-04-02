package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class PackageMapperTest {

    private lateinit var mapper: PackagesMapper
    private val versionSpecsMapper: VersionSpecsMapper = mockk()
    private val factory: DaoFactory<PackageDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PackagesMapper(factory, versionSpecsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val versionSpecDto = VersionSpecDto(id = 1L, versionMin = "1.0", versionMax = "2.0", versionExact = "1.5")
        val packageDto = PackageDto(id = 99L, name = "numpy", disambiguation = "test", versionSpec = versionSpecDto)

        // Mock the VersionSpecDao that would be created from the DTO
        val versionSpecDao = mockk<VersionSpecDao>()
        every { versionSpecsMapper.toEntity(versionSpecDto) } returns versionSpecDao

        // Mock the behavior of findById to return null (not found)
        mockkObject(PackageDao)
        every { PackageDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(packageDto)
        }

        assertEquals("Package with id=99 not found", exception.message)
        unmockkObject(PackageDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock objects
        val versionSpecDao = mockk<VersionSpecDao>()
        val packageDao = mockk<PackageDao> {
            every { id.value } returns 1L
            every { name } returns "pandas"
            every { disambiguation } returns "data-analysis"
            every { versionSpec } returns versionSpecDao
        }

        // Mock the behavior of versionSpecsMapper
        val versionSpecDto = VersionSpecDto(id = 2L, versionMin = "2.0", versionMax = "3.0", versionExact = "2.5")
        every { versionSpecsMapper.toDto(versionSpecDao) } returns versionSpecDto

        // Call the method under test
        val result = mapper.toDto(packageDao)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("pandas", result.name)
        assertEquals("data-analysis", result.disambiguation)
        assertEquals(versionSpecDto, result.versionSpec)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val versionSpecDto = VersionSpecDto(id = 3L, versionMin = "1.5", versionMax = "3.0", versionExact = "2.0")
        val packageDto = PackageDto(id = 42L, name = "scikit-learn", disambiguation = "ml", versionSpec = versionSpecDto)

        // Mock the existing entity that will be updated
        val existingEntity = mockk<PackageDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowExecutionDao: WorkflowExecutionDao = mockk()

        // Mock the VersionSpecDao that would be returned from the mapper
        val versionSpecDao = mockk<VersionSpecDao>()
        every { versionSpecsMapper.toEntity(versionSpecDto) } returns versionSpecDao

        // Set up mocks for finding existing entity
        mockkObject(PackageDao)
        every { PackageDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val disambiguationSlot = slot<String>()
        val versionSpecSlot = slot<VersionSpecDao>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.disambiguation = capture(disambiguationSlot) } just runs
        every { existingEntity.versionSpec = capture(versionSpecSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(packageDto)

        // Verify that the existing entity was found and updated
        verify { PackageDao.findById(42L) }
        verify { existingEntity.name = "scikit-learn" }
        verify { existingEntity.disambiguation = "ml" }
        verify { existingEntity.versionSpec = versionSpecDao }
        verify { versionSpecsMapper.toEntity(versionSpecDto) }

        // Verify the captured values
        assertEquals("scikit-learn", nameSlot.captured)
        assertEquals("ml", disambiguationSlot.captured)
        assertEquals(versionSpecDao, versionSpecSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PackageDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val versionSpecDto = VersionSpecDto(id = 4L, versionMin = "2.0", versionMax = "4.0", versionExact = "3.0")
        val packageDto = PackageDto(id = 0L, name = "tensorflow", disambiguation = "ml", versionSpec = versionSpecDto)

        // Mock the new entity that will be created
        val newEntity = mockk<PackageDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowExecutionDao: WorkflowExecutionDao = mockk()

        // Mock the VersionSpecDao that would be returned from the mapper
        val versionSpecDao = mockk<VersionSpecDao>()
        every { versionSpecsMapper.toEntity(versionSpecDto) } returns versionSpecDao

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Slots to capture property assignments
        val nameSlot = slot<String>()
        val disambiguationSlot = slot<String>()
        val versionSpecSlot = slot<VersionSpecDao>()

        // Set up property capture
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.disambiguation = capture(disambiguationSlot) } just runs
        every { newEntity.versionSpec = capture(versionSpecSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(packageDto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }
        verify { versionSpecsMapper.toEntity(versionSpecDto) }

        // Verify the properties were set correctly
        verify { newEntity.name = "tensorflow" }
        verify { newEntity.disambiguation = "ml" }
        verify { newEntity.versionSpec = versionSpecDao }

        // Verify the captured values
        assertEquals("tensorflow", nameSlot.captured)
        assertEquals("ml", disambiguationSlot.captured)
        assertEquals(versionSpecDao, versionSpecSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}