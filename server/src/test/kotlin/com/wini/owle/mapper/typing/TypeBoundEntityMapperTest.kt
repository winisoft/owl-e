package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeBound
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.typing.TypeBoundEntity
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.mapper.DaoFactory
import io.mockk.mockk
import io.mockk.every
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


open class TypeBoundEntityMapperTest {

    private lateinit var mapper: TypeBoundsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TypeBoundEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeBoundsMapper(factory, typeDeclarationMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeBound(
            id = 99L,
            typeVariableDeclaration = mockk(),
            boundTypeDeclaration = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeBoundEntity)
        every { TypeBoundEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeBound with id=99 not found", exception.message)
        unmockkObject(TypeBoundEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeBoundEntity> {
            every { id.value } returns 1L
            every { typeVariableDeclaration } returns mockk()
            every { boundTypeDeclarationEntity } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper
        val typeVariableDto = mockk<TypeDeclaration>()
        val boundTypeDto = mockk<TypeDeclaration>()
        every { typeDeclarationMapper.toDto(entity.typeVariableDeclaration) } returns typeVariableDto
        every { typeDeclarationMapper.toDto(entity.boundTypeDeclarationEntity) } returns boundTypeDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(typeVariableDto, result.typeVariableDeclaration)
        assertEquals(boundTypeDto, result.boundTypeDeclaration)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeBound(id = 42L, typeVariableDeclaration = mockk(), boundTypeDeclaration = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeBoundEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeVariableDeclarationDao: TypeDeclarationEntity = mockk()
        val boundTypeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Mock the behavior of typeDeclarationMapper.toEntity
        every { typeDeclarationMapper.toEntity(any()) } returnsMany listOf(typeVariableDeclarationDao, boundTypeDeclarationEntity)

        // Set up mocks for finding existing entity
        mockkObject(TypeBoundEntity)
        every { TypeBoundEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val typeVariableSlot = slot<TypeDeclarationEntity>()
        val boundTypeSlot = slot<TypeDeclarationEntity>()

        every { existingEntity.typeVariableDeclaration = capture(typeVariableSlot) } just runs
        every { existingEntity.boundTypeDeclarationEntity = capture(boundTypeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeBoundEntity.findById(42L) }
        verify { existingEntity.typeVariableDeclaration = typeVariableDeclarationDao }
        verify { existingEntity.boundTypeDeclarationEntity = boundTypeDeclarationEntity }

        // Verify the captured values
        assertEquals(typeVariableDeclarationDao, typeVariableSlot.captured)
        assertEquals(boundTypeDeclarationEntity, boundTypeSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeBoundEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeBound(
            id = 0L,
            typeVariableDeclaration = mockk(),
            boundTypeDeclaration = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeBoundEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeVariableDeclarationDao: TypeDeclarationEntity = mockk()
        val boundTypeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Mock the behavior of typeDeclarationMapper.toEntity
        every { typeDeclarationMapper.toEntity(any()) } returnsMany listOf(typeVariableDeclarationDao, boundTypeDeclarationEntity)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val typeVariableSlot = slot<TypeDeclarationEntity>()
        val boundTypeSlot = slot<TypeDeclarationEntity>()

        every { newEntity.typeVariableDeclaration = capture(typeVariableSlot) } just runs
        every { newEntity.boundTypeDeclarationEntity = capture(boundTypeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.typeVariableDeclaration = typeVariableDeclarationDao }
        verify { newEntity.boundTypeDeclarationEntity = boundTypeDeclarationEntity }

        // Verify the captured values
        assertEquals(typeVariableDeclarationDao, typeVariableSlot.captured)
        assertEquals(boundTypeDeclarationEntity, boundTypeSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}