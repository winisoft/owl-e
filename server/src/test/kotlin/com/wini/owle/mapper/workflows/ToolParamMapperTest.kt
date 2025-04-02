package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.entity.workflows.ToolParamDao
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
    private val factory: DaoFactory<ToolParamDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ToolParamsMapper(factory, typeDeclarationsMapper, toolMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ToolParamDto(
            id = 99L,
            name = "paramName",
            hasDefault = true,
            typeDeclaration = mockk(),
            tool = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ToolParamDao)
        every { ToolParamDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("ToolParam with id=99 not found", exception.message)
        unmockkObject(ToolParamDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<ToolParamDao> {
            every { id.value } returns 1L
            every { name } returns "paramName"
            every { hasDefault } returns true
            every { typeDeclaration } returns mockk()
            every { tool } returns mockk()
        }

        // Mock toDto behavior of the mappers
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        val toolDto = mockk<ToolDto>()
        every { typeDeclarationsMapper.toDto(entity.typeDeclaration) } returns typeDeclarationDto
        every { toolMapper.toDto(entity.tool) } returns toolDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("paramName", result.name)
        assertEquals(true, result.hasDefault)
        assertEquals(typeDeclarationDto, result.typeDeclaration)
        assertEquals(toolDto, result.tool)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ToolParamDto(
            id = 42L,
            name = "paramName",
            hasDefault = true,
            typeDeclaration = mockk(),
            tool = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ToolParamDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationDao: TypeDeclarationDao = mockk()
        val toolDao: ToolDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(ToolParamDao)
        every { ToolParamDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val hasDefaultSlot = slot<Boolean>()
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val toolSlot = slot<ToolDao>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.hasDefault = capture(hasDefaultSlot) } just runs
        every { existingEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { existingEntity.tool = capture(toolSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ToolParamDao.findById(42L) }
        verify { existingEntity.name = "paramName" }
        verify { existingEntity.hasDefault = true }
        verify { existingEntity.typeDeclaration = typeDeclarationDao }
        verify { existingEntity.tool = toolDao }

        // Verify the captured values
        assertEquals("paramName", nameSlot.captured)
        assertEquals(true, hasDefaultSlot.captured)
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals(toolDao, toolSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ToolParamDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ToolParamDto(
            id = 0L,
            name = "paramName",
            hasDefault = true,
            typeDeclaration = mockk(),
            tool = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ToolParamDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationDao: TypeDeclarationDao = mockk()
        val toolDao: ToolDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val hasDefaultSlot = slot<Boolean>()
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val toolSlot = slot<ToolDao>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.hasDefault = capture(hasDefaultSlot) } just runs
        every { newEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { newEntity.tool = capture(toolSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "paramName" }
        verify { newEntity.hasDefault = true }
        verify { newEntity.typeDeclaration = typeDeclarationDao }
        verify { newEntity.tool = toolDao }

        // Verify the captured values
        assertEquals("paramName", nameSlot.captured)
        assertEquals(true, hasDefaultSlot.captured)
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals(toolDao, toolSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}