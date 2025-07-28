package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.Provision
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.entity.steps.ToolParamDataPointEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.steps.ToolParamsMapper
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
    private val factory: DaoFactory<ToolParamDataPointEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = ProvisionsMapper(factory, stepsMapper, toolParamsMapper, dataPointsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = Provision(
            id = 99L,
            step = mockk(),
            toolParam = mockk<ToolParam>(),
            dataPoints = emptyList()
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(ToolParamDataPointEntity)
        every { ToolParamDataPointEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("Provision with id=99 not found", exception.message)
        unmockkObject(ToolParamDataPointEntity)
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
        val dto = Provision(
            id = 42L,
            step = mockk(),
            toolParam = mockk(),
            dataPoints = emptyList()
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<ToolParamDataPointEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val toolParamEntity: ToolParamEntity = mockk()
        val dataPointEntity: DataPointEntity = mockk()

        every { stepsMapper.toEntity(any<Step>()) } returns stepEntity
        every { toolParamsMapper.toEntity(any<ToolParam>()) } returns toolParamEntity
        every { dataPointsMapper.toEntity(any<DataPoint>()) } returns dataPointEntity

        // Set up mocks for finding existing entity
        mockkObject(ToolParamDataPointEntity)
        every { ToolParamDataPointEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val stepSlot = slot<StepEntity>()
        val toolParamSlot = slot<ToolParamEntity>()
        val dataPointSlot = slot<SizedIterable<DataPointEntity>>()

        every { existingEntity.step = capture(stepSlot) } just runs
        every { existingEntity.toolParam = capture(toolParamSlot) } just runs
        every { existingEntity.dataPointEntity = capture(dataPointSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { ToolParamDataPointEntity.findById(42L) }
        verify { existingEntity.step = stepEntity }
        verify { existingEntity.toolParam = toolParamEntity }

        // Verify the captured values
        assertEquals(stepEntity, stepSlot.captured)
        assertEquals(toolParamEntity, toolParamSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(ToolParamDataPointEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = Provision(
            id = 0L,
            step = mockk(),
            toolParam = mockk(),
            dataPoints = emptyList()
        )

        // Mock the new entity that will be created
        val newEntity = mockk<ToolParamDataPointEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val stepEntity: StepEntity = mockk()
        val toolParamEntity: ToolParamEntity = mockk()
        val dataPointEntity: DataPointEntity = mockk()

        every { stepsMapper.toEntity(any<Step>()) } returns stepEntity
        every { toolParamsMapper.toEntity(any<ToolParam>()) } returns toolParamEntity
        every { dataPointsMapper.toEntity(any()) } returns dataPointEntity

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val stepSlot = slot<StepEntity>()
        val toolParamSlot = slot<ToolParamEntity>()
        val dataPointSlot = slot<SizedIterable<DataPointEntity>>()

        every { newEntity.step = capture(stepSlot) } just runs
        every { newEntity.toolParam = capture(toolParamSlot) } just runs
        every { newEntity.dataPointEntity = capture(dataPointSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.step = stepEntity }
        verify { newEntity.toolParam = toolParamEntity }

        // Verify the captured values
        assertEquals(stepEntity, stepSlot.captured)
        assertEquals(toolParamEntity, toolParamSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}