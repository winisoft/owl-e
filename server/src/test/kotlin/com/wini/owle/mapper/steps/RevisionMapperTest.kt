package com.wini.owle.mapper.steps

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.steps.DataPointSave
import com.wini.owle.dto.workflows.Step
import com.wini.owle.entity.steps.DataPointSaveEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class RevisionMapperTest {

    private lateinit var mapper: RevisionsMapper
    private val dataPointMapper: DataPointsMapper = mockk()
    private val stepMapper: StepsMapper = mockk()
    private val factory: DaoFactory<DataPointSaveEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = RevisionsMapper(factory, dataPointMapper, stepMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = DataPointSave(id = 99L, dataPoint = mockk(), step = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(DataPointSaveEntity)
        every { DataPointSaveEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Revision with id=99 not found", exception.message)
        unmockkObject(DataPointSaveEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<DataPointSaveEntity> {
            every { id.value } returns 1L
            every { dataPointEntity } returns mockk()
            every { step } returns mockk()
        }

        // Mock the behavior of dataPointMapper and stepMapper
        val dataPoint = mockk<DataPoint>()
        val step = mockk<Step>()
        every { dataPointMapper.toDto(entity.dataPointEntity) } returns dataPoint
        every { stepMapper.toDto(entity.step) } returns step

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(dataPoint, result.dataPoint)
        assertEquals(step, result.step)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = DataPointSave(id = 42L, dataPoint = mockk(), step = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<DataPointSaveEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val dataPointEntity: DataPointEntity = mockk()
        val stepEntity: StepEntity = mockk()

        every { dataPointMapper.toEntity(any<DataPoint>()) } returns dataPointEntity
        every { stepMapper.toEntity(any<Step>()) } returns stepEntity

        // Set up mocks for finding existing entity
        mockkObject(DataPointSaveEntity)
        every { DataPointSaveEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val dataPointSlot = slot<DataPointEntity>()
        val stepSlot = slot<StepEntity>()

        every { existingEntity.dataPointEntity = capture(dataPointSlot) } just runs
        every { existingEntity.step = capture(stepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { DataPointSaveEntity.findById(42L) }
        verify { existingEntity.dataPointEntity = dataPointEntity }
        verify { existingEntity.step = stepEntity }

        // Verify the captured values
        assertEquals(dataPointEntity, dataPointSlot.captured)
        assertEquals(stepEntity, stepSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(DataPointSaveEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = DataPointSave(id = 0L, dataPoint = mockk(), step = mockk())

        // Mock the new entity that will be created
        val newEntity = mockk<DataPointSaveEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val dataPointEntity: DataPointEntity = mockk()
        val stepEntity: StepEntity = mockk()

        every { dataPointMapper.toEntity(any<DataPoint>()) } returns dataPointEntity
        every { stepMapper.toEntity(any<Step>()) } returns stepEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val dataPointSlot = slot<DataPointEntity>()
        val stepSlot = slot<StepEntity>()

        every { newEntity.dataPointEntity = capture(dataPointSlot) } just runs
        every { newEntity.step = capture(stepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.dataPointEntity = dataPointEntity }
        verify { newEntity.step = stepEntity }

        // Verify the captured values
        assertEquals(dataPointEntity, dataPointSlot.captured)
        assertEquals(stepEntity, stepSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}