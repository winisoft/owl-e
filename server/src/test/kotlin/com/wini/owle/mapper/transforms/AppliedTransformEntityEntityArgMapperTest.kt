package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.dto.transforms.AppliedTransform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.entity.transforms.AppliedTransformArgEntity
import com.wini.owle.entity.transforms.AppliedTransformEntity
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.mapper.DaoFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class AppliedTransformEntityEntityArgMapperTest {

    private lateinit var mapper: AppliedTransformArgsMapper
    private val appliedTransformsMapper: AppliedTransformsMapper = mockk()
    private val transformationArgsMapper: TransformationArgsMapper = mockk()
    private val factory: DaoFactory<AppliedTransformArgEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = AppliedTransformArgsMapper(factory, appliedTransformsMapper, transformationArgsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = AppliedTransformArg(id = 99L, appliedTransform = mockk(), transformationArg = mockk(), value = "testValue")

        // Mock the behavior of findById to return null (not found)
        mockkObject(AppliedTransformArgEntity)
        every { AppliedTransformArgEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("AppliedTransformArg with id=99 not found", exception.message)
        unmockkObject(AppliedTransformArgEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<AppliedTransformArgEntity> {
            every { id.value } returns 1L
            every { appliedTransformEntity } returns mockk()
            every { transformationArg } returns mockk()
            every { value } returns "testValue"
        }

        // Mock the behavior of appliedTransformsMapper and transformationArgsMapper
        val appliedTransform = mockk<AppliedTransform>()
        val transformationArgDto = mockk<TransformArg>()
        every { appliedTransformsMapper.toDto(entity.appliedTransformEntity) } returns appliedTransform
        every { transformationArgsMapper.toDto(entity.transformationArg) } returns transformationArgDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(appliedTransform, result.appliedTransform)
        assertEquals(transformationArgDto, result.transformationArg)
        assertEquals("testValue", result.value)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = AppliedTransformArg(
            id = 42L,
            appliedTransform = mockk(),
            transformationArg = mockk(),
            value = "updatedValue"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<AppliedTransformArgEntity>(relaxed = true)
        val appliedTransformEntity: AppliedTransformEntity = mockk()
        val transformArgEntity: TransformArgEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(AppliedTransformArgEntity)
        every { AppliedTransformArgEntity.findById(42L) } returns existingEntity
        every { appliedTransformsMapper.toEntity(dto.appliedTransform) } returns appliedTransformEntity
        every { transformationArgsMapper.toEntity(dto.transformationArg) } returns transformArgEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify(exactly = 1) { appliedTransformsMapper.toEntity(dto.appliedTransform) }
        verify(exactly = 1) { transformationArgsMapper.toEntity(dto.transformationArg) }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)
        unmockkObject(AppliedTransformArgEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = AppliedTransformArg(
            id = 0L,
            appliedTransform = mockk(),
            transformationArg = mockk(),
            value = "newValue"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<AppliedTransformArgEntity>(relaxed = true)
        val appliedTransformEntity: AppliedTransformEntity = mockk()
        val transformArgEntity: TransformArgEntity = mockk()

        every { factory.create() } returns newEntity
        every { appliedTransformsMapper.toEntity(any<AppliedTransform>()) } returns appliedTransformEntity
        every { transformationArgsMapper.toEntity(any<TransformArg>()) } returns transformArgEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify(exactly = 1) { factory.create() }
        verify(exactly = 1) { appliedTransformsMapper.toEntity(dto.appliedTransform) }
        verify(exactly = 1) { transformationArgsMapper.toEntity(dto.transformationArg) }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}