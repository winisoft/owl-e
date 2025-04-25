package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.entity.typing.TypeDefinitionEntity
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


open class TypeDeclarationEntityMapperTest {

    private lateinit var mapper: TypeDeclarationsMapper
    private val typeDefinitionsMapper: TypeDefinitionsMapper = mockk()
    private val factory: DaoFactory<TypeDeclarationEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeDeclarationsMapper(factory, typeDefinitionsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeDeclaration(id = 99L, typeDefinition = mockk(), alias = "aliasName")

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeDeclarationEntity)
        every { TypeDeclarationEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeDeclaration with id=99 not found", exception.message)
        unmockkObject(TypeDeclarationEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeDeclarationEntity> {
            every { id.value } returns 1L
            every { typeDefinitionEntity } returns mockk()
            every { alias } returns "aliasName"
        }

        // Mock the behavior of typeDefinitionsMapper
        val typeDefinitionDto = mockk<TypeDefinition>()
        every { typeDefinitionsMapper.toDto(entity.typeDefinitionEntity) } returns typeDefinitionDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(typeDefinitionDto, result.typeDefinition)
        assertEquals("aliasName", result.alias)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeDeclaration(id = 42L, typeDefinition = mockk(), alias = "aliasName")

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeDeclarationEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDefinitionEntity: TypeDefinitionEntity = mockk()

        // Mock the behavior of typeDefinitionsMapper.toEntity
        every { typeDefinitionsMapper.toEntity(any()) } returns typeDefinitionEntity

        // Set up mocks for finding existing entity
        mockkObject(TypeDeclarationEntity)
        every { TypeDeclarationEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val typeDefinitionEntitySlot = slot<TypeDefinitionEntity>()
        val aliasSlot = slot<String>()

        every { existingEntity.typeDefinitionEntity = capture(typeDefinitionEntitySlot) } just runs
        every { existingEntity.alias = capture(aliasSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeDeclarationEntity.findById(42L) }
        verify { existingEntity.typeDefinitionEntity = typeDefinitionEntity }
        verify { existingEntity.alias = "aliasName" }

        // Verify the captured values
        assertEquals(typeDefinitionEntity, typeDefinitionEntitySlot.captured)
        assertEquals("aliasName", aliasSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeDeclarationEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeDeclaration(id = 0L, typeDefinition = mockk(), alias = "aliasName")

        // Mock the new entity that will be created
        val newEntity = mockk<TypeDeclarationEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDefinitionEntity: TypeDefinitionEntity = mockk()

        // Mock the behavior of typeDefinitionsMapper.toEntity
        every { typeDefinitionsMapper.toEntity(any()) } returns typeDefinitionEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val typeDefinitionEntitySlot = slot<TypeDefinitionEntity>()
        val aliasSlot = slot<String>()

        every { newEntity.typeDefinitionEntity = capture(typeDefinitionEntitySlot) } just runs
        every { newEntity.alias = capture(aliasSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.typeDefinitionEntity = typeDefinitionEntity }
        verify { newEntity.alias = "aliasName" }

        // Verify the captured values
        assertEquals(typeDefinitionEntity, typeDefinitionEntitySlot.captured)
        assertEquals("aliasName", aliasSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}