package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.dto.transforms.TransformOccurrenceDto
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.entity.transforms.enums.TransformOccurrence
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.transforms.enums.TransformOccurrenceMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class AppliedTransformMapperTest {

    private lateinit var mapper: AppliedTransformsMapper
    private val transformationsMapper: TransformationsMapper = mockk()
    private val typeDeclarationsMapper: TypeDeclarationsMapper = mockk()
    private val dataPointsMapper: DataPointsMapper = mockk()
    private val transformOccurrenceMapper: TransformOccurrenceMapper = mockk()
    private val factory: DaoFactory<AppliedTransformDao, Long> = mockk()

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
        val dto = AppliedTransformDto(
            id = 99L,
            transformation = mockk(),
            occurrence = mockk(),
            resultType = mockk(),
            dataPoint = mockk()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(AppliedTransformDao)
        every { AppliedTransformDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("AppliedTransform with id=99 not found", exception.message)
        unmockkObject(AppliedTransformDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<AppliedTransformDao> {
            every { id.value } returns 1L
            every { transformation } returns mockk()
            every { occurrence } returns mockk()
            every { resultType } returns mockk()
            every { dataPoint } returns mockk()
        }

        // Mock the behavior of transformationsMapper, typeDeclarationsMapper, and dataPointsMapper
        val transformationDto = mockk<TransformationDto>()
        val occurrenceDto = mockk<TransformOccurrenceDto>()
        val resultTypeDto = mockk<TypeDeclarationDto>()
        val dataPointDto = mockk<DataPointDto>()

        every { transformationsMapper.toDto(entity.transformation) } returns transformationDto
        every { transformOccurrenceMapper.toDto(entity.occurrence) } returns occurrenceDto
        every { typeDeclarationsMapper.toDto(entity.resultType) } returns resultTypeDto
        every { dataPointsMapper.toDto(entity.dataPoint!!) } returns dataPointDto

        // Call the method under test
        val result = mapper.toDto(entity)

        verify(exactly = 1) { transformationsMapper.toDto(entity.transformation) }
        verify(exactly = 1) { transformOccurrenceMapper.toDto(entity.occurrence) }
        verify(exactly = 1) { typeDeclarationsMapper.toDto(entity.resultType) }
        verify(exactly = 1) { dataPointsMapper.toDto(entity.dataPoint!!) }

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(transformationDto, result.transformation)
        assertEquals(occurrenceDto, result.occurrence)
        assertEquals(resultTypeDto, result.resultType)
        assertEquals(dataPointDto, result.dataPoint)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = AppliedTransformDto(
            id = 42L,
            transformation = mockk(),
            occurrence = mockk(),
            resultType = mockk(),
            dataPoint = mockk()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<AppliedTransformDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val transformDao: TransformationDao = mockk()
        val dataPointDao: DataPointDao = mockk()
        val transformOccurrence: TransformOccurrence = mockk()
        val typeDeclarationDao: TypeDeclarationDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(AppliedTransformDao)
        every { AppliedTransformDao.findById(42L) } returns existingEntity
        every { transformationsMapper.toEntity(dto.transformation) } returns transformDao
        every { dataPointsMapper.toEntity(dto.dataPoint!!) } returns dataPointDao
        every { transformOccurrenceMapper.toEntity(dto.occurrence) } returns transformOccurrence
        every { typeDeclarationsMapper.toEntity(dto.resultType) } returns typeDeclarationDao

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify(exactly = 1) { AppliedTransformDao.findById(42L) }
        verify(exactly = 1) { transformationsMapper.toEntity(dto.transformation) }
        verify(exactly = 1) { dataPointsMapper.toEntity(dto.dataPoint!!) }
        verify(exactly = 1) { transformOccurrenceMapper.toEntity(dto.occurrence) }
        verify(exactly = 1) { typeDeclarationsMapper.toEntity(dto.resultType) }

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(AppliedTransformDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = AppliedTransformDto(
            id = 0L,
            transformation = mockk(),
            occurrence = mockk(),
            resultType = mockk(),
            dataPoint = mockk()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<AppliedTransformDao>(relaxed = true)
        val transformDao: TransformationDao = mockk()
        val dataPointDao: DataPointDao = mockk()
        val transformOccurrence: TransformOccurrence = mockk()
        val typeDeclarationDao: TypeDeclarationDao = mockk()

        every { factory.create() } returns newEntity
        every { transformationsMapper.toEntity(dto.transformation) } returns transformDao
        every { dataPointsMapper.toEntity(dto.dataPoint!!) } returns dataPointDao
        every { transformOccurrenceMapper.toEntity(dto.occurrence) } returns transformOccurrence
        every { typeDeclarationsMapper.toEntity(dto.resultType) } returns typeDeclarationDao

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
        assertEquals(transformDao, result.transformation)
        assertEquals(transformOccurrence, result.occurrence)
        assertEquals(typeDeclarationDao, result.resultType)
        assertEquals(dataPointDao, result.dataPoint)


    }
}