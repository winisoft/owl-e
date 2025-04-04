package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.dto.steps.ProvisionDto
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.ToolParamsMapper
import io.mockk.*
import org.jetbrains.exposed.sql.SizedIterable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class ProvisionMapperTest {

    private lateinit var mapper: ProvisionsMapper
    private val stepsMapper: StepsMapper = mockk()
    private val toolParamsMapper: ToolParamsMapper = mockk()
    private val dataPointsMapper: DataPointsMapper = mockk()
    private val factory: DaoFactory<ProvisionDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ProvisionsMapper(factory, stepsMapper, toolParamsMapper, dataPointsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = ProvisionDto(
            id = 99L,
            step = mockk(),
            toolParam = mockk<ToolParamDto>(),
            dataPoints = emptyList()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ProvisionDao)
        every { ProvisionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Provision with id=99 not found", exception.message)
        unmockkObject(ProvisionDao)
    }

//    @Test
//    fun `toDto should correctly map entity to dto`() {
//        // Create mock dao objects
//        val entity = mockk<ProvisionDao> {
//            every { id.value } returns 1L
//            every { step } returns mockk()
//            every { toolParam } returns mockk()
//            every { dataPoint } returns mockk<SizedIterable<DataPointDao>>()
//        }
//
//        // Mock the behavior of stepsMapper, toolParamsMapper, and provisionDataPointMapper
//        val stepDto = mockk<StepDto>()
//        val toolParamDto = mockk<ToolParamDto>()
//        val dataPointDto = mockk<DataPointDto>()
//        every { stepsMapper.toDto(entity.step) } returns stepDto
//        every { toolParamsMapper.toDto(entity.toolParam) } returns toolParamDto
//        every { dataPointsMapper.toDto(any()) } returns dataPointDto
//
//        // Call the method under test
//        val result = mapper.toDto(entity)
//
//        // Verify the result
//        assertEquals(1L, result.id)
//        assertEquals(stepDto, result.step)
//        assertEquals(toolParamDto, result.toolParam)
//        assertEquals(listOf(dataPointDto), result.dataPoints)
//    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = ProvisionDto(
            id = 42L,
            step = mockk(),
            toolParam = mockk(),
            dataPoints = emptyList()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ProvisionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepDao: StepDao = mockk()
        val toolParamDao: ToolParamDao = mockk()
        val dataPointDao: DataPointDao = mockk()

        every { stepsMapper.toEntity(any<StepDto>()) } returns stepDao
        every { toolParamsMapper.toEntity(any<ToolParamDto>()) } returns toolParamDao
        every { dataPointsMapper.toEntity(any<DataPointDto>()) } returns dataPointDao

        // Set up mocks for finding existing entity
        mockkObject(ProvisionDao)
        every { ProvisionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val stepSlot = slot<StepDao>()
        val toolParamSlot = slot<ToolParamDao>()
        val dataPointSlot = slot<SizedIterable<DataPointDao>>()

        every { existingEntity.step = capture(stepSlot) } just runs
        every { existingEntity.toolParam = capture(toolParamSlot) } just runs
        every { existingEntity.dataPoint = capture(dataPointSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ProvisionDao.findById(42L) }
        verify { existingEntity.step = stepDao }
        verify { existingEntity.toolParam = toolParamDao }

        // Verify the captured values
        assertEquals(stepDao, stepSlot.captured)
        assertEquals(toolParamDao, toolParamSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ProvisionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = ProvisionDto(
            id = 0L,
            step = mockk(),
            toolParam = mockk(),
            dataPoints = emptyList()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ProvisionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepDao: StepDao = mockk()
        val toolParamDao: ToolParamDao = mockk()
        val dataPointDao: DataPointDao = mockk()

        every { stepsMapper.toEntity(any<StepDto>()) } returns stepDao
        every { toolParamsMapper.toEntity(any<ToolParamDto>()) } returns toolParamDao
        every { dataPointsMapper.toEntity(any()) } returns dataPointDao

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stepSlot = slot<StepDao>()
        val toolParamSlot = slot<ToolParamDao>()
        val dataPointSlot = slot<SizedIterable<DataPointDao>>()

        every { newEntity.step = capture(stepSlot) } just runs
        every { newEntity.toolParam = capture(toolParamSlot) } just runs
        every { newEntity.dataPoint = capture(dataPointSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.step = stepDao }
        verify { newEntity.toolParam = toolParamDao }

        // Verify the captured values
        assertEquals(stepDao, stepSlot.captured)
        assertEquals(toolParamDao, toolParamSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}