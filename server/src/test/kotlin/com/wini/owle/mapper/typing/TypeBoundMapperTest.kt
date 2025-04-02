package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeBoundsDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.typing.TypeBoundDao
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


open class TypeBoundMapperTest {

    private lateinit var mapper: TypeBoundsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TypeBoundDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeBoundsMapper(factory, typeDeclarationMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeBoundsDto(
            id = 99L,
            typeVariableDeclaration = mockk(),
            boundTypeDeclaration = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeBoundDao)
        every { TypeBoundDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeBound with id=99 not found", exception.message)
        unmockkObject(TypeBoundDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeBoundDao> {
            every { id.value } returns 1L
            every { typeVariableDeclaration } returns mockk()
            every { boundTypeDeclaration } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper
        val typeVariableDto = mockk<TypeDeclarationDto>()
        val boundTypeDto = mockk<TypeDeclarationDto>()
        every { typeDeclarationMapper.toDto(entity.typeVariableDeclaration) } returns typeVariableDto
        every { typeDeclarationMapper.toDto(entity.boundTypeDeclaration) } returns boundTypeDto

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
        val dto = TypeBoundsDto(id = 42L, typeVariableDeclaration = mockk(), boundTypeDeclaration = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeBoundDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(TypeBoundDao)
        every { TypeBoundDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val typeVariableSlot = slot<TypeDeclaration>()
        val boundTypeSlot = slot<TypeDeclaration>()

        every { existingEntity.typeVariableDeclaration = capture(typeVariableSlot) } just runs
        every { existingEntity.boundTypeDeclaration = capture(boundTypeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeBoundDao.findById(42L) }
        verify { existingEntity.typeVariableDeclaration = dto.typeVariableDeclaration }
        verify { existingEntity.boundTypeDeclaration = dto.boundTypeDeclaration }

        // Verify the captured values
        assertEquals(dto.typeVariableDeclaration, typeVariableSlot.captured)
        assertEquals(dto.boundTypeDeclaration, boundTypeSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeBoundDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeBoundsDto(
            id = 0L,
            typeVariableDeclaration = mockk(),
            boundTypeDeclaration = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeBoundDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val typeVariableSlot = slot<TypeDeclaration>()
        val boundTypeSlot = slot<TypeDeclaration>()

        every { newEntity.typeVariableDeclaration = capture(typeVariableSlot) } just runs
        every { newEntity.boundTypeDeclaration = capture(boundTypeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.typeVariableDeclaration = dto.typeVariableDeclaration }
        verify { newEntity.boundTypeDeclaration = dto.boundTypeDeclaration }

        // Verify the captured values
        assertEquals(dto.typeVariableDeclaration, typeVariableSlot.captured)
        assertEquals(dto.boundTypeDeclaration, boundTypeSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}