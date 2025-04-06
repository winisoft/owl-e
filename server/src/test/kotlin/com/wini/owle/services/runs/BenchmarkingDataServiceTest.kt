package com.wini.owle.services.runs

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.BenchmarkingDataDao
import com.wini.owle.mapper.runs.BenchmarkingDataMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.BenchmarkingDataIdFactory
import com.wini.owle.repo.runs.BenchmarkingDataRepository
import com.wini.owle.services.runs.BenchmarkingDataService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal


open class BenchmarkingDataServiceTest {

    private lateinit var service: BenchmarkingDataService
    private lateinit var repository: BenchmarkingDataRepository
    private lateinit var mapper: BenchmarkingDataMapper
    private lateinit var idFactory: BenchmarkingDataIdFactory

    fun createSampleDto(
        mockId: Long = 1L,
        workflowExecution: WorkflowExecutionDto = mockk(),
        totalExecutionTime: Double = 123.456,
        totalSteps: Int = 99,
        aiDeterminedSteps: Int = 88,
        predeterminedSteps: Int = 77
    ): BenchmarkingDataDto = BenchmarkingDataDto(
        id = mockId,
        workflowExecution = workflowExecution,
        totalExecutionTime = totalExecutionTime,
        totalSteps = totalSteps,
        aiDeterminedSteps = aiDeterminedSteps,
        predeterminedSteps = predeterminedSteps
    )

    fun createSampleEntity(): BenchmarkingDataDao = mockk<BenchmarkingDataDao>() {
        every { id } returns mockk()
        every { workflowExecution } returns mockk()
        every { totalExecutionTime } returns BigDecimal(123.456)
        every { totalSteps } returns 99
        every { aiDeterminedSteps } returns 88
        every { predeterminedSteps } returns 77
    }

    @BeforeEach
    fun setUp() {
        repository = mockk() 
        mapper = mockk() 
        idFactory = mockk()
        service = BenchmarkingDataService(repository, mapper, idFactory)
    }

    @Test
    fun `test create, when run succeeds, returns AsyncResult Success with valid data payload`() {
        // Arrange
        val dto: BenchmarkingDataDto = mockk()
        val entity: BenchmarkingDataDao = mockk()

        every { mapper.toEntity(dto) } returns entity
        every { repository.create(entity) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.create(dto)

        // Assert
        assertTrue(result is AsyncResult.Success<*>)
        assertEquals(dto, (result as AsyncResult.Success<*>).data)

        // Verify
        verify(exactly = 1) { repository.create(entity) }
        verify(exactly = 1) { mapper.toEntity(dto) }
        verify(exactly = 1) { mapper.toDto(entity) }
    }

    @Test
    fun `test create, when failure occurs, returns AsyncResult Failure with DatabaseError `() {
        // Arrange
        val dto: BenchmarkingDataDto = mockk()
        val entity: BenchmarkingDataDao = mockk()
        val exception = RuntimeException("Database error")

        every { mapper.toEntity(dto) } returns entity
        every { repository.create(entity) } throws exception

        // Act
        val result = service.create(dto)

        // Assert
        verify(exactly = 1) { mapper.toEntity(dto) }
        verify(exactly = 1) { repository.create(entity) }

        assertTrue(result is AsyncResult.Failure)

        val error = (result as AsyncResult.Failure).error
        assertTrue(error is AsyncError.DatabaseError)
        assertEquals("Database error", error.message)
    }

    @Test
    fun `test getById success`() {
        // Arrange
        val dto: BenchmarkingDataDto = createSampleDto()
        val entityId: EntityID<Long> = mockk()
        val entity: BenchmarkingDataDao = mockk<BenchmarkingDataDao>() {
            every { id } returns mockk()
        }

        every { mapper.toEntity(dto) } returns entity
        every { idFactory.build(any<Long>()) } returns entityId
        every { repository.getById(entityId) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.getById(dto.id)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dto, (result as AsyncResult.Success).data)

        // Verify
        verify(exactly = 1) { repository.getById(entityId) }
        verify(exactly = 1) { mapper.toDto(entity) }
    }

    @Test
    fun `test getById not found`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()
        val entityId: EntityID<Long> = mockk()

        every { entityId.value } returns dto.id
        every { repository.entityClass.table }
        every { idFactory.build(any()) } returns entityId
        every { repository.getById(entityId) } returns null

        // Act
        val result = service.getById(dto.id)

        // Assert
        assertTrue(result is AsyncResult.Failure)
        val error = (result as AsyncResult.Failure).error
        assertTrue(error is AsyncError.NoSuchIdError)

        // Verify
        verify { repository.getById(entityId) }
    }

    @Test
    fun `test getAll success`() {
        // Arrange
        val dtos = listOf(createSampleDto(1L), createSampleDto(2L))
        val entities: List<BenchmarkingDataDao> = mockk()

        every { mapper.toEntities(dtos) } returns entities
        every { mapper.toDtos(entities) } returns dtos
        every { repository.getAll(emptyList()) } returns entities

        // Act
        val result = service.getAll()

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dtos, (result as AsyncResult.Success).data)

        // Verify
        verify(exactly = 1) { repository.getAll(emptyList()) }
        verify(exactly = 1) { mapper.toDtos(entities) }
    }

    @Test
    fun `test getAll returns empty list when no entities exist`() {
        // Arrange
        val emptyList = emptyList<BenchmarkingDataDao>()
        val emptyDtoList = emptyList<BenchmarkingDataDto>()

        every { repository.getAll(emptyList()) } returns emptyList
        every { mapper.toDtos(emptyList) } returns emptyDtoList

        // Act
        val result = service.getAll()

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data.isEmpty())

        // Verify
        verify(exactly = 1) { repository.getAll(emptyList()) }
        verify(exactly = 1) { mapper.toDtos(emptyList) }
    }

    @Test
    fun `test update success`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()

        every { mapper.toEntity(dto) } returns entity
        every { repository.update(entity) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.update(dto)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dto, (result as AsyncResult.Success).data)

        // Verify
        verify { repository.update(entity) }
        verify { mapper.toEntity(dto) }
        verify { mapper.toDto(entity) }
    }

    @Test
    fun `test delete success`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()
        val entityClass: EntityClass<Long, BenchmarkingDataDao> = mockk()
        val id = 1L
        val entityId: EntityID<Long> = mockk()

        every { entity.id } returns entityId
        every { entityId.value } returns id
        every { mapper.toEntity(dto) } returns entity
        every { idFactory.build(id) } returns entityId
        every { repository.entityClass } returns entityClass
        every { entityClass.findById(entityId) } returns entity
        every { repository.getById(entityId) } returns mockk()
        every { repository.delete(entityId) } just runs

        // Act
        val result = service.delete(dto.id)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data)

        // Verify
        verify { repository.delete(entityId) }
    }

    @Test
    fun `test batch create success`() {
        // Arrange
        val dtos = listOf(createSampleDto(1L), createSampleDto(2L))
        val entities = listOf(createSampleEntity(), createSampleEntity())

        every { mapper.toEntities(dtos) } returns entities
        every { mapper.toDtos(entities) } returns dtos
        every { repository.create(entities) } returns entities

        // Act
        val result = service.create(dtos)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dtos, (result as AsyncResult.Success).data)

        // Verify
        verify(exactly = 1) { repository.create(entities) }
        verify(exactly = 1) { mapper.toEntities(dtos) }
        verify(exactly = 1) { mapper.toDtos(entities) }
    }

    @Test
    fun `test batch create with empty list`() {
        // Arrange
        val emptyDtoList = emptyList<BenchmarkingDataDto>()
        val emptyEntityList = emptyList<BenchmarkingDataDao>()

        every { mapper.toEntities(emptyDtoList) } returns emptyEntityList
        every { repository.create(emptyEntityList) } returns emptyEntityList
        every { mapper.toDtos(emptyEntityList) } returns emptyDtoList

        // Act
        val result = service.create(emptyDtoList)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data.isEmpty())

        // Verify
        verify(exactly = 1) { repository.create(emptyEntityList) }
        verify(exactly = 1) { mapper.toEntities(emptyDtoList) }
        verify(exactly = 1) { mapper.toDtos(emptyEntityList) }
    }

    @Test
    fun `test batch update success`() {
        // Arrange
        val dtos = listOf(createSampleDto(1L), createSampleDto(2L))
        val entities = listOf(createSampleEntity(), createSampleEntity())

        every { mapper.toEntities(dtos) } returns entities
        every { mapper.toDtos(entities) } returns dtos
        every { repository.update(entities) } returns entities

        // Act
        val result = service.update(dtos)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals((result as AsyncResult.Success).data, dtos)

        // Verify
        verify(exactly = 1) { repository.update(entities) }
        verify(exactly = 1) { mapper.toEntities(dtos) }
        verify(exactly = 1) { mapper.toDtos(entities) }
    }

    @Test
    fun `test batch delete success`() {
        // Arrange
        val ids = listOf(1L, 2L)
        val entityIds = listOf<EntityID<Long>>(mockk(), mockk())

        every { idFactory.build(1L) } returns entityIds[0]
        every { idFactory.build(2L) } returns entityIds[1]
        every { repository.delete(entityIds) } just runs

        // Act
        val result = service.delete(ids)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data)

        // Verify
        verify { idFactory.build(1L) }
        verify { idFactory.build(2L) }
        verify { repository.delete(entityIds) }
    }
}