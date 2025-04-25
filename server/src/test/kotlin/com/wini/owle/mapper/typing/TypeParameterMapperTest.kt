package com.wini.owle.mapper.typing

import com.wini.owle.entity.typing.TypeParameterEntity
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
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.typing.TypeDeclarationEntity
import org.junit.jupiter.api.assertThrows


open class TypeParameterMapperTest {

    private lateinit var mapper: TypeParametersMapper
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TypeParameterEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeParametersMapper(factory, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeParameter(id = 99L, typeDeclaration = mockk(), position = 1, parameterTypeDeclaration = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeParameterEntity)
        every { TypeParameterEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeParameter with id=99 not found", exception.message)
        unmockkObject(TypeParameterEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeParameterEntity> {
            every { id.value } returns 1L
            every { typeDeclarationEntity } returns mockk()
            every { position } returns 1
            every { parameterTypeDeclarationEntity } returns mockk()
        }

        // Mock the behavior of typeDeclarationsMapper
        val typeDeclaration = mockk<TypeDeclaration>()
        val parameterTypeDeclaration = mockk<TypeDeclaration>()
        every { typeDeclarationsMapper.toDto(entity.typeDeclarationEntity) } returns typeDeclaration
        every { typeDeclarationsMapper.toDto(entity.parameterTypeDeclarationEntity) } returns parameterTypeDeclaration

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(typeDeclaration, result.typeDeclaration)
        assertEquals(1, result.position)
        assertEquals(parameterTypeDeclaration, result.parameterTypeDeclaration)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeParameter(
            id = 42L,
            typeDeclaration = mockk(),
            position = 1,
            parameterTypeDeclaration = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeParameterEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()
        val parameterTypeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returnsMany listOf(typeDeclarationEntity, parameterTypeDeclarationEntity)

        // Set up mocks for finding existing entity
        mockkObject(TypeParameterEntity)
        every { TypeParameterEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val positionSlot = slot<Int>()
        val parameterTypeDeclarationEntitySlot = slot<TypeDeclarationEntity>()

        every { existingEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { existingEntity.position = capture(positionSlot) } just runs
        every { existingEntity.parameterTypeDeclarationEntity = capture(parameterTypeDeclarationEntitySlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeParameterEntity.findById(42L) }
        verify { existingEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { existingEntity.position = 1 }
        verify { existingEntity.parameterTypeDeclarationEntity = parameterTypeDeclarationEntity }

        // Verify the captured values
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals(1, positionSlot.captured)
        assertEquals(parameterTypeDeclarationEntity, parameterTypeDeclarationEntitySlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeParameterEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeParameter(
            id = 0L,
            typeDeclaration = mockk(),
            position = 1,
            parameterTypeDeclaration = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeParameterEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()
        val parameterTypeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returnsMany listOf(typeDeclarationEntity, parameterTypeDeclarationEntity)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val positionSlot = slot<Int>()
        val parameterTypeDeclarationEntitySlot = slot<TypeDeclarationEntity>()

        every { newEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { newEntity.position = capture(positionSlot) } just runs
        every { newEntity.parameterTypeDeclarationEntity = capture(parameterTypeDeclarationEntitySlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { newEntity.position = 1 }
        verify { newEntity.parameterTypeDeclarationEntity = parameterTypeDeclarationEntity }

        // Verify the captured values
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals(1, positionSlot.captured)
        assertEquals(parameterTypeDeclarationEntity, parameterTypeDeclarationEntitySlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}