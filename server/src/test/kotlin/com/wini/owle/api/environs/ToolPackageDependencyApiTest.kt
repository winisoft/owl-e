package com.wini.owle.api.environs

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTest
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.environs.ToolPackageDependencyValidator
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


open class ToolPackageDependencyApiTest : BaseCompositePkApiTest<ToolPackageDependencyDto, ToolPackageDependencyApi>() {

    override val route: String = "/api/tool-package-dep"

    override fun getDto(id1: Long, id2: Long): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto()

    fun get3IdDto(
        id1: Long,
        id2: Long,
        id3: Long
    ): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto(
        toolId = id1,
        packageId = id2,
        versionSpecId = id3
    )

    override val getByIdRoute: String
        get() {
            val dto = get3IdDto(1, 1, 1)
            return "$route/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}"
        }

    override val deleteByIdRoute: String
        get() {
            val dto = get3IdDto(1, 1, 1)
            return "$route/tool-package-dep/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}"
        }

    override val invalidIdRoute: String
        get() = "$route/id?missingParams=true" 

    fun getDto(id1: Long, id2: Long, id3: Long): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto(
        toolId = id1, packageId = id2, versionSpecId = id3
    )

    override val serializer: KSerializer<ToolPackageDependencyDto> = kotlinx.serialization.serializer()

    override val listSerializer: KSerializer<List<ToolPackageDependencyDto>> = ListSerializer(serializer)

    override fun getApiInstance(
        dll: DLL<CompositeID, ToolPackageDependencyDto>,
        validator: DtoValidator<ToolPackageDependencyDto>
    ): ToolPackageDependencyApi = ToolPackageDependencyApi(dll, validator)

    @Test
    override fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: ToolPackageDependencyDto = get3IdDto(1, 1, 1)
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>> {
            coEvery { getById(any()) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    override fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val dto = get3IdDto(1, 1, 1)
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>> {
            coEvery {
                getById(any())
            } returns AsyncError.NoSuchIdError.asFailure()
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

    @Test
    fun `GET by id - with missing parameters - returns HTTP Code 400, CompositeIdError`() = runBlocking {
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>>()

        setupTestApp(dll) {
            val response = client.get("$route/id?toolId=1") { // Missing packageId and versionSpecId
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("CompositeIdError"))
        }
    }

    @Test
    fun `POST create - with custom validator - on validation success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val dto = get3IdDto(1, 1, 1)
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>> {
            coEvery { create(dto) } returns AsyncResult.Success(dto)
        }
        
        // Create a validator that checks that the numeric IDs are > 0
        val validator: ToolPackageDependencyValidator = ToolPackageDependencyValidator()

        setupTestApp(dll, validator) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, dto))
            }

            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    fun `POST create - with custom validator - on validation failure - returns HTTP Code 400, DtoConstraintError`() = runBlocking {
        val dto = get3IdDto(1, 1, 1).copy(toolId = -1) // Invalid toolId
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>>()
        
        // Create a validator that checks that the numeric IDs are > 0
        val validator: ToolPackageDependencyValidator = ToolPackageDependencyValidator()

        setupTestApp(dll, validator) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, dto))
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("DtoConstraintError"))
        }
    }

    @Test
    fun `POST create batch - with multiple entities - returns HTTP Code 201, serialized list of DTO return val`() = runBlocking {
        val dto1 = get3IdDto(1, 1, 1)
        val dto2 = get3IdDto(2, 2, 2).copy(toolId = dto1.toolId + 1, packageId = dto1.packageId + 1)
        val dtoList = listOf(dto1, dto2)
        
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>> {
            coEvery { createAll(dtoList) } returns AsyncResult.Success(dtoList)
        }

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertEquals(dtoList, json.decodeFromString(listSerializer, response.bodyAsText()))
        }
    }
}

