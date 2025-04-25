package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransform
import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.transforms.AppliedTransformEntity
import com.wini.owle.entity.transforms.enums.TransformOccurrenceEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.transforms.enums.TransformOccurrenceMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class AppliedTransformEntityEntityMapperTest {

    private lateinit var mapper: AppliedTransformsMapper
    private val transformationsMapper: TransformationsMapper = mockk()
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val dataPointsMapper: DataPointsMapper = mockk()
    private val transformOccurrenceMapper: TransformOccurrenceMapper = mockk()
    private val factory: DaoFactory<AppliedTransformEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = AppliedTransformsMapper(
            factory,
            transformationsMapper,
            typeDeclarationsMapper,
            dataPointsMapper,
            transformOccurrenceMapper
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = AppliedTransform(
            id = 99L,
            transformation = mockk(),
            occurrence = mockk(),
            resultType = mockk(),
            dataPoint = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(AppliedTransformEntity)
        every { AppliedTransformEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("AppliedTransform with id=99 not found", exception.message)
        unmockkObject(AppliedTransformEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<AppliedTransformEntity> {
            every { id.value } returns 1L
            every { transformation } returns mockk()
            every { occurrence } returns mockk()
            every { resultType } returns mockk()
            every { dataPointEntity } returns mockk()
        }

        // Mock the behavior of transformationsMapper, typeDeclarationsMapper, and dataPointsMapper
        val transform = mockk<Transform>()
        val occurrenceDto = mockk<TransformOccurrence>()
        val resultTypeDto = mockk<TypeDeclaration>()
        val dataPoint = mockk<DataPoint>()

        every { transformationsMapper.toDto(entity.transformation) } returns transform
        every { transformOccurrenceMapper.toDto(entity.occurrence) } returns occurrenceDto
        every { typeDeclarationsMapper.toDto(entity.resultType) } returns resultTypeDto
        every { dataPointsMapper.toDto(entity.dataPointEntity!!) } returns dataPoint

        // Call the method under test
        val result = mapper.toDto(entity)

        verify(exactly = 1) { transformationsMapper.toDto(entity.transformation) }
        verify(exactly = 1) { transformOccurrenceMapper.toDto(entity.occurrence) }
        verify(exactly = 1) { typeDeclarationsMapper.toDto(entity.resultType) }
        verify(exactly = 1) { dataPointsMapper.toDto(entity.dataPointEntity!!) }

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(transform, result.transformation)
        assertEquals(occurrenceDto, result.occurrence)
        assertEquals(resultTypeDto, result.resultType)
        assertEquals(dataPoint, result.dataPoint)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = AppliedTransform(
            id = 42L,
            transformation = mockk(),
            occurrence = mockk(),
            resultType = mockk(),
            dataPoint = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<AppliedTransformEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val transformEntityDao: TransformEntity = mockk()
        val dataPointEntity: DataPointEntity = mockk()
        val transformOccurrence: TransformOccurrenceEntity = mockk()
        val typeDeclarationEntity: TypeDeclarationEntity = mockk()

        // Set up mocks for finding existing entity
        mockkObject(AppliedTransformEntity)
        every { AppliedTransformEntity.findById(42L) } returns existingEntity
        every { transformationsMapper.toEntity(dto.transformation) } returns transformEntityDao
        every { dataPointsMapper.toEntity(dto.dataPoint!!) } returns dataPointEntity
        every { transformOccurrenceMapper.toEntity(dto.occurrence) } returns transformOccurrence
        every { typeDeclarationsMapper.toEntity(dto.resultType) } returns typeDeclarationEntity

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify(exactly = 1) { AppliedTransformEntity.findById(42L) }
        verify(exactly = 1) { transformationsMapper.toEntity(dto.transformation) }
        verify(exactly = 1) { dataPointsMapper.toEntity(dto.dataPoint!!) }
        verify(exactly = 1) { transformOccurrenceMapper.toEntity(dto.occurrence) }
        verify(exactly = 1) { typeDeclarationsMapper.toEntity(dto.resultType) }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(AppliedTransformEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = AppliedTransform(
            id = 0L,
            transformation = mockk(),
            occurrence = mockk(),
            resultType = mockk(),
            dataPoint = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<AppliedTransformEntity>(relaxed = true) {
            every { dataPointEntity } returns mockk()
            every { transformation } returns mockk()
            every { resultType } returns mockk()
            every { occurrence } returns mockk()
        }

        every { factory.create() } returns newEntity
        every { transformationsMapper.toEntity(dto.transformation) } returns newEntity.transformation
        every { dataPointsMapper.toEntity(dto.dataPoint!!) } returns newEntity.dataPointEntity!!
        every { transformOccurrenceMapper.toEntity(dto.occurrence) } returns newEntity.occurrence
        every { typeDeclarationsMapper.toEntity(dto.resultType) } returns newEntity.resultType

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify the properties were set correctly
        verify { factory.create() }
        verify(exactly = 1) { transformationsMapper.toEntity(dto.transformation) }
        verify(exactly = 1) { dataPointsMapper.toEntity(dto.dataPoint!!) }
        verify(exactly = 1) { transformOccurrenceMapper.toEntity(dto.occurrence) }
        verify(exactly = 1) { typeDeclarationsMapper.toEntity(dto.resultType) }

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}