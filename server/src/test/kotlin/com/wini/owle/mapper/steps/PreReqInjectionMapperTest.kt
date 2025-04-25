package com.wini.owle.mapper.steps

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.steps.InitialDataPointInjector
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.entity.steps.InitialDataPointInjectorEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.workflows.DataPointEntity
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
    private val factory: DaoFactory<InitialDataPointInjectorEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = PreReqInjectionsMapper(factory, workflowJunctionsMapper, dataPointsMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = InitialDataPointInjector(
            id = 99L,
            junction = mockk(),
            sourceDataPoint = mockk(),
            injectedDataPoint = mockk(),
            name = "testName",
            description = "testDescription"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(InitialDataPointInjectorEntity)
        every { InitialDataPointInjectorEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("PreReqInjection with id=99 not found", exception.message)
        unmockkObject(InitialDataPointInjectorEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val entity = mockk<InitialDataPointInjectorEntity> {
            every { id.value } returns 1L
            every { junction } returns mockk()
            every { sourceDataPointEntity } returns mockk()
            every { injectedDataPointEntity } returns mockk()
            every { name } returns "testName"
            every { description } returns "testDescription"
        }

        // Mock the behavior of workflowJunctionsMapper and dataPointsMapper
        val junctionDto = mockk<WorkflowJunction>()
        val sourceDataPoint = mockk<DataPoint>()
        val injectedDataPoint = mockk<DataPoint>()
        every { workflowJunctionsMapper.toDto(entity.junction) } returns junctionDto
        every { dataPointsMapper.toDto(entity.sourceDataPointEntity) } returns sourceDataPoint
        every { dataPointsMapper.toDto(entity.injectedDataPointEntity) } returns injectedDataPoint

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(junctionDto, result.junction)
        assertEquals(sourceDataPoint, result.sourceDataPoint)
        assertEquals(injectedDataPoint, result.injectedDataPoint)
        assertEquals("testName", result.name)
        assertEquals("testDescription", result.description)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = InitialDataPointInjector(
            id = 42L,
            junction = mockk(),
            sourceDataPoint = mockk(),
            injectedDataPoint = mockk(),
            name = "updatedName",
            description = "updatedDescription"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<InitialDataPointInjectorEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowJunctionEntity: WorkflowJunctionEntity = mockk()
        val sourceDataPointEntity: DataPointEntity = mockk()
        val injectedDataPointEntity: DataPointEntity = mockk()

        every { workflowJunctionsMapper.toEntity(any<WorkflowJunction>()) } returns workflowJunctionEntity
        every { dataPointsMapper.toEntity(any<DataPoint>()) }.returnsMany(sourceDataPointEntity, injectedDataPointEntity)

        // Set up mocks for finding existing entity
        mockkObject(InitialDataPointInjectorEntity)
        every { InitialDataPointInjectorEntity.findById(42L) } returns existingEntity

        // Capture the property updates
        val junctionSlot = slot<WorkflowJunctionEntity>()
        val sourceDataPointEntitySlot = slot<DataPointEntity>()
        val injectedDataPointEntitySlot = slot<DataPointEntity>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { existingEntity.junction = capture(junctionSlot) } just runs
        every { existingEntity.sourceDataPointEntity = capture(sourceDataPointEntitySlot) } just runs
        every { existingEntity.injectedDataPointEntity = capture(injectedDataPointEntitySlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { InitialDataPointInjectorEntity.findById(42L) }
        verify { existingEntity.junction = workflowJunctionEntity }
        verify { existingEntity.sourceDataPointEntity = sourceDataPointEntity }
        verify { existingEntity.injectedDataPointEntity = injectedDataPointEntity }
        verify { existingEntity.name = dto.name }
        verify { existingEntity.description = dto.description }

        // Verify the captured values
        assertEquals(workflowJunctionEntity, junctionSlot.captured)
        assertEquals(sourceDataPointEntity, sourceDataPointEntitySlot.captured)
        assertEquals(injectedDataPointEntity, injectedDataPointEntitySlot.captured)
        assertEquals(dto.name, nameSlot.captured)
        assertEquals(dto.description, descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(InitialDataPointInjectorEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = InitialDataPointInjector(
            id = 0L,
            junction = mockk(),
            sourceDataPoint = mockk(),
            injectedDataPoint = mockk(),
            name = "newName",
            description = "newDescription"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<InitialDataPointInjectorEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowJunctionEntity: WorkflowJunctionEntity = mockk()
        val sourceDataPointEntity: DataPointEntity = mockk()
        val injectedDataPointEntity: DataPointEntity = mockk()

        every { workflowJunctionsMapper.toEntity(any<WorkflowJunction>()) } returns workflowJunctionEntity
        every { dataPointsMapper.toEntity(any<DataPoint>()) }.returnsMany(sourceDataPointEntity, injectedDataPointEntity)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val junctionSlot = slot<WorkflowJunctionEntity>()
        val sourceDataPointEntitySlot = slot<DataPointEntity>()
        val injectedDataPointEntitySlot = slot<DataPointEntity>()
        val nameSlot = slot<String>()
        val descriptionSlot = slot<String>()

        every { newEntity.junction = capture(junctionSlot) } just runs
        every { newEntity.sourceDataPointEntity = capture(sourceDataPointEntitySlot) } just runs
        every { newEntity.injectedDataPointEntity = capture(injectedDataPointEntitySlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.junction = workflowJunctionEntity }
        verify { newEntity.sourceDataPointEntity = sourceDataPointEntity }
        verify { newEntity.injectedDataPointEntity = injectedDataPointEntity }
        verify { newEntity.name = dto.name }
        verify { newEntity.description = dto.description }

        // Verify the captured values
        assertEquals(workflowJunctionEntity, junctionSlot.captured)
        assertEquals(sourceDataPointEntity, sourceDataPointEntitySlot.captured)
        assertEquals(injectedDataPointEntity, injectedDataPointEntitySlot.captured)
        assertEquals(dto.name, nameSlot.captured)
        assertEquals(dto.description, descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}