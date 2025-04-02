package com.wini.owle.mapper.steps

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

open class RevisionMapperTest {

    private lateinit var mapper: RevisionsMapper
    private val dataPointMapper: DataPointsMapper = mockk()
    private val stepMapper: StepsMapper = mockk()
    private val factory: DaoFactory<RevisionDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = RevisionsMapper(factory, dataPointMapper, stepMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = RevisionDto(id = 99L, dataPoint = mockk(), step = mockk())

        // Mock the behavior of findById to return null (not found)
        mockkObject(RevisionDao)
        every { RevisionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Revision with id=99 not found", exception.message)
        unmockkObject(RevisionDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<RevisionDao> {
            every { id.value } returns 1L
            every { dataPoint } returns mockk()
            every { step } returns mockk()
        }

        // Mock the behavior of dataPointMapper and stepMapper
        val dataPointDto = mockk<DataPointDto>()
        val stepDto = mockk<StepDto>()
        every { dataPointMapper.toDto(entity.dataPoint) } returns dataPointDto
        every { stepMapper.toDto(entity.step) } returns stepDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(dataPointDto, result.dataPoint)
        assertEquals(stepDto, result.step)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = RevisionDto(id = 42L, dataPoint = mockk(), step = mockk())

        // Mock the existing entity that will be updated
        val existingEntity = mockk<RevisionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val dataPointDao: DataPointDao = mockk()
        val stepDao: StepDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(RevisionDao)
        every { RevisionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val dataPointSlot = slot<DataPointDao>()
        val stepSlot = slot<StepDao>()

        every { existingEntity.dataPoint = capture(dataPointSlot) } just runs
        every { existingEntity.step = capture(stepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { RevisionDao.findById(42L) }
        verify { existingEntity.dataPoint = dataPointDao }
        verify { existingEntity.step = stepDao }

        // Verify the captured values
        assertEquals(dataPointDao, dataPointSlot.captured)
        assertEquals(stepDao, stepSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(RevisionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = RevisionDto(id = 0L, dataPoint = mockk(), step = mockk())

        // Mock the new entity that will be created
        val newEntity = mockk<RevisionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val dataPointDao: DataPointDao = mockk()
        val stepDao: StepDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val dataPointSlot = slot<DataPointDao>()
        val stepSlot = slot<StepDao>()

        every { newEntity.dataPoint = capture(dataPointSlot) } just runs
        every { newEntity.step = capture(stepSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.dataPoint = dataPointDao }
        verify { newEntity.step = stepDao }

        // Verify the captured values
        assertEquals(dataPointDao, dataPointSlot.captured)
        assertEquals(stepDao, stepSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}