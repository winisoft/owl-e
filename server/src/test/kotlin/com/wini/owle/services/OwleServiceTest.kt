package com.wini.owle.services

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.BaseDbMapper
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.OwleRepo
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.koin.core.module.Module
import org.koin.test.KoinTest
import kotlin.reflect.KClass


interface OwleServiceTest<ID: Any, T>: KoinTest where T : BaseDto {

    val testModule: Module
    fun getInstance(): T
    fun getService(): Service<ID, T>

}

@ExtendWith(MockKExtension::class)
abstract class OwleServiceUnitTest<ID: Any, Dto: BaseDto, E: Entity<ID>> {

    // Mocked dependencies
    @MockK
    protected lateinit var repository: OwleRepo<ID, E>

    @MockK
    protected lateinit var mapper: BaseDbMapper<Dto, E>

    // The service to be tested - should be initialized in the setup method
    protected abstract val service: Service<ID, Dto>

    // The entity class for creating EntityIDs
    protected abstract val entityClass: KClass<E>

    // The table name for error messages
    protected abstract val tableName: String

    // Sample data for testing
    protected abstract fun createSampleDto(): Dto
    protected abstract fun createSampleEntity(): E

    // Extract ID from a DTO and Entity
    protected abstract fun extractDtoId(dto: Dto): ID
    protected abstract fun extractEntityId(entity: E): ID

    // Create an EntityID for the repository
    protected abstract fun createEntityId(id: ID): EntityID<ID>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setupMocks()
    }

    // Setup common mocking behavior
    protected open fun setupMocks() {
        // Setup mapper mocks - now much simpler with regular methods
        every { mapper.toEntity(any()) } returns createSampleEntity()
        every { mapper.toDto(any()) } returns createSampleDto()
        every { mapper.toEntities(any()) } returns listOf(createSampleEntity())
        every { mapper.toDtos(any()) } returns listOf(createSampleDto())
    }

    @Test
    fun `test create success`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()

        every { mapper.toEntity(dto) } returns entity
        every { repository.create(entity) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.create(dto)

        // Assert
        assertTrue(result is AsyncResult.Success<*>)
        assertEquals(dto, (result as AsyncResult.Success<*>).data)

        // Verify
        verify { repository.create(entity) }
        verify { mapper.toEntity(dto) }
        verify { mapper.toDto(entity) }
    }

    @Test
    fun `test create failure`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()
        val exception = RuntimeException("Database error")

        every { mapper.run { dto.toEntity() } } returns entity
        every { repository.create(entity) } throws exception

        // Act
        val result = service.create(dto)

        // Assert
        assertTrue(result is AsyncResult.Failure)
        val error = (result as AsyncResult.Failure).error
        assertTrue(error is AsyncError.DatabaseError)
        assertEquals("Database error", error.message)

        // Verify
        verify { repository.create(entity) }
        verify { mapper.run { dto.toEntity() } }
    }

    @Test
    fun `test getById success`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()
        val id = extractDtoId(dto)
        val entityId = createEntityId(id)

        every { repository.entityClass.table } returns mockk()
        every { repository.getById(entityId) } returns entity
        every { mapper.run { entity.toDto() } } returns dto

        // Act
        val result = service.getById(id)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dto, (result as AsyncResult.Success).data)

        // Verify
        verify { repository.getById(entityId) }
        verify { mapper.run { entity.toDto() } }
    }

    @Test
    fun `test getById not found`() {
        // Arrange
        val dto = createSampleDto()
        val id = extractDtoId(dto)
        val entityId = createEntityId(id)

        every { repository.entityClass.table } returns mockk()
        every { repository.getById(entityId) } returns null

        // Act
        val result = service.getById(id)

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
        val entities = listOf(createSampleEntity(), createSampleEntity())
        val dtos = entities.map { createSampleDto() }

        every { repository.getAll(emptyList()) } returns entities
        for (i in entities.indices) {
            every { mapper.run { entities[i].toDto() } } returns dtos[i]
        }

        // Act
        val result = service.getAll()

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dtos.size, (result as AsyncResult.Success).data.size)

        // Verify
        verify { repository.getAll(emptyList()) }
        verify(exactly = entities.size) { mapper.run { any<E>().toDto() } }
    }

    @Test
    fun `test update success`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()

        every { mapper.run { dto.toEntity() } } returns entity
        every { repository.update(entity) } returns entity
        every { mapper.run { entity.toDto() } } returns dto

        // Act
        val result = service.update(dto)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dto, (result as AsyncResult.Success).data)

        // Verify
        verify { repository.update(entity) }
        verify { mapper.run { dto.toEntity() } }
        verify { mapper.run { entity.toDto() } }
    }

    @Test
    fun `test delete success`() {
        // Arrange
        val dto = createSampleDto()
        val id = extractDtoId(dto)
        val entityId = createEntityId(id)

        every { repository.entityClass.table } returns mockk()
        every { repository.delete(entityId) } just runs

        // Act
        val result = service.delete(id)

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
        val entities = dtos.map { createSampleEntity() }

        for (i in dtos.indices) {
            every { mapper.run { dtos[i].toEntity() } } returns entities[i]
            every { mapper.run { entities[i].toDto() } } returns dtos[i]
        }

        every { repository.create(any<List<E>>()) } returns entities

        // Act
        val result = service.create(dtos)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dtos.size, (result as AsyncResult.Success).data.size)

        // Verify
        verify { repository.create(any<List<E>>()) }
        verify(exactly = dtos.size) { mapper.run { any<Dto>().toEntity() } }
        verify(exactly = entities.size) { mapper.run { any<E>().toDto() } }
    }

    @Test
    fun `test batch update success`() {
        // Arrange
        val dtos = listOf(createSampleDto(), createSampleDto())
        val entities = dtos.map { createSampleEntity() }

        for (i in dtos.indices) {
            every { mapper.run { dtos[i].toEntity() } } returns entities[i]
            every { mapper.run { entities[i].toDto() } } returns dtos[i]
        }

        every { repository.update(any<List<E>>()) } returns entities

        // Act
        val result = service.update(dtos)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertEquals(dtos.size, (result as AsyncResult.Success).data.size)

        // Verify
        verify { repository.update(any<List<E>>()) }
        verify(exactly = dtos.size) { mapper.run { any<Dto>().toEntity() } }
        verify(exactly = entities.size) { mapper.run { any<E>().toDto() } }
    }

    @Test
    fun `test batch delete success`() {
        // Arrange
        val dtos = listOf(createSampleDto(), createSampleDto())
        val ids = dtos.map { extractDtoId(it) }
        val entityIds = ids.map { createEntityId(it) }

        every { repository.entityClass.table } returns mockk()
        every { repository.delete(any<List<EntityID<ID>>>()) } just runs

        // Act
        val result = service.delete(ids)

        // Assert
        assertTrue(result is AsyncResult.Success)
        assertTrue((result as AsyncResult.Success).data)

        // Verify
        verify { repository.delete(any<List<EntityID<ID>>>()) }
    }
}