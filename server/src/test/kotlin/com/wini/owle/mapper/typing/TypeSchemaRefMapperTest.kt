package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.dto.typing.TypeSchemaRefDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.typing.TypeSchemaRefDao
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
    private val factory: DaoFactory<TypeSchemaRefDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeSchemaRefsMapper(factory, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeSchemaRefDto(
            id = 99L,
            parentRef = null,
            name = "schemaName",
            typeDeclaration = mockk(),
            description = "description",
            childRefs = emptyList()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeSchemaRefDao)
        every { TypeSchemaRefDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeSchemaRef with id=99 not found", exception.message)
        unmockkObject(TypeSchemaRefDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeSchemaRefDao> {
            every { id.value } returns 1L
            every { parentRef } returns null
            every { name } returns "schemaName"
            every { typeDeclaration } returns mockk()
            every { description } returns "description"
        }

        // Mock the behavior of typeDeclarationsMapper
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        every { typeDeclarationsMapper.toDto(entity.typeDeclaration) } returns typeDeclarationDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(null, result.parentRef)
        assertEquals("schemaName", result.name)
        assertEquals(typeDeclarationDto, result.typeDeclaration)
        assertEquals("description", result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeSchemaRefDto(
            id = 42L,
            parentRef = null,
            name = "schemaName",
            typeDeclaration = mockk(),
            description = "description",
            childRefs = emptyList()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeSchemaRefDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationDao: TypeDeclarationDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(TypeSchemaRefDao)
        every { TypeSchemaRefDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val descriptionSlot = slot<String>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeSchemaRefDao.findById(42L) }
        verify { existingEntity.name = "schemaName" }
        verify { existingEntity.typeDeclaration = typeDeclarationDao }
        verify { existingEntity.description = "description" }

        // Verify the captured values
        assertEquals("schemaName", nameSlot.captured)
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeSchemaRefDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeSchemaRefDto(
            id = 0L,
            parentRef = null,
            name = "schemaName",
            typeDeclaration = mockk(),
            description = "description",
            childRefs = emptyList()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeSchemaRefDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationDao: TypeDeclarationDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val descriptionSlot = slot<String>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "schemaName" }
        verify { newEntity.typeDeclaration = typeDeclarationDao }
        verify { newEntity.description = "description" }

        // Verify the captured values
        assertEquals("schemaName", nameSlot.captured)
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}