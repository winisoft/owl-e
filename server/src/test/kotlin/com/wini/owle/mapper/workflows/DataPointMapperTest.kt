package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.dto.workflows.StoreMethodDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.enums.StoreMethod
import com.wini.owle.entity.workflows.WorkflowDao
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


open class DataPointMapperTest {

    private lateinit var mapper: DataPointsMapper
    private val typeDeclarationMapper: TypeDeclarationsMapper = mockk()
    private val workflowMapper: WorkflowsMapper = mockk()
    private val storeMethodMapper: StoreMethodMapper = mockk()
    private val factory: DaoFactory<DataPointDao, Long> = mockk()

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
        val dto = DataPointDto(
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
        mockkObject(DataPointDao)
        every { DataPointDao.findById(99L) } returns null

        // Verify that the exception is thrown with the correct message
        val exception = assertThrows<IllegalStateException> {
            mapper.toEntity(dto)
        }

        assertEquals("DataPoint with id=99 not found", exception.message)
        unmockkObject(DataPointDao)
    }

    @Test
    fun `toDto should correctly map entity to dto`() {
        // Create mock dao objects
        val workflowDao = mockk<WorkflowDao>()
        val typeDeclarationDao = mockk<TypeDeclarationDao>()
        val storeMethodMock = mockk<StoreMethod>()
        val entity = mockk<DataPointDao> {
            every { id.value } returns 1L
            every { workflow } returns workflowDao
            every { name } returns "dataPointName"
            every { presetValue } returns "preset"
            every { storeMethod } returns storeMethodMock
            every { typeDeclaration } returns typeDeclarationDao
            every { description } returns "description"
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper and workflowMapper
        val workflowDto = mockk<WorkflowDto>()
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        val storeMethodDto = mockk<StoreMethodDto>()
        
        every { workflowMapper.toDto(workflowDao) } returns workflowDto
        every { typeDeclarationMapper.toDto(typeDeclarationDao) } returns typeDeclarationDto
        every { storeMethodMapper.toDto(any()) } returns storeMethodDto
        every { entity.createdAt.toString() } returns "2023-01-01T00:00:00"
        every { entity.updatedAt.toString() } returns "2023-01-01T00:00:00"

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowDto, result.workflow)
        assertEquals("dataPointName", result.name)
        assertEquals("preset", result.presetValue)
        assertEquals(storeMethodDto, result.storeMethod)
        assertEquals(typeDeclarationDto, result.typeDeclaration)
        assertEquals("description", result.description)
        assertEquals("2023-01-01T00:00:00", result.createdAt)
        assertEquals("2023-01-01T00:00:00", result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val workflowDto = mockk<WorkflowDto>()
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        val storeMethodDto = mockk<StoreMethodDto>()
        
        val dto = DataPointDto(
            id = 42L,
            workflow = workflowDto,
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = storeMethodDto,
            typeDeclaration = typeDeclarationDto,
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<DataPointDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowDao = mockk<WorkflowDao>()
        val storeMethod = mockk<StoreMethod>()
        val typeDeclarationDao = mockk<TypeDeclarationDao>()

        // Set up mocks for finding existing entity
        mockkObject(DataPointDao)
        every { DataPointDao.findById(42L) } returns existingEntity

        // Set up mocks for the mapper dependencies
        every { workflowMapper.toEntity(workflowDto) } returns workflowDao
        every { typeDeclarationMapper.toEntity(typeDeclarationDto) } returns typeDeclarationDao
        every { storeMethodMapper.toEntity(storeMethodDto) } returns storeMethod

        // Capture the property updates
        val workflowSlot = slot<WorkflowDao>()
        val nameSlot = slot<String>()
        val presetValueSlot = slot<String>()
        val storeMethodSlot = slot<StoreMethod>()
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val descriptionSlot = slot<String>()

        every { existingEntity.workflow = capture(workflowSlot) } just runs
        every { existingEntity.name = capture(nameSlot) } just runs
        every { existingEntity.presetValue = capture(presetValueSlot) } just runs
        every { existingEntity.storeMethod = capture(storeMethodSlot) } just runs
        every { existingEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { existingEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the existing entity was found and updated
        verify { DataPointDao.findById(42L) }
        verify { existingEntity.workflow = workflowDao }
        verify { existingEntity.name = "dataPointName" }
        verify { existingEntity.presetValue = "preset" }
        verify { existingEntity.storeMethod = storeMethod }
        verify { existingEntity.typeDeclaration = typeDeclarationDao }
        verify { existingEntity.description = "description" }

        // Verify the captured values
        assertEquals(workflowDao, workflowSlot.captured)
        assertEquals("dataPointName", nameSlot.captured)
        assertEquals("preset", presetValueSlot.captured)
        assertEquals(storeMethod, storeMethodSlot.captured)
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(DataPointDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val workflowDto = mockk<WorkflowDto>()
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        val storeMethodDto = mockk<StoreMethodDto>()
        
        val dto = DataPointDto(
            id = 0L,
            workflow = workflowDto,
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = storeMethodDto,
            typeDeclaration = typeDeclarationDto,
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<DataPointDao>(relaxed = true)

        // Mock the class's member dao types that would be returned from the mapper
        val workflowDao = mockk<WorkflowDao>()
        val storeMethodMock = mockk<StoreMethod>()
        val typeDeclarationDao = mockk<TypeDeclarationDao>()

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Set up mocks for the mapper dependencies
        every { workflowMapper.toEntity(workflowDto) } returns workflowDao
        every { typeDeclarationMapper.toEntity(typeDeclarationDto) } returns typeDeclarationDao
        every { storeMethodMapper.toEntity(storeMethodDto) } returns storeMethodMock

        // Capture property assignments
        val workflowSlot = slot<WorkflowDao>()
        val nameSlot = slot<String>()
        val presetValueSlot = slot<String>()
        val storeMethodSlot = slot<StoreMethod>()
        val typeDeclarationSlot = slot<TypeDeclarationDao>()
        val descriptionSlot = slot<String>()

        every { newEntity.workflow = capture(workflowSlot) } just runs
        every { newEntity.name = capture(nameSlot) } just runs
        every { newEntity.presetValue = capture(presetValueSlot) } just runs
        every { newEntity.storeMethod = capture(storeMethodSlot) } just runs
        every { newEntity.typeDeclaration = capture(typeDeclarationSlot) } just runs
        every { newEntity.description = capture(descriptionSlot) } just runs

        // Execute the method under test
        val result = mapper.toEntity(dto)

        // Verify that the factory was used to create a new entity
        verify { factory.create() }

        // Verify the properties were set correctly
        verify { newEntity.workflow = workflowDao }
        verify { newEntity.name = "dataPointName" }
        verify { newEntity.presetValue = "preset" }
        verify { newEntity.storeMethod = storeMethodMock }
        verify { newEntity.typeDeclaration = typeDeclarationDao }
        verify { newEntity.description = "description" }

        // Verify the captured values
        assertEquals(workflowDao, workflowSlot.captured)
        assertEquals("dataPointName", nameSlot.captured)
        assertEquals("preset", presetValueSlot.captured)
        assertEquals(storeMethodMock, storeMethodSlot.captured)
        assertEquals(typeDeclarationDao, typeDeclarationSlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}