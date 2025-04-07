package com.wini.owle.api

import com.wini.owle.api.workflows.WorkflowApi
import com.wini.owle.async.*
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import kotlin.test.*


abstract class BaseEndpointTest<ID: Any, T: BaseDto, R: RoutedApi> {

    private val json = Json { prettyPrint = false; encodeDefaults = true }

    private fun setupTestApp(
        dll: DLL<ID, T>,
        testBlock: suspend ApplicationTestBuilder.() -> Unit
    ) = testApplication {
        application {
            install(ContentNegotiation) { json(json) }
            routing {
                getApiInstance(dll).register(this)
            }
        }
        testBlock()
    }

    abstract val route: String
    abstract fun getApiInstance(dll: DLL<ID, T>): R
    abstract fun getDto(): T
    abstract val serializer: KSerializer<T>
    abstract val listSerializer: KSerializer<List<T>>

    @org.junit.jupiter.api.Test
    fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto()
        val dll = mock<DLL<ID, T>> {
            onBlocking { create(eq(inputDto)) } doReturn AsyncResult.Success(inputDto)
        }

        setupTestApp(dll) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertEquals(json.encodeToString(serializer, inputDto), response.bodyAsText())
        }
    }

    @org.junit.jupiter.api.Test
    fun `GET all - on success - returns HTTP Code 200, serialized list of DTO return val`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val dll = mock<DLL<ID, T>> {
            onBlocking { getAll() } doReturn AsyncResult.Success(data = dtoList)
        }

        setupTestApp(dll) {
            val response = client.get("/api/workflow") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(json.encodeToString(listSerializer, dtoList), response.bodyAsText())
        }
    }
}


//
//@Serializable
//data class TestDto(val id: Long, val name: String) : BaseDto
//
//class TestEndpoint(
//    dll: DLL<Long, TestDto>,
//    serializer: KSerializer<TestDto>
//) : BaseEndpointImpl<Long, TestDto>(dll, serializer, validator = { true }), RoutedApi {
//
//
//    override fun register(routing: Routing) {
//        routing {
//            post("/create") {
//                call.asyncResultSingle(create(call))
//            }
//            get("/all") {
//                call.asyncResult(getAll())
//            }
//        }
//
//    }
//}
//
//class TestEndpointTest {
//
//    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
//
//    private fun setupTestApplication(
//        apiInstance: TestEndpoint,
//        testCode: suspend ApplicationTestBuilder.() -> Unit
//    ) = testApplication {
//        application {
//            install(ContentNegotiation) { json(json) }
//            routing { apiInstance.register(this) }
//        }
//        testCode()
//    }
//
//    @Test
//    fun `create returns Created on success`() {
//        val testDto = TestDto(1, "Alpha")
//        val dll = mockk<DLL<Long, TestDto>> {
//            every { create(testDto) } returns AsyncResult.Success(testDto)
//        }
//        val api = TestEndpoint(dll, TestDto.serializer())
//
//        setupTestApplication(api) {
//            val response = client.post("/create") {
//                contentType(ContentType.Application.Json)
//                setBody(json.encodeToString(testDto))
//            }
//
//            assertEquals(HttpStatusCode.Created, response.status)
//            assertEquals(json.encodeToString(testDto), response.bodyAsText())
//        }
//
//        verify(exactly = 1) { dll.create(testDto) }
//        confirmVerified(dll)
//    }
//
//    @Test
//    fun `create returns BadRequest on constraint failure`() {
//        val testDto = TestDto(2, "Beta")
//        val dll = mockk<DLL<Long, TestDto>> {
//            every { create(testDto) } returns AsyncError.ApiRequest.DtoConstraintError.asFailure()
//        }
//        val api = TestEndpoint(dll, TestDto.serializer())
//
//        setupTestApplication(api) {
//            val response = client.post("/create") {
//                contentType(ContentType.Application.Json)
//                setBody(json.encodeToString(testDto))
//            }
//
//            assertEquals(HttpStatusCode.BadRequest, response.status)
//            assertTrue("DtoConstraintError" in response.bodyAsText())
//        }
//    }
//
//    @Test
//    fun `getAll returns OK with data`() {
//        val dtos = listOf(TestDto(1, "One"), TestDto(2, "Two"))
//        val dll = mockk<DLL<Long, TestDto>> {
//            every { getAll() } returns AsyncResult.Success(dtos)
//        }
//        val api = TestEndpoint(dll, TestDto.serializer())
//
//        setupTestApplication(api) {
//            val response = client.get("/all")
//
//            assertEquals(HttpStatusCode.OK, response.status)
//            assertEquals(json.encodeToString(ListSerializer(TestDto.serializer())), response.bodyAsText())
//        }
//
//        verify(exactly = 1) { dll.getAll() }
//    }
//}
