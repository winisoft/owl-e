package com.wini.owle.api.user

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.user.TagRelationDto
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

open class TagRelationApiTest : BaseCompositePkApiTest<TagRelationDto, TagRelationApi>() {

    override val route: String = "/api/tag-relation"

    override fun getDto(): TagRelationDto = DtoTestInstanceFactory.tagRelationDto()

    override val serializer: KSerializer<TagRelationDto> = TagRelationDto.serializer()
    
    override val listSerializer: KSerializer<List<TagRelationDto>> = ListSerializer(TagRelationDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, TagRelationDto>,
        validator: (TagRelationDto) -> Boolean
    ): TagRelationApi = TagRelationApi(dll, validator)

    @Test
    override fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: TagRelationDto = getDto()
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, TagRelationDto>> {
            coEvery { getById(id) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?parentTagId={${dto.parentTagId}}&childTagId={${dto.childTagId}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    override fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, TagRelationDto>> {
            coEvery {
                getById(id)
            } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?parentTagId={${Long.MAX_VALUE}}&childTagId={${Long.MAX_VALUE}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

}