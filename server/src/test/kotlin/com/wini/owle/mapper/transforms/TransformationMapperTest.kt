package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class TransformationMapperTest {

    private lateinit var mapper: TransformationsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TransformEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TransformationsMapper(factory, typeDeclarationMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = Transform(
            id = 99L,
            name = "transformationName",
            description = "transformationDescription",
            examples = "example",
            returnsType = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TransformEntity)
        every { TransformEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Transformation with id=99 not found", exception.message)
        unmockkObject(TransformEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TransformEntity> {
            every { id.value } returns 1L
            every { name } returns "transformationName"
            every { description } returns "transformationDescription"
            every { examples } returns "example"
            every { returnsTypeDeclarationEntity } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper
        val returnsTypeDto = mockk<TypeDeclaration>()
        every { typeDeclarationMapper.toDto(entity.returnsTypeDeclarationEntity!!) } returns returnsTypeDto

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
        val returnsTypeDto = mockk<TypeDeclaration>()
        val dto = Transform(
            id = 42L,
            name = "transformationName",
            description = "transformationDescription",
            examples = "example",
            returnsType = returnsTypeDto
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TransformEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(TransformEntity)
        every { TransformEntity.findById(42L) } returns existingEntity
        every { typeDeclarationMapper.toEntity(returnsTypeDto) } returns typeDeclarationEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TransformEntity.findById(42L) }
        verify { existingEntity.name = "transformationName" }
        verify { existingEntity.description = "transformationDescription" }
        verify { existingEntity.examples = "example" }
        verify { existingEntity.returnsTypeDeclarationEntity = typeDeclarationEntity }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TransformEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val returnsTypeDto = mockk<TypeDeclaration>()
        val dto = Transform(
            id = 0L,
            name = "transformationName",
            description = "transformationDescription",
            examples = "example",
            returnsType = returnsTypeDto
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TransformEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity
        every { typeDeclarationMapper.toEntity(returnsTypeDto) } returns typeDeclarationEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.name = "transformationName" }
        verify { newEntity.description = "transformationDescription" }
        verify { newEntity.examples = "example" }
        verify { newEntity.returnsTypeDeclarationEntity = typeDeclarationEntity }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}