package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.ToolParam
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.entity.workflows.ToolParamEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class ToolParamMapperTest {

    private lateinit var mapper: ToolParamsMapper
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val toolMapper: ToolsMapper = mockk()
    private val factory: DaoFactory<ToolParamEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ToolParamsMapper(factory, typeDeclarationsMapper, toolMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ToolParam(
            id = 99L,
            name = "paramName",
            hasDefault = true,
            typeDeclaration = mockk(),
            tool = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ToolParamEntity)
        every { ToolParamEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ToolParam with id=99 not found", exception.message)
        unmockkObject(ToolParamEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ToolParamEntity> {
            every { id.value } returns 1L
            every { name } returns "paramName"
            every { hasDefault } returns true
            every { typeDeclarationEntity } returns mockk()
            every { tool } returns mockk()
        }

        // Mock toDto behavior of the mappers
        val typeDeclaration = mockk<TypeDeclaration>()
        val tool = mockk<Tool>()
        every { typeDeclarationsMapper.toDto(entity.typeDeclarationEntity) } returns typeDeclaration
        every { toolMapper.toDto(entity.tool) } returns tool

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("paramName", result.name)
        assertEquals(true, result.hasDefault)
        assertEquals(typeDeclaration, result.typeDeclaration)
        assertEquals(tool, result.tool)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ToolParam(
            id = 42L,
            name = "paramName",
            hasDefault = true,
            typeDeclaration = mockk(),
            tool = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ToolParamEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()
        val toolEntity: ToolEntity = mockk()

        every { typeDeclarationsMapper.toEntity(any()) } returns typeDeclarationEntity
        every { toolMapper.toEntity(any()) } returns toolEntity

        // Set up mocks for finding existing entity
        mockkObject(ToolParamEntity)
        every { ToolParamEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val hasDefaultSlot = slot<Boolean>()
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val toolSlot = slot<ToolEntity>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.hasDefault = capture(hasDefaultSlot) } just runs
        every { existingEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { existingEntity.tool = capture(toolSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ToolParamEntity.findById(42L) }
        verify { existingEntity.name = "paramName" }
        verify { existingEntity.hasDefault = true }
        verify { existingEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { existingEntity.tool = toolEntity }

        // Verify the captured values
        assertEquals("paramName", nameSlot.captured)
        assertEquals(true, hasDefaultSlot.captured)
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals(toolEntity, toolSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ToolParamEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ToolParam(
            id = 0L,
            name = "paramName",
            hasDefault = true,
            typeDeclaration = mockk(),
            tool = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ToolParamEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()
        val toolEntity: ToolEntity = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity and toolMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returns typeDeclarationEntity
        every { toolMapper.toEntity(any()) } returns toolEntity
        
        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val hasDefaultSlot = slot<Boolean>()
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val toolSlot = slot<ToolEntity>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.hasDefault = capture(hasDefaultSlot) } just runs
        every { newEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { newEntity.tool = capture(toolSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "paramName" }
        verify { newEntity.hasDefault = true }
        verify { newEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { newEntity.tool = toolEntity }

        // Verify the captured values
        assertEquals("paramName", nameSlot.captured)
        assertEquals(true, hasDefaultSlot.captured)
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals(toolEntity, toolSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}