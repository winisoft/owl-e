package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.entity.typing.TypeSchemaRefEntity
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


open class TypeSchemaRefMapperTest {

    private lateinit var mapper: TypeSchemaRefsMapper
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TypeSchemaRefEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeSchemaRefsMapper(factory, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeSchemaRef(
            id = 99L,
            parentRef = null,
            name = "schemaName",
            typeDeclaration = mockk(),
            description = "description",
            childRefs = emptyList()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeSchemaRefEntity)
        every { TypeSchemaRefEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeSchemaRef with id=99 not found", exception.message)
        unmockkObject(TypeSchemaRefEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeSchemaRefEntity> {
            every { id.value } returns 1L
            every { parentRef } returns null
            every { name } returns "schemaName"
            every { typeDeclarationEntity } returns mockk()
            every { description } returns "description"
        }

        // Mock the behavior of typeDeclarationsMapper
        val typeDeclaration = mockk<TypeDeclaration>()
        every { typeDeclarationsMapper.toDto(entity.typeDeclarationEntity) } returns typeDeclaration

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(null, result.parentRef)
        assertEquals("schemaName", result.name)
        assertEquals(typeDeclaration, result.typeDeclaration)
        assertEquals("description", result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeSchemaRef(
            id = 42L,
            parentRef = null,
            name = "schemaName",
            typeDeclaration = mockk(),
            description = "description",
            childRefs = emptyList()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeSchemaRefEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returns typeDeclarationEntity

        // Set up mocks for finding existing entity
        mockkObject(TypeSchemaRefEntity)
        every { TypeSchemaRefEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val descriptionSlot = slot<String>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeSchemaRefEntity.findById(42L) }
        verify { existingEntity.name = "schemaName" }
        verify { existingEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { existingEntity.description = "description" }

        // Verify the captured values
        assertEquals("schemaName", nameSlot.captured)
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeSchemaRefEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeSchemaRef(
            id = 0L,
            parentRef = null,
            name = "schemaName",
            typeDeclaration = mockk(),
            description = "description",
            childRefs = emptyList()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeSchemaRefEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returns typeDeclarationEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val descriptionSlot = slot<String>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "schemaName" }
        verify { newEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { newEntity.description = "description" }

        // Verify the captured values
        assertEquals("schemaName", nameSlot.captured)
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}