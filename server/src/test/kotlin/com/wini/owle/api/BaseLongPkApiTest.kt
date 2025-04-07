package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

//
//abstract class BaseLongPkApiTest<ID: Any, Dto : BaseDto, Api : LongIdEndpointImpl<Dto>> {
//
//    protected lateinit var mockDll: DLL<ID, Dto>
//
//    private lateinit var apiInstance: Api
//    protected abstract val clazz: KClass<Dto>
//    protected abstract val localSerializer: KSerializer<Dto>
//    protected abstract val routeBase: String // e.g., "/api/workflow"
//    protected abstract fun createApiInstance(dll: DLL<ID, Dto>): Api
//    protected abstract fun generateSampleDto(id: ID? = null): Dto
//    protected abstract fun generateSampleDtoList(count: Int = 3): List<Dto>
//
//    abstract val sampleId: ID
//    private lateinit var sampleDto: Dto
//    private lateinit var sampleDtoList: List<Dto>
//    private val json = Json { encodeDefaults = true; prettyPrint = true; isLenient = true }
//
//    @BeforeEach
//    protected open fun setUp() {
//        mockDll = mockk()
//        apiInstance = createApiInstance(mockDll)
//        sampleDto = generateSampleDto(sampleId)
//        sampleDtoList = generateSampleDtoList()
//    }
//
//    abstract fun MockKMatcherScope.anyDto(): Dto
//
//    private fun setupTestApplication(
//        localSerializer: KSerializer<Dto>,
//        testCode: suspend ApplicationTestBuilder.() -> Unit
//    ) = testApplication {
//        application {
//            install(ContentNegotiation) { json(json) }
//            routing {
//                apiInstance.register(this)
//            }
//        }
//        testCode()
//    }
//
//    // --- Test Cases ---
//
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
//
//    @Test
//    fun `create should return Created with DTO on success`() = setupTestApplication(localSerializer) {
//        val inputDto = generateSampleDto(null)
//        val createdDto = generateSampleDto(sampleId)
//        coEvery { mockDll.create(inputDto) } returns AsyncResult.Success(createdDto)
//        val response = client.post(routeBase) {
//            contentType(ContentType.Application.Json)
//            setBody(json.encodeToString(localSerializer, inputDto))
//        }
//        assertEquals(HttpStatusCode.Created, response.status)
//        assertEquals(json.encodeToString(localSerializer, createdDto), response.bodyAsText())
//        coVerify(exactly = 1) { mockDll.create(inputDto) }
//    }
//
//    @Test
//    fun `create should return BadRequest on validation error`() = setupTestApplication(localSerializer) {
//        val inputDto = generateSampleDto(null)
//        val validationError = AsyncError.ValidationError("Invalid name", listOf("Name cannot be empty"))
//        coEvery { mockDll.create(inputDto) } returns AsyncResult.Failure(validationError)
//        val response = client.post(routeBase) {
//            contentType(ContentType.Application.Json)
//            setBody(json.encodeToString(localSerializer, inputDto))
//        }
//        val expectedError = AsyncError.ApiRequest.LongIdInvalid
//        assertEquals(HttpStatusCode.BadRequest, response.status)
//         assertEquals(json.encodeToString(AsyncError.serializer(), expectedError), response.bodyAsText())
//        coVerify(exactly = 1) { mockDll.create(inputDto) }
//    }
//
//     @Test
//    fun `create should return BadRequest on invalid JSON body`() = setupTestApplication(localSerializer) {
//         val response = client.post(routeBase) {
//            contentType(ContentType.Application.Json)
//            setBody("{\"invalid json\": }")
//        }
//        assertEquals(HttpStatusCode.BadRequest, response.status)
//        val bodyText = response.bodyAsText()
//        assert(bodyText.contains("\"errorType\":\"BadRequest\""))
//        assert(bodyText.contains("\"message\":\"Invalid request body format"))
//        coVerify(exactly = 0) { mockDll.create(anyDto()) }
//    }
//
//    // TODO: Add tests for createBatch, update, updateBatch following similar patterns
//
//     @Test
//    fun `delete should return OK on success`() = setupTestApplication(localSerializer) {
//        every { mockDll.delete(sampleId) }  returns AsyncResult.Success(Unit)
//        //coEvery { mockDll.delete(sampleId) } returns AsyncResult.Success(Unit)
//        val response = client.delete("$routeBase/$sampleId")
//        assertEquals(HttpStatusCode.OK, response.status)
//        val serializer = AsyncResult.Success.serializer(Unit.serializer())
//        val result: AsyncResult.Success<Unit> = Json.decodeFromString(serializer, response.bodyAsText())
//        assertEquals(Unit, result.data)
//        coVerify(exactly = 1) { mockDll.delete(sampleId) }
//    }
//
//     @Test
//    fun `deleteBatch should return OK on success`() = setupTestApplication(localSerializer) {
//        val idsToDelete = listOf(1L, 2L, 3L)
//        coEvery { mockDll.deleteAll(idsToDelete) } returns AsyncResult.Success(Unit)
//
//        val response = client.delete("$routeBase/batch") {
//            contentType(ContentType.Application.Json)
//            setBody(json.encodeToString(idsToDelete))
//        }
//
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals("", response.bodyAsText())
//        coVerify(exactly = 1) { mockDll.deleteAll(idsToDelete) }
//    }
//
//    // TODO: Add tests for other endpoints (createBatch, update, updateBatch) and error conditions...
//
//}