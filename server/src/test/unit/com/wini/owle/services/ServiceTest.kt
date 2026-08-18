package com.wini.owle.services

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.entity.InstanceDbFakeEntityFactory
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.error.GenericError
import com.wini.owle.error.NoSuchIdError
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.GenericRepoImpl
import io.mockk.MockKMatcherScope
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


/**
 * The service-layer counterpart to [com.wini.owle.repo.GenericRepoTest]: one set of assertions
 * about what [GenericServiceImpl] does with its repository, mapper and delegate, run against
 * every type that has a service.
 *
 * It was declared generic but written against `T` with `as T` casts throughout,
 * so it only ever really tested one type and fought the compiler about it. The sample builders
 * are now abstract, which is what makes the class reusable.
 */
abstract class ServiceTest<ID: Any, T: BaseDto, E: Entity<ID>> {

    private lateinit var service: GenericServiceImpl<ID, T, E>
    private lateinit var repository: GenericRepoImpl<ID, E>
    private lateinit var mapper: DbMapper<ID, T, E>
    private lateinit var daoDelegate: DaoDelegate<ID, T, E>

    /** A DTO of the type under test. */
    abstract fun createSampleDto(): T

    /** A mocked entity of the type under test. */
    abstract fun createSampleEntity(): E

    /** An id of the type under test. `mockk`/`any` are reified and cannot take a type parameter. */
    abstract fun createSampleId(): ID

    /** The `any()` matcher for [ID], for the same reason. */
    abstract fun MockKMatcherScope.anyID(): ID

    @BeforeEach
    fun setUp() {
        repository = mockk()
        mapper = mockk()
        daoDelegate = mockk()
        service = GenericServiceImpl(repository, mapper, daoDelegate)
    }

    @Test
    fun `test create, when run succeeds, returns AsyncResult Success with valid data payload`() {
        // Arrange
        val dto: T = createSampleDto()
        val entity: E = createSampleEntity()

        every { mapper.toEntity(dto) } returns entity
        every { repository.create(entity) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.create(dto)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success<*>)
        Assertions.assertEquals(dto, (result as AsyncResult.Success<*>).data)

        // Verify
        verify(exactly = 1) { repository.create(entity) }
        verify(exactly = 1) { mapper.toEntity(dto) }
        verify(exactly = 1) { mapper.toDto(entity) }
    }

    @Test
    fun `test create, when failure occurs, returns AsyncResult Failure with DatabaseError `() {
        // Arrange
        val dto: T = createSampleDto()
        val entity: E = createSampleEntity()
        val exception = RuntimeException("Database error")

        every { mapper.toEntity(dto) } returns entity
        every { repository.create(entity) } throws exception

        // Act
        val result = service.create(dto)

        // Assert
        verify(exactly = 1) { mapper.toEntity(dto) }
        verify(exactly = 1) { repository.create(entity) }

        Assertions.assertTrue(result is AsyncResult.Failure)

        val error = (result as AsyncResult.Failure).error
        Assertions.assertTrue(error is GenericError)
        Assertions.assertEquals("Database error", error.detail)
    }

    @Test
    fun `test getById success`() {
        // Arrange
        val dto: T = createSampleDto()
        val entityId: EntityID<ID> = mockk() {
            every { value } returns createSampleId()
        }
        val entity: E = createSampleEntity()

        every { mapper.toEntity(dto) } returns entity
        every { daoDelegate.buildEntityId(anyID()) } returns entityId
        every { repository.getById(entityId) } returns entity
        every { mapper.toDto(entity) } returns dto

        // Act
        val result = service.getById(entityId.value)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(dto, (result as AsyncResult.Success).data)

        // Verify
        verify(exactly = 1) { repository.getById(entityId) }
        verify(exactly = 1) { mapper.toDto(entity) }
    }

    @Test
    fun `test getById not found`() {
        // Arrange
        val dto = createSampleDto()
        val entity = createSampleEntity()
        val entityId: EntityID<ID> = mockk() {
            every { value } returns createSampleId()
        }
        every { repository.entityClass.table }
        every { daoDelegate.buildEntityId(anyID()) } returns entityId
        every { repository.getById(entityId) } returns null

        // Act
        val result = service.getById(entityId.value)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Failure)
        val error = (result as AsyncResult.Failure).error
        Assertions.assertTrue(error is NoSuchIdError)

        // Verify
        verify { repository.getById(entityId) }
    }

    @Test
    fun `test getAll success`() {
        // Arrange
        val dtos = listOf(createSampleDto(), createSampleDto())
        val entities: List<E> = listOf(createSampleEntity())

        every { mapper.toEntities(dtos) } returns entities
        every { mapper.toDtos(entities) } returns dtos
        every { repository.getAll(emptyList()) } returns entities

        // Act
        val result = service.getAll()

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(dtos, (result as AsyncResult.Success).data)

        // Verify
        verify(exactly = 1) { repository.getAll(emptyList()) }
        verify(exactly = 1) { mapper.toDtos(entities) }
    }

    @Test
    fun `test getAll returns empty list when no entities exist`() {
        // Arrange
        val emptyList = emptyList<E>()
        val emptyDtoList = emptyList<T>()

        every { repository.getAll(emptyList()) } returns emptyList
        every { mapper.toDtos(emptyList) } returns emptyDtoList

        // Act
        val result = service.getAll()

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertTrue((result as AsyncResult.Success).data.isEmpty())

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
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(dto, (result as AsyncResult.Success).data)

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
        val entityId: EntityID<ID> = mockk() {
            every { value } returns createSampleId()
        }
        val entityClass: EntityClass<ID, E> = mockk()
        val id = 1UL

        every { entity.id } returns entityId
        every { mapper.toEntity(dto) } returns entity
        every { daoDelegate.buildEntityId(entityId.value) } returns entityId
        every { repository.entityClass } returns entityClass
        every { entityClass.findById(entityId) } returns entity
        every { repository.getById(entityId) } returns createSampleEntity()
        every { repository.delete(entityId) } just runs

        // Act
        val result = service.delete(entityId.value)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(Unit, (result as AsyncResult.Success).data)

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
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(dtos, (result as AsyncResult.Success).data)

        // Verify
        verify(exactly = 1) { repository.create(entities) }
        verify(exactly = 1) { mapper.toEntities(dtos) }
        verify(exactly = 1) { mapper.toDtos(entities) }
    }

    @Test
    fun `test batch create with empty list`() {
        // Arrange
        val emptyDtoList = emptyList<T>()
        val emptyEntityList = emptyList<E>()

        every { mapper.toEntities(emptyDtoList) } returns emptyEntityList
        every { repository.create(emptyEntityList) } returns emptyEntityList
        every { mapper.toDtos(emptyEntityList) } returns emptyDtoList

        // Act
        val result = service.create(emptyDtoList)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertTrue((result as AsyncResult.Success).data.isEmpty())

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
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals((result as AsyncResult.Success).data, dtos)

        // Verify
        verify(exactly = 1) { repository.update(entities) }
        verify(exactly = 1) { mapper.toEntities(dtos) }
        verify(exactly = 1) { mapper.toDtos(entities) }
    }

    @Test
    fun `test batch delete success`() {
        val compositeIds = listOf(createSampleId(), createSampleId())
        val entityIds = listOf<EntityID<ID>>(
            mockk { every { value } returns createSampleId() },
            mockk { every { value } returns createSampleId() }
        )
        every { daoDelegate.buildEntityId(anyID()) }.returnsMany(entityIds[0], entityIds[1])
        every { repository.delete(entityIds) } just runs

        // Act
        val result = service.delete(compositeIds)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(Unit, (result as AsyncResult.Success).data)

        // Verify
        verify { daoDelegate.buildEntityId(compositeIds[0]) }
        verify { daoDelegate.buildEntityId(compositeIds[1]) }
        verify { repository.delete(entityIds) }
    }
}
