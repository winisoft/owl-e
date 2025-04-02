package com.wini.owle.services.typing

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.TypeDeclarationDto
import com.wini.owle.dto.TypeDefinitionDto
import com.wini.owle.dto.VersionSpecDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.mapper.mapperModule
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.typing.TypeDefinitionsMapper
import com.wini.owle.repo.typing.TypeDeclarationRepository
import com.wini.owle.services.serviceModule
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue


/**
 * Unit tests for [TypeDeclarationService].
 */
open class TypeDeclarationServiceTest {

    /**
     * Mock repository to simulate success/failure and capture arguments.
     */
    private val mockRepo = mockk<TypeDeclarationRepository>(relaxed = true)

    /**
     * Mock mapper to test correct conversion from Dao <-> Dto.
     */
    private val mockMapper = mockk<TypeDeclarationsMapper>(relaxed = true)

    /**
     * The service under test, using the mocked repository and mapper.
     */
    private val service = TypeDeclarationService(mockRepo, mockMapper)

    protected val testModule: Module get() = module { includes(serviceModule) }

    val implicatedTables: List<IdTable<*>> = listOf(TypeDeclarations, TypeDefinitions, VersionSpecs)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        stopKoin()
        startKoin { modules(testModule) }

    }

    /**
     * Test that getById returns a Success when the repository returns an entity,
     * and that the entity is converted to the correct DTO.
     */
    @Test
    fun `getById - success`() {
        transaction {
            // 1) Create a TypeDeclarationDao mock
            val dao = mockk<TypeDeclarationDao>(relaxed = true)

            // 2) Create a TypeDefinitionDao mock
            val defDao = mockk<TypeDefinitionDao>(relaxed = true)

            // 3) In the mapper’s final DTO, we need a TypeDefinitionDto
            val defDto = TypeDefinitionDto(
                id = 234,
                name = "typeDef",
                namespace = "ok.name.space",
                versionSpec = VersionSpecDto(
                    id = 345L,
                    versionMin = "1.0",
                    versionMax = "2.0",
                    versionExact = "3.0"
                )
            )

            // 4) And a top-level TypeDeclarationDto that references the above def
            val dto = TypeDeclarationDto(
                id = 123,
                typeDefinition = defDto
            )

            // 5) Tell the mock dao how to provide its nested properties
            every { dao.typeDefinition } returns defDao
            every { dao.transformations } returns emptySized()

            val typeDefinitionsMapper = mockk<TypeDefinitionsMapper>()
            // 6) Now we must tell the typeDefinitionsMapper how to turn defDao -> defDto
            every { typeDefinitionsMapper.toDto(defDao) } returns defDto

            // 7) Finally, tell the top-level mapper how to turn dao -> dto
            //    Actually, in your code, TypeDeclarationsMapper *is* the one calling with(typeDefinitionsMapper) { defDao.toDto() }
            //    So you could do it at the repository / service layer.
            //    But if your test calls mapper.toDto(dao) directly:
            val mappedResult = with(mockMapper) { toDto(dao) }

            // The repository returns this DAO for the given ID
            every { mockRepo.getById(any()) } returns dao

            // WHEN we call the service method
            val result = service.getById(123L)

            // THEN verify we got a Success with the correct data
            assertIs<AsyncResult.Success<TypeDeclarationDto>>(result)
            assertEquals(dto, result.data)
            assertEquals(dto, mappedResult)
            // Also verify the repository was called with an EntityID pointing to TypeDeclarations
            // (If you need to ensure it's that table, we can match the table as well.)
            verify {
                mockRepo.getById(
                    withArg {
                        assertEquals(123L, it.value)
                        assertEquals(TypeDeclarations, it.table)
                    }
                )
            }
        }
    }

    /**
     * Test that getById returns a Failure when the repository throws an exception,
     * and that the error is an AsyncError.DatabaseError.
     */
    @Test
    fun `getById - failure`() {
        transaction {
            // GIVEN the repository throws an exception
            every { mockRepo.getById(any()) } throws RuntimeException("(Message Irrelevant)")

            // WHEN we call the service method
            val result = service.getById(999L)

            // THEN verify it's a Failure
            assertIs<AsyncResult.Failure>(result)
            val error = result.error
            assertIs<AsyncError.DatabaseError>(error)
            assertTrue(error.message.contains("Failed to get id"))

            // Verify we invoked the repository with the correct ID
            verify {
                mockRepo.getById(
                    withArg {
                        assertEquals(999L, it.value)
                    }
                )
            }
        }
    }

    /**
     * Test that delete returns Success(true) when repository succeeds,
     * and is called with the correct EntityID.
     */
    @Test
    fun `delete - single entity - success`() {
        transaction {
            // Mock repository.delete() to do nothing (success)
            every { mockRepo.delete(any<EntityID<Long>>()) } returns Unit

            // WHEN we call delete on the service
            val result = service.delete(77L)

            // THEN it's a success with 'true'
            assertIs<AsyncResult.Success<Boolean>>(result)
            assertTrue(result.data)

            verify {
                mockRepo.delete(withArg<EntityID<Long>> { entityId ->
                    assertEquals(77L, entityId.value)
                })
            }
        }
    }

    /**
     * Test that delete returns Success(true) when repository succeeds,
     * and is called with the correct EntityID.
     */
    @Test
    fun `delete - multiple entities - success`() {
        transaction {
            // Mock repository.delete() to do nothing (success)
            every { mockRepo.delete(any<List<EntityID<Long>>>()) } returns Unit

            // WHEN we call delete on the service
            val result = service.delete(listOf(77L, 78L))

            // THEN it's a success with 'true'
            assertIs<AsyncResult.Success<Boolean>>(result)
            assertTrue(result.data)

            verify {
                mockRepo.delete(withArg<List<EntityID<Long>>> { idList ->
                    assertEquals(listOf(77L, 78L), idList.map { it.value })
                })
            }
        }
    }

    /**
     * Test that delete returns a Failure if the repository.delete() call throws.
     */
    @Test
    fun `delete - multiple entities - failure`() {
        // Mock an exception
        every { mockRepo.delete(any<List<EntityID<Long>>>()) } throws IllegalStateException("(Message Irrelevant)")

        val result = service.delete(listOf(88L, 89L))

        assertIs<AsyncResult.Failure>(result)
        assertIs<AsyncError.DatabaseError>(result.error)
        assertTrue(result.error.message?.contains("Unable to delete entity") ?: false)

        // Verify the correct argument
        verify {
            mockRepo.delete(withArg<EntityID<Long>> { entityId ->
                assertEquals(88L, entityId.value)
            })
        }
    }

    /**
     * Test that delete returns a Failure if the repository.delete() call throws.
     */
    @Test
    fun `delete - single entity - failure`() {
        transaction {
            // Mock an exception
            every { mockRepo.delete(any<EntityID<Long>>()) } throws IllegalStateException("(Message Irrelevant)")

            val result = service.delete(88L)

            assertIs<AsyncResult.Failure>(result)
            assertIs<AsyncError.DatabaseError>(result.error)
            assertTrue(result.error.message?.contains("Unable to delete entity") ?: false)

            // Verify the correct argument
            verify {
                mockRepo.delete(withArg<List<EntityID<Long>>> { idList ->
                    assertEquals(listOf(88L, 89L), idList.map { it.value })
                })
            }
        }
    }

    /**
     * A minimal demonstration of create() success:
     * verifying it calls mapper to convert Dto->Dao, repo returns the same Dao,
     * and service returns the mapped Dto.
     */
    @Test
    fun `create - single entity - success`() {
        transaction {
            // A simple Dto
            val defDto = TypeDefinitionDto(
                id = 234,
                name = "typeDef",
                namespace="ok.name.space",
                versionSpec = VersionSpecDto(
                    id = 345L,
                    versionMin="1.0",
                    versionMax="2.0",
                    versionExact="3.0"
                )
            )
            val inputDto = TypeDeclarationDto(id = 123, typeDefinition = defDto)

            // A mock DAO for the "converted" entity
            val dao = mockk<TypeDeclarationDao>()

            // Suppose the repo returns the same dao after creation
            every { mockRepo.create(dao) } returns dao

            // The mapper is used to convert from Dto->Dao and then Dao->Dto
            every { mockMapper.toEntity(inputDto) } returns dao
            every { mockMapper.toDto(dao) } returns inputDto.copy(id = 999)

            // Call the service
            val result = service.create(inputDto)

            // Should get success
            assertIs<AsyncResult.Success<TypeDeclarationDto>>(result)
            // The returned DTO might have an ID if the DB logic does that
            assertEquals(999, result.data.id)

            // Verify the repository was called with the dao
            verify {
                mockRepo.create(dao)
            }
        }
    }

    /**
     * Test that create (passed a single DTO) returns a Failure when the repository throws an exception,
     * and that the error is an AsyncError.DatabaseError.
     */
    @Test
    fun `create - single entity - failure`() {
        transaction {
            // A simple Dto
            val defDto = TypeDefinitionDto(
                id = 234,
                name = "typeDef",
                namespace = "ok.name.space",
                versionSpec = VersionSpecDto(
                    id = 345L,
                    versionMin="1.0",
                    versionMax="2.0",
                    versionExact="3.0"
                )
            )
            val inputDto = TypeDeclarationDto(id = 123, typeDefinition = defDto)

            // A mock DAO for the "converted" entity
            val dao = mockk<TypeDeclarationDao>()
            every { mockMapper.toEntity(inputDto) } returns dao
            every { mockMapper.toDto(dao) } returns inputDto.copy(id = 998)

            // GIVEN the repository throws an exception
            every { mockRepo.create(any<TypeDeclarationDao>()) } throws IllegalStateException("(Message Irrelevant)")

            // WHEN we call the service method
            val result = service.create(inputDto)

            // THEN verify it's a Failure
            assertIs<AsyncResult.Failure>(result)
            val error = result.error
            assertIs<AsyncError.DatabaseError>(error)
            assertTrue(error.message.contains("Unable to create entity"))

            // Verify we invoked the repository with the correct ID
            verify {
                mockRepo.create(withArg<TypeDeclarationDao> { entity ->
                    // Check each EntityID in the list
                    assertEquals(entity, dao)
                })
            }
        }
    }

    /**
     * Test that create (passed a list of DTOs) returns a Failure when the repository throws an exception,
     * and that the error is an AsyncError.DatabaseError.
     */
    @Test
    fun `create - multiple entities - failure`() {
        transaction {
            val versionSpecDao = versionSpecDao(versionMin="1.0", versionMax="2.0", versionExact="3.0")
            val versionSpecDto = versionSpecDao.run {
                versionSpecDto(id = id.value, versionMin, versionMax, versionExact)
            }
            val typeDefDao = typeDefinitionDao(name = "typeDef", namespace="ok.name.space", versionSpec=versionSpecDao)
            val defDto = TypeDefinitionDto(
                id = typeDefDao.id.value,
                name = typeDefDao.name,
                namespace=typeDefDao.namespace,
                versionSpec=versionSpecDto
            )
            val inputDao1 = typeDeclarationDao()
            val inputDao2 = typeDeclarationDao()
            val inputDto1 = TypeDeclarationDto(id = inputDao1.id.value, typeDefinition = defDto)
            val inputDto2 = TypeDeclarationDto(id = inputDao2.id.value, typeDefinition = defDto)

            // Mock DAOs for the "converted" entity
//            val dao1 = mockk<TypeDeclarationDao>()
//            every { mockMapper.run { inputDto1.toEntity() } } returns dao1
//            every { mockMapper.run { dao1.toDto() } } returns inputDto1.copy(id = 998)

//            val dao2 = mockk<TypeDeclarationDao>()
//            every { mockMapper.run { inputDto2.toEntity() } } returns dao2
//            every { mockMapper.run { dao2.toDto() } } returns inputDto2.copy(id = 999)

            // GIVEN the repository throws an exception
            every { mockRepo.create(any<List<TypeDeclarationDao>>()) } throws IllegalStateException("(Message Irrelevant)")

            // WHEN we call the service method
            val result = service.create(listOf(inputDto1, inputDto2))

            // THEN verify it's a Failure
            assertIs<AsyncResult.Failure>(result)
            val error = result.error
            assertIs<AsyncError.DatabaseError>(error)
            assertTrue(error.message.contains("Unable to create entities"))

            // Verify we invoked the repository with the correct ID
//            verify {
//                mockRepo.create(withArg<List<TypeDeclarationDao>> { idList ->
//                    assertEquals(listOf(dao1, dao2), idList)
//                })
//            }
        }
    }

}
