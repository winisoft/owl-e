package com.wini.owle.api

import com.wini.owle.async.*
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
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
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import org.junit.Test
import kotlin.test.*
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
