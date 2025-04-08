package com.wini.owle.api.environs

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
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

    override fun getDto(): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto()

    override val serializer: KSerializer<ToolPackageDependencyDto> = ToolPackageDependencyDto.serializer()

    override val listSerializer: KSerializer<List<ToolPackageDependencyDto>> = ListSerializer(ToolPackageDependencyDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, ToolPackageDependencyDto>,
        validator: (ToolPackageDependencyDto) -> Boolean
    ): ToolPackageDependencyApi = ToolPackageDependencyApi(dll, validator)


    @Test
    override fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: ToolPackageDependencyDto = getDto()
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>> {
            coEvery { getById(id) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?toolId={${dto.toolId}}&packageId={${dto.packageId}}&versionSpecId={${dto.versionSpecId}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    override fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, ToolPackageDependencyDto>> {
            coEvery {
                getById(id)
            } returns AsyncError.NoSuchIdError("Entity with id $id not found").asFailure()
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?toolId={${Long.MAX_VALUE}}&packageId={${Long.MAX_VALUE}}&versionSpecId={${Long.MAX_VALUE}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

}

