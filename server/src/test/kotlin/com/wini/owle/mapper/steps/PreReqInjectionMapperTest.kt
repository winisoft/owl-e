package com.wini.owle.mapper.steps

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.dto.steps.WorkflowJunctionDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


open class PreReqInjectionMapperTest {

    private lateinit var mapper: PreReqInjectionsMapper
    private val workflowJunctionsMapper: WorkflowJunctionsMapper = mockk()
    private val dataPointsMapper: DataPointsMapper = mockk()
    private val factory: DaoFactory<PreReqInjectionDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PreReqInjectionsMapper(factory, workflowJunctionsMapper, dataPointsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = PreReqInjectionDto(
            id = 99L,
            junction = mockk(),
            sourceDataPoint = mockk(),
            injectedDataPoint = mockk(),
            name = "testName",
            description = "testDescription"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(PreReqInjectionDao)
        every { PreReqInjectionDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("PreReqInjection with id=99 not found", exception.message)
        unmockkObject(PreReqInjectionDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<PreReqInjectionDao> {
            every { id.value } returns 1L
            every { junction } returns mockk()
            every { sourceDataPoint } returns mockk()
            every { injectedDataPoint } returns mockk()
            every { name } returns "testName"
            every { description } returns "testDescription"
        }

        // Mock the behavior of workflowJunctionsMapper and dataPointsMapper
        val junctionDto = mockk<WorkflowJunctionDto>()
        val sourceDataPointDto = mockk<DataPointDto>()
        val injectedDataPointDto = mockk<DataPointDto>()
        every { workflowJunctionsMapper.toDto(entity.junction) } returns junctionDto
        every { dataPointsMapper.toDto(entity.sourceDataPoint) } returns sourceDataPointDto
        every { dataPointsMapper.toDto(entity.injectedDataPoint) } returns injectedDataPointDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(junctionDto, result.junction)
        assertEquals(sourceDataPointDto, result.sourceDataPoint)
        assertEquals(injectedDataPointDto, result.injectedDataPoint)
        assertEquals("testName", result.name)
        assertEquals("testDescription", result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = PreReqInjectionDto(
            id = 42L,
            junction = mockk(),
            sourceDataPoint = mockk(),
            injectedDataPoint = mockk(),
            name = "updatedName",
            description = "updatedDescription"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<PreReqInjectionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowJunctionDao: WorkflowJunctionDao = mockk()
        val sourceDataPointDao: DataPointDao = mockk()
        val injectedDataPointDao: DataPointDao = mockk()

        // Set up mocks for finding existing entity
        mockkObject(PreReqInjectionDao)
        every { PreReqInjectionDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val junctionSlot = slot<WorkflowJunctionDao>()
        val sourceDataPointSlot = slot<DataPointDao>()
        val injectedDataPointSlot = slot<DataPointDao>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { existingEntity.junction = capture(junctionSlot) } just runs
        every { existingEntity.sourceDataPoint = capture(sourceDataPointSlot) } just runs
        every { existingEntity.injectedDataPoint = capture(injectedDataPointSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { PreReqInjectionDao.findById(42L) }
        verify { existingEntity.junction = workflowJunctionDao }
        verify { existingEntity.sourceDataPoint = sourceDataPointDao }
        verify { existingEntity.injectedDataPoint = injectedDataPointDao }
        verify { existingEntity.name = dto.name }
        verify { existingEntity.description = dto.description }

        // Verify the captured values
        assertEquals(workflowJunctionDao, junctionSlot.captured)
        assertEquals(sourceDataPointDao, sourceDataPointSlot.captured)
        assertEquals(injectedDataPointDao, injectedDataPointSlot.captured)
        assertEquals(dto.name, nameSlot.captured)
        assertEquals(dto.description, descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(PreReqInjectionDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = PreReqInjectionDto(
            id = 0L,
            junction = mockk(),
            sourceDataPoint = mockk(),
            injectedDataPoint = mockk(),
            name = "newName",
            description = "newDescription"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<PreReqInjectionDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowJunctionDao: WorkflowJunctionDao = mockk()
        val sourceDataPointDao: DataPointDao = mockk()
        val injectedDataPointDao: DataPointDao = mockk()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val junctionSlot = slot<WorkflowJunctionDao>()
        val sourceDataPointSlot = slot<DataPointDao>()
        val injectedDataPointSlot = slot<DataPointDao>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { newEntity.junction = capture(junctionSlot) } just runs
        every { newEntity.sourceDataPoint = capture(sourceDataPointSlot) } just runs
        every { newEntity.injectedDataPoint = capture(injectedDataPointSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.junction = workflowJunctionDao }
        verify { newEntity.sourceDataPoint = sourceDataPointDao }
        verify { newEntity.injectedDataPoint = injectedDataPointDao }
        verify { newEntity.name = dto.name }
        verify { newEntity.description = dto.description }

        // Verify the captured values
        assertEquals(workflowJunctionDao, junctionSlot.captured)
        assertEquals(sourceDataPointDao, sourceDataPointSlot.captured)
        assertEquals(injectedDataPointDao, injectedDataPointSlot.captured)
        assertEquals(dto.name, nameSlot.captured)
        assertEquals(dto.description, descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}