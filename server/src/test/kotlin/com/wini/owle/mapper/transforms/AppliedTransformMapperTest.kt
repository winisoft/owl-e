package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.DaoFactory
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
    private val factory: DaoFactory<AppliedTransformDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = AppliedTransformsMapper(
            factory,
            transformationsMapper,
            typeDeclarationsMapper,
            dataPointsMapper
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
        val occurrenceDto = mockk<OccurrenceDto>()
        val resultTypeDto = mockk<ResultTypeDto>()
        val dataPointDto = mockk<DataPointDto>()
        every { transformationsMapper.toDto(entity.transformation) } returns transformationDto
        every { entity.occurrence.toDto() } returns occurrenceDto
        every { typeDeclarationsMapper.toDto(entity.resultType) } returns resultTypeDto
        every { dataPointsMapper.toDto(entity.dataPoint) } returns dataPointDto

        // Call the method under test
        val result = mapper.toDto(entity)

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

        // Set up mocks for finding existing entity
        mockkObject(AppliedTransformDao)
        every { AppliedTransformDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val transformationSlot = slot<Transformation>()
        val occurrenceSlot = slot<Occurrence>()
        val resultTypeSlot = slot<ResultType>()
        val dataPointSlot = slot<DataPoint>()

        every { existingEntity.transformation = capture(transformationSlot) } just runs
        every { existingEntity.occurrence = capture(occurrenceSlot) } just runs
        every { existingEntity.resultType = capture(resultTypeSlot) } just runs
        every { existingEntity.dataPoint = capture(dataPointSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { AppliedTransformDao.findById(42L) }
        verify { existingEntity.transformation = dto.transformation }
        verify { existingEntity.occurrence = dto.occurrence }
        verify { existingEntity.resultType = dto.resultType }
        verify { existingEntity.dataPoint = dto.dataPoint }

        // Verify the captured values
        assertEquals(dto.transformation, transformationSlot.captured)
        assertEquals(dto.occurrence, occurrenceSlot.captured)
        assertEquals(dto.resultType, resultTypeSlot.captured)
        assertEquals(dto.dataPoint, dataPointSlot.captured)

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

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val transformationSlot = slot<Transformation>()
        val occurrenceSlot = slot<Occurrence>()
        val resultTypeSlot = slot<ResultType>()
        val dataPointSlot = slot<DataPoint>()

        every { newEntity.transformation = capture(transformationSlot) } just runs
        every { newEntity.occurrence = capture(occurrenceSlot) } just runs
        every { newEntity.resultType = capture(resultTypeSlot) } just runs
        every { newEntity.dataPoint = capture(dataPointSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.transformation = dto.transformation }
        verify { newEntity.occurrence = dto.occurrence }
        verify { newEntity.resultType = dto.resultType }
        verify { newEntity.dataPoint = dto.dataPoint }

        // Verify the captured values
        assertEquals(dto.transformation, transformationSlot.captured)
        assertEquals(dto.occurrence, occurrenceSlot.captured)
        assertEquals(dto.resultType, resultTypeSlot.captured)
        assertEquals(dto.dataPoint, dataPointSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}