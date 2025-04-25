package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.transforms.TransformArgEntity
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


open class TransformationArgMapperTest {

    private lateinit var mapper: TransformationArgsMapper
    private val transformationsMapper: TransformationsMapper = mockk()
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val factory: DaoFactory<TransformArgEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = TransformationArgsMapper(factory, transformationsMapper, typeDeclarationsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = TransformArg(
            id = 99L,
            transformation = mockk(),
            typeDeclaration = mockk(),
            name = "argName",
            description = "argDescription"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(TransformArgEntity)
        every { TransformArgEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("TransformationArg with id=99 not found", exception.message)
        unmockkObject(TransformArgEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<TransformArgEntity> {
            every { id.value } returns 1L
            every { transformation } returns mockk()
            every { typeDeclarationEntity } returns mockk()
            every { name } returns "argName"
            every { description } returns "argDescription"
        }

        // Mock the behavior of transformationsMapper and typeDeclarationsMapper
        val transform = mockk<Transform>()
        val typeDeclaration = mockk<TypeDeclaration>()
        every { transformationsMapper.toDto(entity.transformation) } returns transform
        every { typeDeclarationsMapper.toDto(entity.typeDeclarationEntity) } returns typeDeclaration

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(transform, result.transformation)
        assertEquals(typeDeclaration, result.typeDeclaration)
        assertEquals("argName", result.name)
        assertEquals("argDescription", result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = TransformArg(
            id = 42L,
            transformation = mockk(),
            typeDeclaration = mockk(),
            name = "argName",
            description = "argDescription"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<TransformArgEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val transformEntity: TransformEntity = mockk()
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(TransformArgEntity)
        every { TransformArgEntity.findById(42L) } returns existingEntity
        every { transformationsMapper.toEntity(dto.transformation) } returns transformEntity
        every { typeDeclarationsMapper.toEntity(dto.typeDeclaration) } returns typeDeclarationEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { TransformArgEntity.findById(42L) }
        verify { existingEntity.transformation = transformEntity }
        verify { existingEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { existingEntity.name = "argName" }
        verify { existingEntity.description = "argDescription" }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(TransformArgEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = TransformArg(
            id = 0L,
            transformation = mockk(),
            typeDeclaration = mockk(),
            name = "argName",
            description = "argDescription"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<TransformArgEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val transformEntity: TransformEntity = mockk()
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity
        every { transformationsMapper.toEntity(dto.transformation) } returns transformEntity
        every { typeDeclarationsMapper.toEntity(dto.typeDeclaration) } returns typeDeclarationEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.transformation = transformEntity }
        verify { newEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { newEntity.name = "argName" }
        verify { newEntity.description = "argDescription" }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}