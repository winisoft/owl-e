package com.wini.owle.services.steps

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.mapper.steps.ProvisionDataPointMapper
import com.wini.owle.repo.ProvisionDataPointIdFactory
import com.wini.owle.repo.steps.ProvisionDataPointRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


open class ProvisionDataPointServiceTest {

    private lateinit var service: ProvisionDataPointService
    private lateinit var repository: ProvisionDataPointRepository
    private lateinit var mapper: ProvisionDataPointMapper
    private lateinit var idFactory: ProvisionDataPointIdFactory

    fun createSampleDto(
        provisionId: Long = 1L,
        dataPointId: Long = 2L
    ): ProvisionDataPointDto = ProvisionDataPointDto(
        provisionId = provisionId,
        dataPointId = dataPointId
    )

    fun createSampleEntity(): ProvisionDataPointDao = mockk<ProvisionDataPointDao>() {
        every { id } returns mockk()
    }

    @BeforeEach
    fun setUp() {
        repository = mockk() 
        mapper = mockk() 
        idFactory = mockk()
        service = ProvisionDataPointService(repository, mapper, idFactory)
    }

    @Test
    fun `test create, when run succeeds, returns AsyncResult Success with valid data payload`() {
        // Arrange
        val dto: ProvisionDataPointDto = mockk()
        val entity: ProvisionDataPointDao = mockk()

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
        val dto: ProvisionDataPointDto = mockk()
        val entity: ProvisionDataPointDao = mockk()
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
        val dto: ProvisionDataPointDto = createSampleDto()
        val entityId: EntityID<CompositeID> = mockk() {
            every { value } returns mockk()
        }
        val entity: ProvisionDataPointDao = mockk<ProvisionDataPointDao>() {
            every { id } returns mockk()
        }

        every { mapper.toEntity(dto) } returns entity
        every { idFactory.build(any<CompositeID>()) } returns entityId
        every { repository.getById(entityId) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.getById(entityId.value)

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
        val entityId: EntityID<CompositeID> = mockk() {
            every { value } returns mockk()
        }
        every { repository.entityClass.table }
        every { idFactory.build(any()) } returns entityId
        every { repository.getById(entityId) } returns null

        // Act
        val result = service.getById(entityId.value)

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
        val dtos = listOf(createSampleDto(), createSampleDto())
        val entities: List<ProvisionDataPointDao> = mockk()

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
        val emptyList = emptyList<ProvisionDataPointDao>()
        val emptyDtoList = emptyList<ProvisionDataPointDto>()

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
        val entityId: EntityID<CompositeID> = mockk() {
            every { value } returns mockk()
        }
        val entityClass: EntityClass<CompositeID, ProvisionDataPointDao> = mockk()
        val id = 1L

        every { entity.id } returns entityId
        every { mapper.toEntity(dto) } returns entity
        every { idFactory.build(entityId.value) } returns entityId
        every { repository.entityClass } returns entityClass
        every { entityClass.findById(entityId) } returns entity
        every { repository.getById(entityId) } returns mockk()
        every { repository.delete(entityId) } just runs

        // Act
        val result = service.delete(entityId.value)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data)

        // Verify
        verify { repository.delete(entityId) }
    }

    @Test
    fun `test batch create success`() {
        // Arrange
        val dtos = listOf(createSampleDto(), createSampleDto())
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
        val emptyDtoList = emptyList<ProvisionDataPointDto>()
        val emptyEntityList = emptyList<ProvisionDataPointDao>()

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
        val dtos = listOf(createSampleDto(), createSampleDto())
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
        val compositeIds = listOf<CompositeID>(mockk(), mockk())
        val entityIds = listOf<EntityID<CompositeID>>(
            mockk { every { value } returns mockk() },
            mockk { every { value } returns mockk() }
        )
        every { idFactory.build(any<CompositeID>()) }.returnsMany(entityIds[0], entityIds[1])
        every { repository.delete(any<List<EntityID<CompositeID>>>()) } just runs

        // Act
        val result = service.delete(compositeIds)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data)

        // Verify
        verify { idFactory.build(compositeIds[0]) }
        verify { idFactory.build(compositeIds[1]) }
        verify { repository.delete(entityIds) }
    }
    
}