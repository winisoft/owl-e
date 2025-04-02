package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.transforms.TransformationArgDao
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


open class TransformationArgMapperTest {

    private lateinit var mapper: TransformationArgsMapper
    private val transformationsMapper: TransformationsMapper = mockk()
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TransformationArgDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TransformationArgsMapper(factory, transformationsMapper, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TransformationArgDto(
            id = 99L,
            transformation = mockk(),
            typeDeclaration = mockk(),
            name = "argName",
            description = "argDescription"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TransformationArgDao)
        every { TransformationArgDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TransformationArg with id=99 not found", exception.message)
        unmockkObject(TransformationArgDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TransformationArgDao> {
            every { id.value } returns 1L
            every { transformation } returns mockk()
            every { typeDeclaration } returns mockk()
            every { name } returns "argName"
            every { description } returns "argDescription"
        }

        // Mock the behavior of transformationsMapper and typeDeclarationsMapper
        val transformationDto = mockk<TransformationDto>()
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        every { transformationsMapper.toDto(entity.transformation) } returns transformationDto
        every { typeDeclarationsMapper.toDto(entity.typeDeclaration) } returns typeDeclarationDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(transformationDto, result.transformation)
        assertEquals(typeDeclarationDto, result.typeDeclaration)
        assertEquals("argName", result.name)
        assertEquals("argDescription", result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TransformationArgDto(
            id = 42L,
            transformation = mockk(),
            typeDeclaration = mockk(),
            name = "argName",
            description = "argDescription"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TransformationArgDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(TransformationArgDao)
        every { TransformationArgDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val transformationSlot = slot<Transformation>()
        val typeDeclarationSlot = slot<TypeDeclaration>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { existingEntity.transformation = capture(transformationSlot) } just runs
        every { existingEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TransformationArgDao.findById(42L) }
        verify { existingEntity.transformation = dto.transformation }
        verify { existingEntity.typeDeclaration = dto.typeDeclaration }
        verify { existingEntity.name = "argName" }
        verify { existingEntity.description = "argDescription" }

        // Verify the captured values
        assertEquals(dto.transformation, transformationSlot.captured)
        assertEquals(dto.typeDeclaration, typeDeclarationSlot.captured)
        assertEquals("argName", nameSlot.captured)
        assertEquals("argDescription", descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TransformationArgDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TransformationArgDto(
            id = 0L,
            transformation = mockk(),
            typeDeclaration = mockk(),
            name = "argName",
            description = "argDescription"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TransformationArgDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val transformationSlot = slot<Transformation>()
        val typeDeclarationSlot = slot<TypeDeclaration>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { newEntity.transformation = capture(transformationSlot) } just runs
        every { newEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.transformation = dto.transformation }
        verify { newEntity.typeDeclaration = dto.typeDeclaration }
        verify { newEntity.name = "argName" }
        verify { newEntity.description = "argDescription" }

        // Verify the captured values
        assertEquals(dto.transformation, transformationSlot.captured)
        assertEquals(dto.typeDeclaration, typeDeclarationSlot.captured)
        assertEquals("argName", nameSlot.captured)
        assertEquals("argDescription", descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}