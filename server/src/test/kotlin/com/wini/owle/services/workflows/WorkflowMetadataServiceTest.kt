package com.wini.owle.services.workflows

import com.wini.owle.error.OwleError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.workflows.enums.AuthorshipCreditDto
import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.dto.workflows.enums.ModificationTermsDto
import com.wini.owle.entity.workflows.WorkflowMetadataEntity
import com.wini.owle.error.NoSuchIdError
import com.wini.owle.mapper.workflows.WorkflowMetadataMapper
import com.wini.owle.repo.WorkflowMetadataIdFactory
import com.wini.owle.repo.workflows.WorkflowMetadataRepository
import io.mockk.*
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


open class WorkflowMetadataServiceTest {

    private lateinit var service: WorkflowMetadataService
    private lateinit var repository: WorkflowMetadataRepository
    private lateinit var mapper: WorkflowMetadataMapper
    private lateinit var idFactory: WorkflowMetadataIdFactory

    fun createSampleDto(
        id: Long = 1L,
        name: String = "John Arbuckle",
        version: String = "1.0.0",
        contributors: List<User> = mockk(),
        copyrightInformation:String = "2025",
        contactInformation: String = "lasagnalonely@email.com",
        thirdPartyLicenses: String = "Ok",
        distributionVersion: String = "1.0.0",
        distributionAcceptedAt: String = "",
        distributionUpdatedAt: String = "",
        discretionAdvisedContent: Boolean = true,
        commercialUsage: CommercialUse = mockk(),
        authorshipCredit: AuthorshipCreditDto = mockk(),
        allowRedistribution: Boolean = false,
        modification: ModificationTermsDto = mockk(),
        excludedSteps: List<Step> = mockk()
    ): WorkflowMetadata {
        return WorkflowMetadata(
            id = id,
            name = name,
            version = version,
            contributors = contributors,
            copyrightInformation = copyrightInformation,
            contactInformation = contactInformation,
            thirdPartyLicenses = thirdPartyLicenses,
            discretionAdvisedContent = discretionAdvisedContent,
            commercialUsage = commercialUsage,
            creditNotation = authorshipCredit,
            allowRedistribution = allowRedistribution,
            modification = modification,
            distributionVersion = distributionVersion,
            distributionAcceptedAt = distributionAcceptedAt,
            distributionUpdatedAt = distributionUpdatedAt,
            excludedSteps = excludedSteps
        )
    }

    fun createSampleEntity(): WorkflowMetadataEntity = mockk<WorkflowMetadataEntity>() {
        every { id } returns mockk()
        every { name } returns "John Arbuckle"
        every { version } returns "1.0.0"
        every { copyrightInformation } returns "2025"
        every { contactInformation } returns "lasagnalonely@email.com"
        every { thirdPartyLicenses } returns "Ok"
        every { distributionVersion } returns "1.0.0"
        every { distributionAcceptedAt } returns mockk()
        every { distributionUpdatedAt } returns mockk()
        every { discretionAdvisedContent } returns true
        every { commercialUsage } returns mockk()
        every { allowRedistribution } returns false
        every { modification } returns mockk()
    }

    @BeforeEach
    fun setUp() {
        repository = mockk() 
        mapper = mockk() 
        idFactory = mockk()
        service = WorkflowMetadataService(repository, mapper, idFactory)
    }

    @Test
    fun `test create, when run succeeds, returns AsyncResult Success with valid data payload`() {
        // Arrange
        val dto: WorkflowMetadata = createSampleDto()
        val entity: WorkflowMetadataEntity = mockk()

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
        val dto: WorkflowMetadata = mockk()
        val entity: WorkflowMetadataEntity = mockk()
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
        assertTrue(error is OwleError.DatabaseError.Generic)
        assertEquals("The operation could not be completed.", error.message)
    }

    @Test
    fun `test getById success`() {
        // Arrange
        val dto: WorkflowMetadata = createSampleDto()
        val entityId: EntityID<Long> = mockk()
        val entity: WorkflowMetadataEntity = mockk<WorkflowMetadataEntity>() {
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
        assertTrue(error is NoSuchIdError)

        // Verify
        verify { repository.getById(entityId) }
    }

    @Test
    fun `test getAll success`() {
        // Arrange
        val dtos = listOf(createSampleDto(1L), createSampleDto(2L))
        val entities: List<WorkflowMetadataEntity> = mockk()

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
        val emptyList = emptyList<WorkflowMetadataEntity>()
        val emptyDtoList = emptyList<WorkflowMetadata>()

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
        val entityClass: EntityClass<Long, WorkflowMetadataEntity> = mockk()
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
        assertEquals(Unit, (result as AsyncResult.Success).data)

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
        val emptyDtoList = emptyList<WorkflowMetadata>()
        val emptyEntityList = emptyList<WorkflowMetadataEntity>()

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
        assertEquals(Unit, (result as AsyncResult.Success).data)

        // Verify
        verify { idFactory.build(1L) }
        verify { idFactory.build(2L) }
        verify { repository.delete(entityIds) }
    }
}