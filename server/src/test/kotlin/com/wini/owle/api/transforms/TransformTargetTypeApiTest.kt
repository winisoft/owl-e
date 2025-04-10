package com.wini.owle.api.transforms

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
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
import com.wini.owle.validators.DtoValidator


open class TransformTargetTypeApiTest : BaseCompositePkApiTest<TransformationTargetTypeDto, TransformTargetTypeApi>() {

    override val route: String = "/api/transform-target-type"

    override fun getDto(id1: Long, id2: Long): TransformationTargetTypeDto = DtoTestInstanceFactory.transformationTargetTypeDto(
        transformationId = id1,
        typeDeclarationId = id2
    )

    override val serializer: KSerializer<TransformationTargetTypeDto> = TransformationTargetTypeDto.serializer()
    
    override val listSerializer: KSerializer<List<TransformationTargetTypeDto>> = ListSerializer(TransformationTargetTypeDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, TransformationTargetTypeDto>,
        validator: DtoValidator<TransformationTargetTypeDto>
    ): TransformTargetTypeApi = TransformTargetTypeApi(dll, validator)

    @Test
    override fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: TransformationTargetTypeDto = getDto(1, 1)
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, TransformationTargetTypeDto>> {
            coEvery { getById(id) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?transformationId={${dto.transformationId}}&eligibleTypeDeclarationId={${dto.typeDeclarationId}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    override fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, TransformationTargetTypeDto>> {
            coEvery {
                getById(id)
            } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
        }

        setupTestApp(dll) {
            val response = client.get("$route/id?transformationId={${Long.MAX_VALUE}}&eligibleTypeDeclarationId={${Long.MAX_VALUE}}") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

}