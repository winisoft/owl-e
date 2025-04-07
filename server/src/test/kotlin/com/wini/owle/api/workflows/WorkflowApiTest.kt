package com.wini.owle.api.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock


class WorkflowApiTest {

    private val json = Json { prettyPrint = false; encodeDefaults = true }

    private fun setupTestApp(
        dll: DLL<Long, WorkflowDto>,
        testBlock: suspend ApplicationTestBuilder.() -> Unit
    ) = testApplication {
        application {
            install(ContentNegotiation) { json(json) }
            routing {
                WorkflowApi(dll).register(this)
            }
        }
        testBlock()
    }

    @Test
    fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val inputDto: WorkflowDto = DtoTestInstanceFactory.workflowDto()
        val dll = mock<DLL<Long, WorkflowDto>> {
            onBlocking { create(eq(inputDto)) } doReturn AsyncResult.Success(inputDto)
        }

        setupTestApp(dll) {
            val response = client.post("/api/workflow") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(inputDto))
            }

            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(json.encodeToString(inputDto), response.bodyAsText())
        }
    }

    @Test
    fun `GET all - on success - returns HTTP Code 200, serialized list of DTO return val`() = runBlocking {
        val dtoList = listOf(DtoTestInstanceFactory.workflowDto())
        val dll = mock<DLL<Long, WorkflowDto>> {
            onBlocking { getAll() } doReturn AsyncResult.Success(data = dtoList)
        }

        setupTestApp(dll) {
            val response = client.get("/api/workflow") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(dtoList))
            }

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(json.encodeToString(dtoList), response.bodyAsText())
        }
    }

    //    @Test
//    fun `getAll should return OK with list on success`() = setupTestApplication(localSerializer) {
//        coEvery { mockDll.getAll() } returns AsyncResult.Success(sampleDtoList)
//        val response = client.get(routeBase)
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals(json.encodeToString(ListSerializer(localSerializer), sampleDtoList), response.bodyAsText())
//        coVerify(exactly = 1) { mockDll.getAll() }
//    }
//
//    @Test
//    fun `getById should return OK with DTO on success`() = setupTestApplication(localSerializer) {
//         coEvery { mockDll.getById(sampleId) } returns AsyncResult.Success(sampleDto)
//        val response = client.get("$routeBase/$sampleId")
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals(json.encodeToString(localSerializer, sampleDto), response.bodyAsText())
//        coVerify(exactly = 1) { mockDll.getById(sampleId) }
//    }
}
