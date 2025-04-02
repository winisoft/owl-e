package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
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


open class ToolMapperTest {

    private lateinit var mapper: ToolsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val toolParamMapper: ToolParamsMapper = mockk()
    private val factory: DaoFactory<ToolDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ToolsMapper(factory, typeDeclarationMapper, toolParamMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ToolDto(
            id = 99L,
            name = "toolName",
            description = "description",
            body = "body",
            returnTypeDeclaration = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ToolDao)
        every { ToolDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Tool with id=99 not found", exception.message)
        unmockkObject(ToolDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ToolDao> {
            every { id.value } returns 1L
            every { name } returns "toolName"
            every { description } returns "description"
            every { body } returns "body"
            every { returnTypeDeclaration } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper
        val returnTypeDeclarationDto = mockk<TypeDeclarationDto>()
        every { typeDeclarationMapper.toDto(entity.returnTypeDeclaration) } returns returnTypeDeclarationDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("toolName", result.name)
        assertEquals("description", result.description)
        assertEquals("body", result.body)
        assertEquals(returnTypeDeclarationDto, result.returnTypeDeclaration)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ToolDto(
            id = 42L,
            name = "toolName",
            description = "description",
            body = "body",
            returnTypeDeclaration = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ToolDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(ToolDao)
        every { ToolDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val bodySlot = slot<String>()
        val returnTypeDeclarationSlot = slot<TypeDeclarations>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.body = capture(bodySlot) } just runs
        every { existingEntity.returnTypeDeclaration = capture(returnTypeDeclarationSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ToolDao.findById(42L) }
        verify { existingEntity.name = "toolName" }
        verify { existingEntity.description = "description" }
        verify { existingEntity.body = "body" }
        verify { existingEntity.returnTypeDeclaration = dto.returnTypeDeclaration }

        // Verify the captured values
        assertEquals("toolName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals("body", bodySlot.captured)
        assertEquals(dto.returnTypeDeclaration, returnTypeDeclarationSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ToolDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ToolDto(
            id = 0L,
            name = "toolName",
            description = "description",
            body = "body",
            returnTypeDeclaration = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ToolDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val bodySlot = slot<String>()
        val returnTypeDeclarationSlot = slot<TypeDeclarations>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.body = capture(bodySlot) } just runs
        every { newEntity.returnTypeDeclaration = capture(returnTypeDeclarationSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "toolName" }
        verify { newEntity.description = "description" }
        verify { newEntity.body = "body" }
        verify { newEntity.returnTypeDeclaration = dto.returnTypeDeclaration }

        // Verify the captured values
        assertEquals("toolName", nameSlot.captured)
        assertEquals("description", descriptionSlot.captured)
        assertEquals("body", bodySlot.captured)
        assertEquals(dto.returnTypeDeclaration, returnTypeDeclarationSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}