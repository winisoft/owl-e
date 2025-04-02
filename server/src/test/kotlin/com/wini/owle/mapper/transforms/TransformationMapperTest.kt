package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.transforms.TransformationDao
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


open class TransformationMapperTest {

    private lateinit var mapper: TransformationsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TransformationDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TransformationsMapper(factory, typeDeclarationMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TransformationDto(
            id = 99L,
            name = "transformationName",
            description = "transformationDescription",
            examples = "example",
            returnsType = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TransformationDao)
        every { TransformationDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Transformation with id=99 not found", exception.message)
        unmockkObject(TransformationDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TransformationDao> {
            every { id.value } returns 1L
            every { name } returns "transformationName"
            every { description } returns "transformationDescription"
            every { examples } returns "example"
            every { returnsTypeDeclaration } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper
        val returnsTypeDto = mockk<TypeDeclarationDto>()
        every { typeDeclarationMapper.toDto(entity.returnsTypeDeclaration!!) } returns returnsTypeDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals("transformationName", result.name)
        assertEquals("transformationDescription", result.description)
        assertEquals("example", result.examples)
        assertEquals(returnsTypeDto, result.returnsType)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TransformationDto(
            id = 42L,
            name = "transformationName",
            description = "transformationDescription",
            examples = "example",
            returnsType = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TransformationDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(TransformationDao)
        every { TransformationDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val examplesSlot = slot<String>()
        val returnsTypeSlot = slot<TypeDeclaration>()

        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs
        every { existingEntity.examples = capture(examplesSlot) } just runs
        every { existingEntity.returnsTypeDeclaration = capture(returnsTypeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TransformationDao.findById(42L) }
        verify { existingEntity.name = "transformationName" }
        verify { existingEntity.description = "transformationDescription" }
        verify { existingEntity.examples = "example" }
        verify { existingEntity.returnsTypeDeclaration = dto.returnsType }

        // Verify the captured values
        assertEquals("transformationName", nameSlot.captured)
        assertEquals("transformationDescription", descriptionSlot.captured)
        assertEquals("example", examplesSlot.captured)
        assertEquals(dto.returnsType, returnsTypeSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TransformationDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TransformationDto(id = 0L, name = "transformationName", description = "transformationDescription", examples = "example", returnsType = mockk())

        // Mock the new entity that will be created
        val newEntity = mockk<TransformationDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()
        val examplesSlot = slot<String>()
        val returnsTypeSlot = slot<TypeDeclaration>()

        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs
        every { newEntity.examples = capture(examplesSlot) } just runs
        every { newEntity.returnsTypeDeclaration = capture(returnsTypeSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "transformationName" }
        verify { newEntity.description = "transformationDescription" }
        verify { newEntity.examples = "example" }
        verify { newEntity.returnsTypeDeclaration = dto.returnsType }

        // Verify the captured values
        assertEquals("transformationName", nameSlot.captured)
        assertEquals("transformationDescription", descriptionSlot.captured)
        assertEquals("example", examplesSlot.captured)
        assertEquals(dto.returnsType, returnsTypeSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}