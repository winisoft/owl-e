package com.wini.owle.api.typing

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.typing.TypeUnionMemberDto
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


open class TypeUnionMemberApiTest : BaseCompositePkApiTest<TypeUnionMemberDto, TypeUnionMemberApi>() {

    override val route: String = "/api/type-union-member"

    override fun getDto(): TypeUnionMemberDto = DtoTestInstanceFactory.typeUnionMemberDto()

    override val serializer: KSerializer<TypeUnionMemberDto> = TypeUnionMemberDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeUnionMemberDto>> = ListSerializer(TypeUnionMemberDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, TypeUnionMemberDto>,
        validator: (TypeUnionMemberDto) -> Boolean
    ): TypeUnionMemberApi = TypeUnionMemberApi(dll, validator)

    @Test
    override fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: TypeUnionMemberDto = getDto()
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, TypeUnionMemberDto>> {
            coEvery { getById(id) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?unionTypeDeclarationId={${dto.unionTypeDeclarationId}}&memberTypeDeclarationId={${dto.memberTypeDeclarationId}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    override fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, TypeUnionMemberDto>> {
            coEvery {
                getById(id)
            } returns AsyncResult.Failure(AsyncError.NoSuchIdError("Entity with id $id not found"))
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?unionTypeDeclarationId={unionTypeDeclarationId}&memberTypeDeclarationId={memberTypeDeclarationId}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

}