package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
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
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


abstract class BaseEndpointTest<ID: Any, T: BaseDto, R: RoutedApi> {

    protected val json = Json { prettyPrint = false; encodeDefaults = true }

    protected fun setupTestApp(
        dll: DLL<ID, T>,
        validator: DtoValidator<T> = DtoValidator.alwaysValid(),
        testBlock: suspend ApplicationTestBuilder.() -> Unit
    ) = testApplication {
        application {
            install(ContentNegotiation) { json(json) }
            routing {
                getApiInstance(dll, validator).register(this)
            }
        }
        testBlock()
    }

    val apiResponseOneSerializer get() = ApiResponse.serializer(serializer)
    val apiResponseListSerializer get() = ApiResponse.serializer(listSerializer)

    abstract val route: String
    abstract fun getApiInstance(dll: DLL<ID, T>, validator: DtoValidator<T>): R
    abstract fun getDto(): T
    abstract val serializer: KSerializer<T>
    abstract val listSerializer: KSerializer<List<T>>


    @Test
    fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto()
        val expectedResponse = ApiResponse(success = true, data = inputDto)
        val dll = mockk<DLL<ID, T>> {
            coEvery { create(inputDto) } returns AsyncResult.Success(inputDto)
        }

        setupTestApp(dll) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertTrue(apiResponse.success)
            Assertions.assertNull(apiResponse.errors)
            Assertions.assertEquals(inputDto, apiResponse.data)
        }
    }

    @Test
    fun `GET all - on success - returns HTTP Code 200, serialized list of DTO return val`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val dll = mockk<DLL<ID, T>> {
            coEvery { getAll() } returns AsyncResult.Success(data = dtoList)
        }

        setupTestApp(dll) {
            val response = client.get(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertTrue(apiResponse.success)
            Assertions.assertNull(apiResponse.errors)
            Assertions.assertEquals(dtoList, apiResponse.data)
        }
    }

    @Test
    fun `PUT update - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto()
        val expectedResponse = ApiResponse(success = true, data = inputDto)
        val dll = mockk<DLL<ID, T>> {
            coEvery { update(inputDto) } returns AsyncResult.Success(inputDto)
        }

        setupTestApp(dll) {
            val response = client.put(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertTrue(apiResponse.success)
            Assertions.assertNull(apiResponse.errors)
            Assertions.assertEquals(inputDto, apiResponse.data)
        }
    }

    @Test
    fun `POST create batch - on success - returns HTTP Code 201, serialized list of DTO return val`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val expectedResponse = ApiResponse(true, data = dtoList)
        val dll = mockk<DLL<ID, T>> {
            coEvery { createAll(dtoList) } returns AsyncResult.Success(dtoList)
        }

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertTrue(apiResponse.success)
            Assertions.assertNull(apiResponse.errors)
            Assertions.assertEquals(dtoList, apiResponse.data)
        }
    }

    @Test
    fun `PUT update batch - on success - returns HTTP Code 200, serialized list of DTO return val`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val dll = mockk<DLL<ID, T>> {
            coEvery { updateAll(dtoList) } returns AsyncResult.Success(dtoList)
        }

        setupTestApp(dll) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertTrue(apiResponse.success)
            Assertions.assertNull(apiResponse.errors)
            Assertions.assertEquals(dtoList, apiResponse.data)
        }
    }

    @Test
    fun `POST create - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto()
        val expectedResponse = ApiResponse<T>(success = false, errors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequest.TypeDefinitionValidation)))
        val dll = mockk<DLL<ID, T>>()
        // We won't need this mock since validation should fail before DLL is called
        // coEvery { dll.create(inputDto) } returns AsyncResult.Success<T>(inputDto)
        
        // Create a validator that always fails
        val failingValidator = object : DtoValidator<T> {
            override fun validate(dto: T): Boolean = false
        }

        setupTestApp(dll, failingValidator) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            val error = AsyncError.ApiRequest.TypeDefinitionValidation.run { listOf(ApiError(errorCode, message)) }
            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(error, apiResponse.errors)
        }
    }

    @Test
    fun `POST createBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto()
        val expectedResponse = ApiResponse<T>(success = false, errors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequest.TypeDefinitionValidation)))
        val dll = mockk<DLL<ID, T>>()
        // We won't need this mock since validation should fail before DLL is called
        // coEvery { dll.createAll(listOf(inputDto)) } returns AsyncResult.Success<List<T>>(listOf(inputDto))
        
        // Create a validator that always fails
        val failingValidator = object : DtoValidator<T> {
            override fun validate(dto: T): Boolean = false
        }

        setupTestApp(dll, failingValidator) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, listOf(inputDto)))
            }

            val error = AsyncError.ApiRequest.TypeDefinitionValidation.run { listOf(ApiError(errorCode, message)) }
            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(error, apiResponse.errors)
        }
    }

    @Test
    fun `PUT update - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto()
        val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequest.TypeDefinitionValidation))
        val dll = mockk<DLL<ID, T>>()
        // We won't need this mock since validation should fail before DLL is called
        // coEvery { dll.update(inputDto) } returns AsyncResult.Success(inputDto)
        
        // Create a validator that always fails
        val failingValidator = object : DtoValidator<T> {
            override fun validate(dto: T): Boolean = false
        }

        setupTestApp(dll, failingValidator) {
            val response = client.put(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            val error = AsyncError.ApiRequest.TypeDefinitionValidation.run { listOf(ApiError(errorCode, message)) }
            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `PUT updateBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto()
        val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequest.TypeDefinitionValidation))
        val dll = mockk<DLL<ID, T>>()
        // We won't need this mock since validation should fail before DLL is called
        // coEvery { dll.updateAll(any()) } returns AsyncResult.Success(emptyList())
        
        // Create a validator that always fails
        val failingValidator = object : DtoValidator<T> {
            override fun validate(dto: T): Boolean = false
        }

        setupTestApp(dll, failingValidator) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, listOf(inputDto)))
            }

            val error = AsyncError.ApiRequest.TypeDefinitionValidation.run { listOf(ApiError(errorCode, message)) }
            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `POST create - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()
        val expectedErrors = AsyncError.ApiRequest.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

        setupTestApp(dll) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `POST createBatch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()
        val reqError = AsyncError.ApiRequest.RequestBodyMalformed
        val expectedErrors = AsyncError.ApiRequest.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(apiResponse.message, reqError.message)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `PUT update - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()
        val apiError = AsyncError.ApiRequest.RequestBodyMalformed.run { ApiError(errorCode, message) }
        val expectedErrors = AsyncError.ApiRequest.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

        setupTestApp(dll) {
            val response = client.put(route) {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(apiResponse.message, apiError.message)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `PUT updateBatch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()
        coEvery { dll.updateAll(any()) } returns AsyncResult.Success(emptyList())
        val apiError = AsyncError.ApiRequest.RequestBodyMalformed.run { ApiError(errorCode, message) }
        val expectedErrors = listOf(AsyncError.ApiRequest.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) })

        setupTestApp(dll) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(apiResponse.message, apiError.message)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `POST create batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
        val emptyList = emptyList<T>()
        val dll = mockk<DLL<ID, T>>()
        val expectedErrors = listOf(AsyncError.ApiRequest.EmptyListNotAllowed.run { ApiError(errorCode, message) })

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, emptyList))
            }


            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(AsyncError.ApiRequest.EmptyListNotAllowed.message, apiResponse.message)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `PUT update batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
        val emptyList = emptyList<T>()
        val dll = mockk<DLL<ID, T>>()
        coEvery { dll.updateAll(emptyList()) } returns AsyncResult.Success(emptyList())
        val apiError = AsyncError.ApiRequest.EmptyListNotAllowed.run { ApiError(errorCode, message) }
        val expectedResponse = ApiResponse<T>(success = false, message = apiError.message, errors = listOf(apiError))

        setupTestApp(dll) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, emptyList))
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(apiResponse.message, apiError.message)
            Assertions.assertEquals(expectedResponse.errors, apiResponse.errors)
        }
    }

    @Test
    fun `DELETE batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
        val emptyList = emptyList<T>()
        val dll = mockk<DLL<ID, T>>()
        coEvery { dll.deleteAll(emptyList()) } returns AsyncResult.Success(Unit)
        val apiError = AsyncError.ApiRequest.EmptyListNotAllowed.run { ApiError(errorCode, message) }
        val expectedResponse = ApiResponse<T>(success = false, message = apiError.message, errors = listOf(apiError))

        setupTestApp(dll) {
            val response = client.delete("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, emptyList))
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(apiResponse.message, apiError.message)
            Assertions.assertEquals(expectedResponse.errors, apiResponse.errors)
        }
    }
}
