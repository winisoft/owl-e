package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransformArgDto
import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.models.TransformationArg
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class AppliedTransformArgMapperTest {

    private lateinit var mapper: AppliedTransformArgsMapper
    private val appliedTransformsMapper: AppliedTransformsMapper = mockk()
    private val transformationArgsMapper: TransformationArgsMapper = mockk()
    private val factory: DaoFactory<AppliedTransformArgDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = AppliedTransformArgsMapper(factory, appliedTransformsMapper, transformationArgsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = AppliedTransformArgDto(id = 99L, appliedTransform = mockk(), transformationArg = mockk(), value = "testValue")

        // Mock the behavior of findById to return null (not found)
        mockkObject(AppliedTransformArgDao)
        every { AppliedTransformArgDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("AppliedTransformArg with id=99 not found", exception.message)
        unmockkObject(AppliedTransformArgDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<AppliedTransformArgDao> {
            every { id.value } returns 1L
            every { appliedTransform } returns mockk()
            every { transformationArg } returns mockk()
            every { value } returns "testValue"
        }

        // Mock the behavior of appliedTransformsMapper and transformationArgsMapper
        val appliedTransformDto = mockk<AppliedTransformDto>()
        val transformationArgDto = mockk<TransformationArgDto>()
        every { appliedTransformsMapper.toDto(entity.appliedTransform) } returns appliedTransformDto
        every { transformationArgsMapper.toDto(entity.transformationArg) } returns transformationArgDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(appliedTransformDto, result.appliedTransform)
        assertEquals(transformationArgDto, result.transformationArg)
        assertEquals("testValue", result.value)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = AppliedTransformArgDto(id = 42L, appliedTransform = mockk(), transformationArg = mockk(), value = "updatedValue")

        // Mock the existing entity that will be updated
        val existingEntity = mockk<AppliedTransformArgDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(AppliedTransformArgDao)
        every { AppliedTransformArgDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val appliedTransformSlot = slot<AppliedTransform>()
        val transformationArgSlot = slot<TransformationArg>()
        val valueSlot = slot<String>()

        every { existingEntity.appliedTransform = capture(appliedTransformSlot) } just runs
        every { existingEntity.transformationArg = capture(transformationArgSlot) } just runs
        every { existingEntity.value = capture(valueSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { AppliedTransformArgDao.findById(42L) }
        verify { existingEntity.appliedTransform = dto.appliedTransform }
        verify { existingEntity.transformationArg = dto.transformationArg }
        verify { existingEntity.value = dto.value }

        // Verify the captured values
        assertEquals(dto.appliedTransform, appliedTransformSlot.captured)
        assertEquals(dto.transformationArg, transformationArgSlot.captured)
        assertEquals(dto.value, valueSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(AppliedTransformArgDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = AppliedTransformArgDto(
            id = 0L,
            appliedTransform = mockk(),
            transformationArg = mockk(),
            value = "newValue"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<AppliedTransformArgDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val appliedTransformSlot = slot<AppliedTransform>()
        val transformationArgSlot = slot<TransformationArg>()
        val valueSlot = slot<String>()

        every { newEntity.appliedTransform = capture(appliedTransformSlot) } just runs
        every { newEntity.transformationArg = capture(transformationArgSlot) } just runs
        every { newEntity.value = capture(valueSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.appliedTransform = dto.appliedTransform }
        verify { newEntity.transformationArg = dto.transformationArg }
        verify { newEntity.value = dto.value }

        // Verify the captured values
        assertEquals(dto.appliedTransform, appliedTransformSlot.captured)
        assertEquals(dto.transformationArg, transformationArgSlot.captured)
        assertEquals(dto.value, valueSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}