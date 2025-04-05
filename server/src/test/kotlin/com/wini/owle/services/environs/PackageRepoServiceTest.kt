package com.wini.owle.services.environs

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.PackageRepoIdFactory
import com.wini.owle.repo.environs.PackageRepoRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


open class PackageRepoServiceTest {

    private lateinit var service: PackageRepoService
    @MockK private lateinit var repository: PackageRepoRepository
    @MockK private lateinit var mapper: PackageRepoMapper
    @MockK private lateinit var idFactory: PackageRepoIdFactory

    fun createSampleDto(mockId: Long = 1L): PackageRepoDto = PackageRepoDto(
        id = mockId,

    )

    fun createSampleEntity(): PackageRepoDao = mockk<PackageRepoDao>() {
        every { id } returns mockk()

    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        service = PackageRepoService(repository, mapper, idFactory)
    }

    @Test
    fun `test create, when run succeeds, returns AsyncResult Success with valid data payload`() {
        // Arrange
        val dto: PackageRepoDto = mockk()
        val entity: PackageRepoDao = mockk()

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
        val dto: PackageRepoDto = mockk()
        val entity: PackageRepoDao = mockk()
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
        val dto: PackageRepoDto = createSampleDto()
        val entityId: EntityID<Long> = mockk()
        val entity: PackageRepoDao = mockk<PackageRepoDao>() {
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
        val entities: List<PackageRepoDao> = mockk()

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
        val emptyList = emptyList<PackageRepoDao>()
        val emptyDtoList = emptyList<PackageRepoDto>()

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
        val entityClass: EntityClass<Long, PackageRepoDao> = mockk()
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
        val emptyDtoList = emptyList<PackageRepoDto>()
        val emptyEntityList = emptyList<PackageRepoDao>()

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