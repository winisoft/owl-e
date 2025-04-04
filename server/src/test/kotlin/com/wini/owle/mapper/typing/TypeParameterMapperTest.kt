package com.wini.owle.mapper.typing

import com.wini.owle.entity.typing.TypeParameterDao
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
import com.wini.owle.dto.typing.TypeParameterDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import org.junit.jupiter.api.assertThrows


open class TypeParameterMapperTest {

    private lateinit var mapper: TypeParametersMapper
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TypeParameterDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TypeParametersMapper(factory, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TypeParameterDto(id = 99L, typeDeclaration = mockk(), position = 1, parameterTypeDeclaration = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(TypeParameterDao)
        every { TypeParameterDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TypeParameter with id=99 not found", exception.message)
        unmockkObject(TypeParameterDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TypeParameterDao> {
            every { id.value } returns 1L
            every { typeDeclaration } returns mockk()
            every { position } returns 1
            every { parameterTypeDeclaration } returns mockk()
        }

        // Mock the behavior of typeDeclarationsMapper
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        val parameterTypeDeclarationDto = mockk<TypeDeclarationDto>()
        every { typeDeclarationsMapper.toDto(entity.typeDeclaration) } returns typeDeclarationDto
        every { typeDeclarationsMapper.toDto(entity.parameterTypeDeclaration) } returns parameterTypeDeclarationDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(typeDeclarationDto, result.typeDeclaration)
        assertEquals(1, result.position)
        assertEquals(parameterTypeDeclarationDto, result.parameterTypeDeclaration)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TypeParameterDto(
            id = 42L,
            typeDeclaration = mockk(),
            position = 1,
            parameterTypeDeclaration = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TypeParameterDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationDao: TypeDeclarationDao = mockk()
        val parameterTypeDeclarationDao: TypeDeclarationDao = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returnsMany listOf(typeDeclarationDao, parameterTypeDeclarationDao)

        // Set up mocks for finding existing entity
        mockkObject(TypeParameterDao)
        every { TypeParameterDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val positionSlot = slot<Int>()
        val parameterTypeDeclarationSlot = slot<TypeDeclarationDao>()

        every { existingEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { existingEntity.position = capture(positionSlot) } just runs
        every { existingEntity.parameterTypeDeclaration = capture(parameterTypeDeclarationSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TypeParameterDao.findById(42L) }
        verify { existingEntity.typeDeclaration = typeDeclarationDao }
        verify { existingEntity.position = 1 }
        verify { existingEntity.parameterTypeDeclaration = parameterTypeDeclarationDao }

        // Verify the captured values
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals(1, positionSlot.captured)
        assertEquals(parameterTypeDeclarationDao, parameterTypeDeclarationSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TypeParameterDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TypeParameterDto(
            id = 0L,
            typeDeclaration = mockk(),
            position = 1,
            parameterTypeDeclaration = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TypeParameterDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationDao: TypeDeclarationDao = mockk()
        val parameterTypeDeclarationDao: TypeDeclarationDao = mockk()

        // Mock the behavior of typeDeclarationsMapper.toEntity
        every { typeDeclarationsMapper.toEntity(any()) } returnsMany listOf(typeDeclarationDao, parameterTypeDeclarationDao)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val positionSlot = slot<Int>()
        val parameterTypeDeclarationSlot = slot<TypeDeclarationDao>()

        every { newEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { newEntity.position = capture(positionSlot) } just runs
        every { newEntity.parameterTypeDeclaration = capture(parameterTypeDeclarationSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.typeDeclaration = typeDeclarationDao }
        verify { newEntity.position = 1 }
        verify { newEntity.parameterTypeDeclaration = parameterTypeDeclarationDao }

        // Verify the captured values
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals(1, positionSlot.captured)
        assertEquals(parameterTypeDeclarationDao, parameterTypeDeclarationSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}