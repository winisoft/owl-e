package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.dto.typing.TypeVarianceDto
import com.wini.owle.dto.typing.VarianceCardinalityDto
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.entity.typing.enums.VarianceCardinality
import com.wini.owle.mapper.DaoFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.slot
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class TypeVariancesMapperTest {

    private lateinit var mapper: TypeVariancesMapper
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TypeVarianceDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeVariancesMapper(factory, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeVarianceDto(
            id = 99L,
            typeVariableDeclaration = mockk(),
            varianceCardinality = VarianceCardinalityDto.Covariant
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeVarianceDao)
        every { TypeVarianceDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeVariance with id=99 not found", exception.message)
        unmockkObject(TypeVarianceDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeVarianceDao> {
            every { id.value } returns 1L
            every { typeVariableDeclaration } returns mockk()
            every { varianceCardinality } returns VarianceCardinality.COVARIANT
        }

        // Mock the behavior of typeDeclarationsMapper
        val typeVariableDeclarationDto = mockk<TypeDeclarationDto>()
        every { typeDeclarationsMapper.toDto(entity.typeVariableDeclaration) } returns typeVariableDeclarationDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(typeVariableDeclarationDto, result.typeVariableDeclaration)
        assertEquals(VarianceCardinalityDto.Covariant, result.varianceCardinality)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeVarianceDto(id = 42L, typeVariableDeclaration = mockk(), varianceCardinality = VarianceCardinalityDto.Covariant)

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeVarianceDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val varianceCardinality: VarianceCardinality = mockk()
        val typeVariableDeclaration: TypeDeclarationDao = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returns typeVariableDeclaration

        // Set up mocks for finding existing entity
        mockkObject(TypeVarianceDao)
        every { TypeVarianceDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val typeVariableDeclarationSlot = slot<TypeDeclarationDao>()
        val varianceCardinalitySlot = slot<VarianceCardinality>()

        every { existingEntity.typeVariableDeclaration = capture(typeVariableDeclarationSlot) } just runs
        every { existingEntity.varianceCardinality = capture(varianceCardinalitySlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeVarianceDao.findById(42L) }
        verify { existingEntity.typeVariableDeclaration = typeVariableDeclaration }
        verify { existingEntity.varianceCardinality = VarianceCardinality.COVARIANT }

        // Verify the captured values
        assertEquals(typeVariableDeclaration, typeVariableDeclarationSlot.captured)
        assertEquals(VarianceCardinality.COVARIANT, varianceCardinalitySlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeVarianceDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeVarianceDto(
            id = 0L,
            typeVariableDeclaration = mockk(),
            varianceCardinality = VarianceCardinalityDto.Covariant
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeVarianceDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeVariableDeclaration: TypeDeclarationDao = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returns typeVariableDeclaration

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val typeVariableDeclarationSlot = slot<TypeDeclarationDao>()
        val varianceCardinalitySlot = slot<VarianceCardinality>()

        every { newEntity.typeVariableDeclaration = capture(typeVariableDeclarationSlot) } just runs
        every { newEntity.varianceCardinality = capture(varianceCardinalitySlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.typeVariableDeclaration = typeVariableDeclaration }
        verify { newEntity.varianceCardinality = VarianceCardinality.COVARIANT }

        // Verify the captured values
        assertEquals(typeVariableDeclaration, typeVariableDeclarationSlot.captured)
        assertEquals(VarianceCardinality.COVARIANT, varianceCardinalitySlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}