package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


abstract class Base2IdCompositeApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<CompositeID, T, R>() {

    // This should be overridden by subclasses to create a valid route for GET by ID
    protected open val getByIdRoute: String
        get() = "$route/id"

    // This should be overridden by subclasses to create a valid route for DELETE by ID
    protected open val deleteByIdRoute: String
        get() = "$route/id"
    
    // This should be overridden by subclasses to create a route with missing parameters for testing CompositeIDError
    abstract val onlyFirstParamRoute: String

    abstract val malformedParamsRoute: String
    
    abstract fun getDto(id1: Long, id2: Long): T
    abstract fun getApiInstance(
        dll: DLL<CompositeID, T>,
        validator: DtoValidator<T>,
        compositeIdFactory: CompositeIdFactory<T>
    ): R
    abstract fun compositeId1(): CompositeID
    abstract fun compositeId2(): CompositeID
    abstract fun allParamsMalformedErrors(): List<AsyncError.ApiRequestError>
    abstract fun onlyFirstParamProvidedErrors(): List<AsyncError.ApiRequestError>

    protected fun setupTestApp(
        dll: DLL<CompositeID, T>,
        validator: DtoValidator<T> = DtoValidator.alwaysValid(),
        compositeIdFactory: CompositeIdFactory<T> = mockk(),
        testBlock: suspend ApplicationTestBuilder.() -> Unit
    ) = testApplication {
        application {
            install(ContentNegotiation) { json(json) }
            routing {
                getApiInstance(
                    dll = dll, 
                    validator = validator, 
                    compositeIdFactory = compositeIdFactory
                ).register(this)
            }
        }
        testBlock()
    }

    lateinit var dto: T
    lateinit var dtoList: List<T>
    lateinit var id: CompositeID
    lateinit var compositeIdFactory: CompositeIdFactory<T>
    lateinit var dll: DLL<CompositeID, T>
    lateinit var response: HttpResponse

    fun HttpResponse.parseOne(): ApiResponse<T> = runBlocking {
        Json.decodeFromString(apiResponseOneSerializer, bodyAsText())
    }

    fun HttpResponse.parseMany(): ApiResponse<List<T>> = runBlocking {
        Json.decodeFromString(apiResponseListSerializer, bodyAsText())
    }

    @Nested
    @DisplayName("Success")
    inner class Success {

        @Nested
        @DisplayName("GET one")
        inner class GetOne {

            @BeforeEach
            fun setUp() {
                dto = getDto(1, 1)
                id = compositeId1()
                compositeIdFactory = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Success(id)
                }
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { getById(id) } returns AsyncResult.Success(dto)
                }
                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    response = client.get(getByIdRoute) {
                        contentType(ContentType.Application.Json)
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 200`() = runBlocking {
                assertEquals(HttpStatusCode.OK, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(response.parseOne().success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(response.parseOne().errors)
            }

            @Test
            fun `Returns data with expected DTO object`() = runBlocking {
                assertEquals(dto, response.parseOne().data)
            }
        }

        @Nested
        @DisplayName("GET many")
        inner class GetMany {

            @BeforeEach
            fun setUp() {
                dtoList = listOf(getDto(1, 1), getDto(2, 2))
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { getAll() } returns AsyncResult.Success(data = dtoList)
                }
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { getAll() } returns AsyncResult.Success(dtoList)
                }
                setupTestApp(dll = dll) {
                    response = client.get(route) {
                        contentType(ContentType.Application.Json)
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 200`() = runBlocking {
                assertEquals(HttpStatusCode.OK, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(response.parseMany().success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(response.parseMany().errors)
            }

            @Test
            fun `Returns data with expected DTO object`() = runBlocking {
                assertEquals(dtoList, response.parseMany().data)
            }
        }

        @Nested
        @DisplayName("POST one")
        inner class PostOne {

            @BeforeEach
            fun setUp() {
                dto = getDto(1, 1)
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { create(dto) } returns AsyncResult.Success(dto)
                }

                setupTestApp(dll = dll) {
                    response = client.post(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, dto))
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 201`() = runBlocking {
                assertEquals(HttpStatusCode.Created, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(response.parseOne().success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(response.parseOne().errors)
            }

            @Test
            fun `Returns data with expected DTO object`() = runBlocking {
                assertEquals(dto, response.parseOne().data)
            }

        }

        @Nested
        @DisplayName("POST batch")
        inner class PostBatch {

            @BeforeEach
            fun setUp() {
                dtoList = listOf(getDto(1, 1), getDto(2, 2))
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { createAll(dtoList) } returns AsyncResult.Success(dtoList)
                }

                setupTestApp(dll = dll) {
                    response = client.post("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, dtoList))
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 201`() = runBlocking {
                assertEquals(HttpStatusCode.Created, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(response.parseMany().success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(response.parseMany().errors)
            }

            @Test
            fun `Returns data with expected DTO object`() = runBlocking {
                assertEquals(dtoList, response.parseMany().data)
            }

        }

        @Nested
        @DisplayName("PUT one")
        inner class PutOne {

            @BeforeEach
            fun setUp() {
                dto = getDto(1, 1)
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { update(dto) } returns AsyncResult.Success(dto)
                }
                setupTestApp(dll = dll) {
                    response = client.put(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, dto))
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 200`() = runBlocking {
                assertEquals(HttpStatusCode.OK, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(response.parseOne().success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(response.parseOne().errors)
            }

            @Test
            fun `Returns data with expected DTO object`() = runBlocking {
                assertEquals(dto, response.parseOne().data)
            }
        }

        @Nested
        @DisplayName("PUT batch")
        inner class PutBatch {

            @BeforeEach
            fun setUp() {
                dtoList = listOf(getDto(1, 1), getDto(2, 2))
                dll = mockk<DLL<CompositeID, T>> {
                    coEvery { updateAll(dtoList) } returns AsyncResult.Success(dtoList)
                }
                setupTestApp(dll = dll) {
                    response = client.put("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, dtoList))
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 200`() = runBlocking {
                assertEquals(HttpStatusCode.OK, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(response.parseMany().success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(response.parseMany().errors)
            }

            @Test
            fun `Returns data with expected DTO object`() = runBlocking {
                assertEquals(dtoList, response.parseMany().data)
            }

        }

        @Nested
        @DisplayName("DELETE one")
        inner class DeleteOne {

            @BeforeEach
            fun setUp() {
                id = compositeId1()
                compositeIdFactory = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Success(id)
                }
                dll = mockk() {
                    coEvery { delete(id) } returns AsyncResult.Success(Unit)
                }
                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    response = client.delete(deleteByIdRoute) {
                        contentType(ContentType.Application.Json)
                    }
                }
            }
            
            @Test
            fun `Returns HTTP Code 204`() = runBlocking {
                assertEquals(HttpStatusCode.NoContent, response.status)
            }
            
            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText()).success)
            }
            
            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText()).errors)
            }
            
            @Test
            fun `Returns ApiResponse with null data`() = runBlocking {
                assertNull(Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText()).data)
            }
        }

        @Nested
        @DisplayName("DELETE batch")
        inner class DeleteMany {

            @BeforeEach
            fun setUp() {
                dtoList = listOf(getDto(1, 1), getDto(2, 2))
                val id1 = compositeId1()
                val id2 = compositeId2()
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromDtoInstance(dtoList[0]) } returns id1
                    coEvery { fromDtoInstance(dtoList[1]) } returns id2
                }
                val dll = mockk<DLL<CompositeID, T>> {
                    coEvery { deleteAll(any()) } returns AsyncResult.Success(Unit)
                }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    response = client.delete("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(json.encodeToString(listSerializer, dtoList))
                    }
                }
            }

            @Test
            fun `Returns HTTP Code 204`() = runBlocking {
                assertEquals(HttpStatusCode.NoContent, response.status)
            }

            @Test
            fun `Returns ApiResponse success == true`() = runBlocking {
                assertTrue(Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText()).success)
            }

            @Test
            fun `Returns ApiResponse with null errors`() = runBlocking {
                assertNull(Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText()).errors)
            }

            @Test
            fun `Returns ApiResponse with null data`() = runBlocking {
                assertNull(Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText()).data)
            }
        }
    }

    @Nested
    @DisplayName("Validation Errors")
    inner class Validation {
        @Test
        fun `POST create - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
            val inputDto: T = getDto(1,  1)
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()

            // Create a validator that always fails
            val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

            setupTestApp(dll=dll, validator=failingValidator, compositeIdFactory = compositeIdFactory) {
                val response = client.post(route) {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(serializer, inputDto))
                }

                val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
            }
        }

        @Test
        fun `POST createBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
            val inputDto: T = getDto(1, 1)
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()

            // Create a validator that always fails
            val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

            setupTestApp(dll = dll, validator = failingValidator, compositeIdFactory = compositeIdFactory) {
                val response = client.post("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, listOf(inputDto)))
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
            }
        }

        @Test
        fun `PUT update - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
            val inputDto: T = getDto(1, 1)
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()

            // Create a validator that always fails
            val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

            setupTestApp(dll = dll, validator = failingValidator, compositeIdFactory = compositeIdFactory) {
                val response = client.put(route) {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(serializer, inputDto))
                }

                val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
            }
        }

        @Test
        fun `PUT updateBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
            val inputDto: T = getDto(1, 1)
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()

            // Create a validator that always fails
            val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

            setupTestApp(dll = dll, validator = failingValidator, compositeIdFactory = compositeIdFactory) {
                val response = client.put("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, listOf(inputDto)))
                }

                val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
            }
        }

    }

    @Nested
    @DisplayName("Serialization Errors")
    inner class SerializationErrors {

        @Test
        fun `DELETE batch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
            val dll = mockk<DLL<CompositeID, T>>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequestError.RequestBodyMalformed))

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.delete("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody("{ invalid json }")
                }

                assertEquals(HttpStatusCode.BadRequest, response.status)
                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(expectedErrors, apiResponse.errors)
            }
        }


        @Test
        fun `POST create - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
            val dll = mockk<DLL<CompositeID, T>>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.post(route) {
                    contentType(ContentType.Application.Json)
                    setBody("{ invalid json }")
                }

                val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())

                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(expectedErrors, apiResponse.errors)
            }
        }

        @Test
        fun `POST createBatch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
            val dll = mockk<DLL<CompositeID, T>>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val reqError = AsyncError.ApiRequestError.RequestBodyMalformed
            val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.post("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody("{ invalid json }")
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(apiResponse.message, reqError.message)
                assertEquals(expectedErrors, apiResponse.errors)
            }
        }

        @Test
        fun `PUT update - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
            val dll = mockk<DLL<CompositeID, T>>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val apiError = AsyncError.ApiRequestError.RequestBodyMalformed.run { ApiError(errorCode, message) }
            val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.put(route) {
                    contentType(ContentType.Application.Json)
                    setBody("{ invalid json }")
                }

                val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(apiResponse.message, apiError.message)
                assertEquals(expectedErrors, apiResponse.errors)
            }
        }

        @Test
        fun `PUT updateBatch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
            val dll = mockk<DLL<CompositeID, T>>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            coEvery { dll.updateAll(any()) } returns AsyncResult.Success(emptyList())
            val apiError = AsyncError.ApiRequestError.RequestBodyMalformed.run { ApiError(errorCode, message) }
            val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.put("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody("{ invalid json }")
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(apiResponse.message, apiError.message)
                assertEquals(expectedErrors, apiResponse.errors)
            }
        }

    }

    @Nested
    @DisplayName("By ID Errors")
    inner class ByIdError {

        @Nested
        @DisplayName("GET")
        inner class Get {

            @Test
            fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
                val id: CompositeID = mockk()
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Success(id)
                }
                val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.NoSuchIdError))
                val dll = mockk<DLL<CompositeID, T>> {
                    coEvery {
                        getById(id)
                    } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
                }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    val response = client.get(getByIdRoute) {
                        contentType(ContentType.Application.Json)
                    }

                    assertEquals(HttpStatusCode.NotFound, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    assertFalse(apiResponse.success)
                    assertNull(apiResponse.data)
                    assertEquals(expectedErrors, apiResponse.errors)
                }
            }

            @Test
            fun `GET by id - with missing parameters - returns HTTP Code 400, MultipleParamFailure`() = runBlocking {
                val dll = mockk<DLL<CompositeID, T>>()
                val asyncErrors = onlyFirstParamProvidedErrors()
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Failure(AsyncError.ApiRequestError.MultipleParamFailure(asyncErrors))
                }

                val apiErrors = asyncErrors.map { ApiError.fromAsyncError(it) }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    val response = client.get(onlyFirstParamRoute) {
                        contentType(ContentType.Application.Json)
                    }

                    assertEquals(HttpStatusCode.BadRequest, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    assertFalse(apiResponse.success)
                    assertEquals("The required values were not provided in valid format.", apiResponse.message)
                    assertNull(apiResponse.data)
                    assertEquals(apiErrors, apiResponse.errors)
                }
            }

            @Test
            fun `GET by id - with malformed parameters - returns HTTP Code 400, MultipleParamFailure`() = runBlocking {
                val dll = mockk<DLL<CompositeID, T>>()
                val asyncErrors = onlyFirstParamProvidedErrors()
                val apiErrors = asyncErrors.map { ApiError.fromAsyncError(it) }
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Failure(AsyncError.ApiRequestError.MultipleParamFailure(asyncErrors))
                }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    val response = client.get(onlyFirstParamRoute) {
                        contentType(ContentType.Application.Json)
                    }

                    assertEquals(HttpStatusCode.BadRequest, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    assertFalse(apiResponse.success)
                    assertNull(apiResponse.data)
                    assertEquals(apiErrors, apiResponse.errors)
                }
            }

        }

        @Nested
        @DisplayName("DELETE")
        inner class Delete {

            @Test
            fun `DELETE by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
                val id: CompositeID = compositeId1()
                val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.NoSuchIdError))
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Success(id)
                }
                val dll = mockk<DLL<CompositeID, T>> {
                    coEvery { getById(id) } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
                }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    val response = client.get(getByIdRoute) {
                        contentType(ContentType.Application.Json)
                    }

                    assertEquals(HttpStatusCode.NotFound, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    assertFalse(apiResponse.success)
                    assertNull(apiResponse.data)
                    assertEquals(expectedErrors, apiResponse.errors)
                }
            }

            @Test
            fun `DELETE by id - with missing parameters - returns HTTP Code 400, MultipleParamFailure`() = runBlocking {
                val dll = mockk<DLL<CompositeID, T>>()
                val asyncErrors = onlyFirstParamProvidedErrors()
                val apiErrors = asyncErrors.map { ApiError.fromAsyncError(it) }
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Failure(AsyncError.ApiRequestError.MultipleParamFailure(asyncErrors))
                }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    val response = client.delete(onlyFirstParamRoute) {
                        contentType(ContentType.Application.Json)
                    }

                    assertEquals(HttpStatusCode.BadRequest, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    assertFalse(apiResponse.success)
                    assertEquals("The required values were not provided in valid format.", apiResponse.message)
                    assertNull(apiResponse.data)
                    assertEquals(apiErrors, apiResponse.errors)
                }
            }

            @Test
            fun `DELETE by id - with malformed parameter - returns HTTP Code 400, ParamValueMalformed`() = runBlocking {
                val dll = mockk<DLL<CompositeID, T>>()
                val expectedAsyncErrors = allParamsMalformedErrors()
                val compositeIdFactory: CompositeIdFactory<T> = mockk() {
                    coEvery { fromRequestParams(any()) } returns AsyncResult.Failure(AsyncError.ApiRequestError.MultipleParamFailure(failed = expectedAsyncErrors))
                }
                val apiErrors = expectedAsyncErrors.map { ApiError.fromAsyncError(it) }

                setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                    val response = client.delete(malformedParamsRoute) {
                        contentType(ContentType.Application.Json)
                    }

                    assertEquals(HttpStatusCode.BadRequest, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    assertFalse(apiResponse.success)
                    assertEquals(apiErrors, apiResponse.errors)
                    assertNull(apiResponse.data)
                }
            }

        }

    }

    @Nested
    @DisplayName("Empty List Errors")
    inner class EmptyList {

        @Test
        fun `POST create batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
            val emptyList = emptyList<T>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()
            val expectedErrors = listOf(AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) })

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.post("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, emptyList))
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(AsyncError.ApiRequestError.EmptyListNotAllowed.message, apiResponse.message)
                assertEquals(expectedErrors, apiResponse.errors)
            }
        }

        @Test
        fun `PUT update batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
            val emptyList = emptyList<T>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()
            coEvery { dll.updateAll(emptyList()) } returns AsyncResult.Success(emptyList())
            val apiError = AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) }
            val expectedResponse = ApiResponse<T>(success = false, message = apiError.message, errors = listOf(apiError))

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.put("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, emptyList))
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(apiResponse.message, apiError.message)
                assertEquals(expectedResponse.errors, apiResponse.errors)
            }
        }

        @Test
        fun `DELETE batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
            val emptyList = emptyList<T>()
            val compositeIdFactory: CompositeIdFactory<T> = mockk()
            val dll = mockk<DLL<CompositeID, T>>()
            coEvery { dll.deleteAll(emptyList()) } returns AsyncResult.Success(Unit)
            val apiError = AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) }
            val expectedResponse = ApiResponse<T>(success = false, message = apiError.message, errors = listOf(apiError))

            setupTestApp(dll = dll, compositeIdFactory = compositeIdFactory) {
                val response = client.delete("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, emptyList))
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                assertEquals(HttpStatusCode.BadRequest, response.status)
                assertFalse(apiResponse.success)
                assertNull(apiResponse.data)
                assertEquals(apiResponse.message, apiError.message)
                assertEquals(expectedResponse.errors, apiResponse.errors)
            }
        }

    }
}