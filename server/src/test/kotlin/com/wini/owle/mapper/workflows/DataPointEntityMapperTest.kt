package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.enums.StoreMethodEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.enums.StoreMethodMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.mockk.slot
import io.mockk.just
import io.mockk.runs
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


open class DataPointEntityMapperTest {

    private lateinit var mapper: DataPointsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val workflowMapper: WorkflowsMapper = mockk()
    private val storeMethodMapper: StoreMethodMapper = mockk()
    private val factory: DaoFactory<DataPointEntity, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = DataPointsMapper(
            factory,
            typeDeclarationMapper,
            workflowMapper,
            storeMethodMapper
        )
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = DataPoint(
            id = 99L,
            workflow = mockk(),
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = mockk(),
            typeDeclaration = mockk(),
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the behavior of findById to return null (not found)
        mockkObject(DataPointEntity)
        every { DataPointEntity.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("DataPoint with id=99 not found", exception.message)
        unmockkObject(DataPointEntity)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val workflowEntity = mockk<WorkflowEntity>()
        val typeDeclarationEntity = mockk<TypeDeclarationEntity>()
        val storeMethodMock = mockk<StoreMethodEntity>()
        val entity = mockk<DataPointEntity> {
            every { id.value } returns 1L
            every { workflow } returns workflowEntity
            every { name } returns "dataPointName"
            every { presetValue } returns "preset"
            every { storeMethod } returns storeMethodMock
            every { typeDeclarationEntity } returns typeDeclarationEntity
            every { description } returns "description"
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper and workflowMapper
        val workflow = mockk<Workflow>()
        val typeDeclaration = mockk<TypeDeclaration>()
        val storeMethod = mockk<StoreMethod>()
        
        every { workflowMapper.toDto(workflowEntity) } returns workflow
        every { typeDeclarationMapper.toDto(typeDeclarationEntity) } returns typeDeclaration
        every { storeMethodMapper.toDto(any()) } returns storeMethod
        every { entity.createdAt.toString() } returns "2023-01-01T00:00:00"
        every { entity.updatedAt.toString() } returns "2023-01-01T00:00:00"

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflow, result.workflow)
        assertEquals("dataPointName", result.name)
        assertEquals("preset", result.presetValue)
        assertEquals(storeMethod, result.storeMethod)
        assertEquals(typeDeclaration, result.typeDeclaration)
        assertEquals("description", result.description)
        assertEquals("2023-01-01T00:00:00", result.createdAt)
        assertEquals("2023-01-01T00:00:00", result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val workflow = mockk<Workflow>()
        val typeDeclaration = mockk<TypeDeclaration>()
        val storeMethodDto = mockk<StoreMethod>()
        
        val dto = DataPoint(
            id = 42L,
            workflow = workflow,
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = storeMethodDto,
            typeDeclaration = typeDeclaration,
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<DataPointEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowEntity = mockk<WorkflowEntity>()
        val storeMethod = mockk<StoreMethodEntity>()
        val typeDeclarationEntity = mockk<TypeDeclarationEntity>()

        // Set up mocks for finding existing entity
        mockkObject(DataPointEntity)
        every { DataPointEntity.findById(42L) } returns existingEntity

        // Set up mocks for the mapper dependencies
        every { workflowMapper.toEntity(workflow) } returns workflowEntity
        every { typeDeclarationMapper.toEntity(typeDeclaration) } returns typeDeclarationEntity
        every { storeMethodMapper.toEntity(storeMethodDto) } returns storeMethod

        // Capture the property updates
        val workflowSlot = slot<WorkflowEntity>()
        val nameSlot = slot<String>()
        val presetValueSlot = slot<String>()
        val storeMethodSlot = slot<StoreMethodEntity>()
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val descriptionSlot = slot<String>()

        every { existingEntity.workflow = capture(workflowSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.presetValue = capture(presetValueSlot) } just runs
        every { existingEntity.storeMethod = capture(storeMethodSlot) } just runs
        every { existingEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { DataPointEntity.findById(42L) }
        verify { existingEntity.workflow = workflowEntity }
        verify { existingEntity.name = "dataPointName" }
        verify { existingEntity.presetValue = "preset" }
        verify { existingEntity.storeMethod = storeMethod }
        verify { existingEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { existingEntity.description = "description" }

        // Verify the captured values
        assertEquals(workflowEntity, workflowSlot.captured)
        assertEquals("dataPointName", nameSlot.captured)
        assertEquals("preset", presetValueSlot.captured)
        assertEquals(storeMethod, storeMethodSlot.captured)
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(DataPointEntity)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val workflow = mockk<Workflow>()
        val typeDeclaration = mockk<TypeDeclaration>()
        val storeMethod = mockk<StoreMethod>()
        
        val dto = DataPoint(
            id = 0L,
            workflow = workflow,
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = storeMethod,
            typeDeclaration = typeDeclaration,
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<DataPointEntity>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowEntity = mockk<WorkflowEntity>()
        val storeMethodMock = mockk<StoreMethodEntity>()
        val typeDeclarationEntity = mockk<TypeDeclarationEntity>()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Set up mocks for the mapper dependencies
        every { workflowMapper.toEntity(workflow) } returns workflowEntity
        every { typeDeclarationMapper.toEntity(typeDeclaration) } returns typeDeclarationEntity
        every { storeMethodMapper.toEntity(storeMethod) } returns storeMethodMock

        // Capture property assignments
        val workflowSlot = slot<WorkflowEntity>()
        val nameSlot = slot<String>()
        val presetValueSlot = slot<String>()
        val storeMethodSlot = slot<StoreMethodEntity>()
        val typeDeclarationEntitySlot = slot<TypeDeclarationEntity>()
        val descriptionSlot = slot<String>()

        every { newEntity.workflow = capture(workflowSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.presetValue = capture(presetValueSlot) } just runs
        every { newEntity.storeMethod = capture(storeMethodSlot) } just runs
        every { newEntity.typeDeclarationEntity = capture(typeDeclarationEntitySlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflow = workflowEntity }
        verify { newEntity.name = "dataPointName" }
        verify { newEntity.presetValue = "preset" }
        verify { newEntity.storeMethod = storeMethodMock }
        verify { newEntity.typeDeclarationEntity = typeDeclarationEntity }
        verify { newEntity.description = "description" }

        // Verify the captured values
        assertEquals(workflowEntity, workflowSlot.captured)
        assertEquals("dataPointName", nameSlot.captured)
        assertEquals("preset", presetValueSlot.captured)
        assertEquals(storeMethodMock, storeMethodSlot.captured)
        assertEquals(typeDeclarationEntity, typeDeclarationEntitySlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}