package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
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
    private val factory: DaoFactory<DataPointDao, Long> = mockk()

    @BeforeEach
    fun setUp() {
        mapper = DataPointsMapper(factory, typeDeclarationMapper, workflowMapper)
    }

    @Test
    fun `toEntity should throw exception when id is non-zero but entity not found`() {
        // Create test DTOs
        val dto = DataPointDto(id = 99L, workflow = mockk(), name = "dataPointName", presetValue = "preset", storeMethod = mockk(), typeDeclaration = mockk(), description = "description", createdAt = "2023-01-01T00:00:00", updatedAt = "2023-01-01T00:00:00")

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
        val entity = mockk<DataPointDao> {
            every { id.value } returns 1L
            every { workflow } returns mockk()
            every { name } returns "dataPointName"
            every { presetValue } returns "preset"
            every { storeMethod } returns mockk()
            every { typeDeclaration } returns mockk()
            every { description } returns "description"
            every { createdAt } returns mockk()
            every { updatedAt } returns mockk()
        }

        // Mock the behavior of typeDeclarationMapper and workflowMapper
        val workflowDto = mockk<WorkflowDto>()
        val typeDeclarationDto = mockk<TypeDeclarationDto>()
        every { workflowMapper.toDto(entity.workflow) } returns workflowDto
        every { typeDeclarationMapper.toDto(entity.typeDeclaration) } returns typeDeclarationDto

        // Call the method under test
        val result = mapper.toDto(entity)

        // Verify the result
        assertEquals(1L, result.id)
        assertEquals(workflowDto, result.workflow)
        assertEquals("dataPointName", result.name)
        assertEquals("preset", result.presetValue)
        assertEquals(entity.storeMethod.toDto(), result.storeMethod)
        assertEquals(typeDeclarationDto, result.typeDeclaration)
        assertEquals("description", result.description)
        assertEquals(entity.createdAt.toString(), result.createdAt)
        assertEquals(entity.updatedAt.toString(), result.updatedAt)
    }

    @Test
    fun `toEntity should update existing entity when id is non-zero`() {
        // Create test DTOs
        val dto = DataPointDto(
            id = 42L,
            workflow = mockk(),
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = mockk(),
            typeDeclaration = mockk(),
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the existing entity that will be updated
        val existingEntity = mockk<DataPointDao>(relaxed = true)

        // Set up mocks for finding existing entity
        mockkObject(DataPointDao)
        every { DataPointDao.findById(42L) } returns existingEntity

        // Capture the property updates
        val workflowSlot = slot<Workflows>()
        val nameSlot = slot<String>()
        val presetValueSlot = slot<String>()
        val storeMethodSlot = slot<StoreMethod>()
        val typeDeclarationSlot = slot<TypeDeclarations>()
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
        verify { existingEntity.workflow = dto.workflow }
        verify { existingEntity.name = "dataPointName" }
        verify { existingEntity.presetValue = "preset" }
        verify { existingEntity.storeMethod = dto.storeMethod }
        verify { existingEntity.typeDeclaration = dto.typeDeclaration }
        verify { existingEntity.description = "description" }

        // Verify the captured values
        assertEquals(dto.workflow, workflowSlot.captured)
        assertEquals("dataPointName", nameSlot.captured)
        assertEquals("preset", presetValueSlot.captured)
        assertEquals(dto.storeMethod, storeMethodSlot.captured)
        assertEquals(dto.typeDeclaration, typeDeclarationSlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the updated entity
        assertEquals(existingEntity, result)

        unmockkObject(DataPointDao)
    }

    @Test
    fun `toEntity should create a correctly mapped entity when id is zero`() {
        // Create test DTOs
        val dto = DataPointDto(
            id = 0L,
            workflow = mockk(),
            name = "dataPointName",
            presetValue = "preset",
            storeMethod = mockk(),
            typeDeclaration = mockk(),
            description = "description",
            createdAt = "2023-01-01T00:00:00",
            updatedAt = "2023-01-01T00:00:00"
        )

        // Mock the new entity that will be created
        val newEntity = mockk<DataPointDao>(relaxed = true)

        // Set up the factory mock to return our mock entity
        every { factory.create() } returns newEntity

        // Capture property assignments
        val workflowSlot = slot<Workflows>()
        val nameSlot = slot<String>()
        val presetValueSlot = slot<String>()
        val storeMethodSlot = slot<StoreMethod>()
        val typeDeclarationSlot = slot<TypeDeclarations>()
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
        verify { newEntity.workflow = dto.workflow }
        verify { newEntity.name = "dataPointName" }
        verify { newEntity.presetValue = "preset" }
        verify { newEntity.storeMethod = dto.storeMethod }
        verify { newEntity.typeDeclaration = dto.typeDeclaration }
        verify { newEntity.description = "description" }

        // Verify the captured values
        assertEquals(dto.workflow, workflowSlot.captured)
        assertEquals("dataPointName", nameSlot.captured)
        assertEquals("preset", presetValueSlot.captured)
        assertEquals(dto.storeMethod, storeMethodSlot.captured)
        assertEquals(dto.typeDeclaration, typeDeclarationSlot.captured)
        assertEquals("description", descriptionSlot.captured)

        // Verify the result is the new entity
        assertEquals(newEntity, result)
    }
}