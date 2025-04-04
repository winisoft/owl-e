package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.environs.PackageRepoTypeDto
import com.wini.owle.entity.environs.PackageRepoType.Companion.toDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.environs.PackageRepoType
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class PackageRepoMapperTest {

    private lateinit var mapper: PackageRepoMapper
    private val factory: DaoFactory<PackageRepoDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PackageRepoMapper(factory)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        val dto = PackageRepoDto(
            id = 99L, 
            sourceType = PackageRepoTypeDto.Pypi,
            sourceUrl = "http://example.com"
        )

        mockkObject(PackageRepoDao)
        every { PackageRepoDao.findById(99L) } returns null

        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("PackageRepo with id=99 not found", exception.message)
        unmockkObject(PackageRepoDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        val mockSourceType = mockk<PackageRepoType>()
        
        val entity = mockk<PackageRepoDao> {
            every { id.value } returns 1L
            every { sourceType } returns mockSourceType
            every { sourceUrl } returns "http://example.com"
        }
        
        // Mock the extension function behavior
        mockkObject(PackageRepoType.Companion)
        every { mockSourceType.toDto() } returns PackageRepoTypeDto.Pypi

        val result = mapper.toDto(entity)

        assertEquals(1L, result.id)
        assertEquals(PackageRepoTypeDto.Pypi, result.sourceType)
        assertEquals("http://example.com", result.sourceUrl)
        
        unmockkObject(PackageRepoType.Companion)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        val dto = PackageRepoDto(
            id = 42L,
            sourceType = PackageRepoTypeDto.Pypi,
            sourceUrl = "http://updated.com"
        )

        val existingEntity = mockk<PackageRepoDao>(relaxed = true)

        mockkObject(PackageRepoDao)
        every { PackageRepoDao.findById(42L) } returns existingEntity

        val sourceTypeSlot = slot<PackageRepoType>()
        val sourceUrlSlot = slot<String>()

        every { existingEntity.sourceType = capture(sourceTypeSlot) } just runs
        every { existingEntity.sourceUrl = capture(sourceUrlSlot) } just runs

        val result = mapper.toEntity(dto)

        verify { PackageRepoDao.findById(42L) }
        verify { existingEntity.sourceType = PackageRepoType.PYPI }
        verify { existingEntity.sourceUrl = "http://updated.com" }

        assertEquals(PackageRepoType.PYPI, sourceTypeSlot.captured)
        assertEquals("http://updated.com", sourceUrlSlot.captured)

        assertEquals(existingEntity, result)

        unmockkObject(PackageRepoDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        val dto = PackageRepoDto(id = 0L, sourceType = PackageRepoTypeDto.Pypi, sourceUrl = "http://new.com")

        val newEntity = mockk<PackageRepoDao>(relaxed = true)

        every { factory.create() } returns newEntity

        val sourceTypeSlot = slot<PackageRepoType>()
        val sourceUrlSlot = slot<String>()

        every { newEntity.sourceType = capture(sourceTypeSlot) } just runs
        every { newEntity.sourceUrl = capture(sourceUrlSlot) } just runs

        val result = mapper.toEntity(dto)

        verify { factory.create() }
        verify { newEntity.sourceType = PackageRepoType.PYPI }
        verify { newEntity.sourceUrl = "http://new.com" }

        assertEquals(PackageRepoType.PYPI, sourceTypeSlot.captured)
        assertEquals("http://new.com", sourceUrlSlot.captured)

        assertEquals(newEntity, result)
    }
}