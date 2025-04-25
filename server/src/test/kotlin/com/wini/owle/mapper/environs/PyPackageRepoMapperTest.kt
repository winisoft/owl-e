package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.enums.PyPackageRepoType
import com.wini.owle.entity.environs.PyPackageRepoEntity
import com.wini.owle.entity.environs.enums.PackageRepoTypeEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.environs.enums.PackageRepoTypeMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class PyPackageRepoMapperTest {

    private lateinit var mapper: PackageRepoMapper
    private val factory: DaoFactory<PyPackageRepoEntity, Long> = mockk()
    private val packageRepoTypeMapper: PackageRepoTypeMapper = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PackageRepoMapper(factory, packageRepoTypeMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        val dto = PyPackageRepo(
            id = 99L, 
            sourceType = PyPackageRepoType.Pypi,
            sourceUrl = "http://example.com"
        )

        mockkObject(PyPackageRepoEntity)
        every { PyPackageRepoEntity.findById(99L) } returns null

        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("PackageRepo with id=99 not found", exception.message)
        unmockkObject(PyPackageRepoEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        
        val entity = mockk<PyPackageRepoEntity> {
            every { id.value } returns 1L
            every { sourceType } returns mockk()
            every { sourceUrl } returns "http://example.com"
        }

        every { packageRepoTypeMapper.toDto(any()) } returns PyPackageRepoType.Pypi

        val result = mapper.toDto(entity)

        assertEquals(1L, result.id)
        assertEquals(PyPackageRepoType.Pypi, result.sourceType)
        assertEquals("http://example.com", result.sourceUrl)

    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        val dto = PyPackageRepo(
            id = 42L,
            sourceType = PyPackageRepoType.Pypi,
            sourceUrl = "http://updated.com"
        )

        val existingEntity = mockk<PyPackageRepoEntity>(relaxed = true)

        mockkObject(PyPackageRepoEntity)
        every { PyPackageRepoEntity.findById(42L) } returns existingEntity
        every { packageRepoTypeMapper.toEntity(any()) } returns PackageRepoTypeEntity.PYPI

        val sourceTypeSlot = slot<PackageRepoTypeEntity>()
        val sourceUrlSlot = slot<String>()

        every { existingEntity.sourceType = capture(sourceTypeSlot) } just runs
        every { existingEntity.sourceUrl = capture(sourceUrlSlot) } just runs

        val result = mapper.toEntity(dto)

        verify { PyPackageRepoEntity.findById(42L) }
        verify { existingEntity.sourceType = PackageRepoTypeEntity.PYPI }
        verify { existingEntity.sourceUrl = "http://updated.com" }

        assertEquals(PackageRepoTypeEntity.PYPI, sourceTypeSlot.captured)
        assertEquals("http://updated.com", sourceUrlSlot.captured)

        assertEquals(existingEntity, result)

        unmockkObject(PyPackageRepoEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        val dto = PyPackageRepo(id = 0L, sourceType = PyPackageRepoType.Pypi, sourceUrl = "http://new.com")

        val newEntity = mockk<PyPackageRepoEntity>(relaxed = true)

        every { factory.create() } returns newEntity
        every { packageRepoTypeMapper.toEntity(any()) } returns PackageRepoTypeEntity.PYPI

        val sourceTypeSlot = slot<PackageRepoTypeEntity>()
        val sourceUrlSlot = slot<String>()

        every { newEntity.sourceType = capture(sourceTypeSlot) } just runs
        every { newEntity.sourceUrl = capture(sourceUrlSlot) } just runs

        val result = mapper.toEntity(dto)

        verify { factory.create() }
        verify { newEntity.sourceType = PackageRepoTypeEntity.PYPI }
        verify { newEntity.sourceUrl = "http://new.com" }

        assertEquals(PackageRepoTypeEntity.PYPI, sourceTypeSlot.captured)
        assertEquals("http://new.com", sourceUrlSlot.captured)

        assertEquals(newEntity, result)
    }
}