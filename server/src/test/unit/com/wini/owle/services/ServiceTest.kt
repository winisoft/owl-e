package com.wini.owle.services

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.entity.InstanceDbFakeEntityFactory
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.error.GenericError
import com.wini.owle.error.NoSuchIdError
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.GenericRepoImpl
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


open class ServiceTest<ID: Any, T: BaseDto, E: Entity<ID>> {

    private lateinit var service: GenericServiceImpl<ID, T, E>
    private lateinit var repository: GenericRepoImpl<ID, E>
    private lateinit var mapper: DbMapper<ID, T, E>
    private lateinit var daoDelegate: DaoDelegate<ID, T, E>

    fun createSampleDto(): EnvMarkerComparison = DtoTestInstanceFactory.environs.envMarkerComparison()

    fun createSampleEntity(): EnvMarkerComparisonEntity = mockk<EnvMarkerComparisonEntity>() {
        every { id } returns mockk {
            every { value } returns mockk<ULong>()
        }
    }

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
        val dto: EnvMarkerComparison = mockk()
        val entity: EnvMarkerComparisonEntity = mockk()

        every { mapper.toEntity(dto as T) } returns entity as E
        every { repository.create(entity) } returns entity
        every { mapper.toDto(entity) } returns dto as T

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
        val dto: EnvMarkerComparison = mockk()
        val entity: EnvMarkerComparisonEntity = mockk()
        val exception = RuntimeException("Database error")

        every { mapper.toEntity(dto as T) } returns entity as E
        every { repository.create(entity) } throws exception

        // Act
        val result = service.create(dto as T)

        // Assert
        verify(exactly = 1) { mapper.toEntity(dto) }
        verify(exactly = 1) { repository.create(entity) }

        Assertions.assertTrue(result is AsyncResult.Failure)

        val error = (result as AsyncResult.Failure).error
        Assertions.assertTrue(error is GenericError)
        Assertions.assertEquals("The operation could not be completed.", error.message)
    }

    @Test
    fun `test getById success`() {
        // Arrange
        val dto: EnvMarkerComparison = DtoTestInstanceFactory.environs.envMarkerComparison()
        val entityId: EntityID<ULong> = mockk() {
            every { value } returns mockk()
        }
        val entity: EnvMarkerComparisonEntity = mockk<EnvMarkerComparisonEntity>() {
            every { id } returns mockk()
        }

        every { mapper.toEntity(dto) } returns entity
        every { idFactory.build(any<ULong>()) } returns entityId
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
        val entityId: EntityID<ULong> = mockk() {
            every { value } returns mockk()
        }
        every { repository.entityClass.table }
        every { idFactory.build(any()) } returns entityId
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
        val dtos = listOf(DtoTestInstanceFactory.environs.envMarkerComparison(1UL), DtoTestInstanceFactory.environs.envMarkerComparison(2UL))
        val entities: List<EnvMarkerComparisonEntity> = mockk()

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
        val emptyList = emptyList<EnvMarkerComparisonEntity>()
        val emptyDtoList = emptyList<EnvMarkerComparison>()

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
        val entityId: EntityID<ULong> = mockk() {
            every { value } returns mockk()
        }
        val entityClass: EntityClass<ULong, EnvMarkerComparisonEntity> = mockk()
        val id = 1UL

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
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(Unit, (result as AsyncResult.Success).data)

        // Verify
        verify { repository.delete(entityId) }
    }

    @Test
    fun `test batch create success`() {
        // Arrange
        val dtos = listOf(envMarkerComparison(1UL), envMarkerComparison(2UL))
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
        val emptyDtoList = emptyList<EnvMarkerComparison>()
        val emptyEntityList = emptyList<EnvMarkerComparisonEntity>()

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
        val dtos = listOf(envMarkerComparison(1UL), envMarkerComparison(2UL))
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
        val compositeIds = listOf<ULong>(mockk(), mockk())
        val entityIds = listOf<EntityID<ULong>>(
            mockk { every { value } returns mockk() },
            mockk { every { value } returns mockk() }
        )
        every { idFactory.build(any<ULong>()) }.returnsMany(entityIds[0], entityIds[1])
        every { repository.delete(any<List<EntityID<ULong>>>()) } just runs

        // Act
        val result = service.delete(compositeIds)

        // Assert
        Assertions.assertTrue(result is AsyncResult.Success)
        Assertions.assertEquals(Unit, (result as AsyncResult.Success).data)

        // Verify
        verify { idFactory.build(compositeIds[0]) }
        verify { idFactory.build(compositeIds[1]) }
        verify { repository.delete(entityIds) }
    }
}
