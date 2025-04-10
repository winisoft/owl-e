package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


abstract class BaseCompositePkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<CompositeID, T, R>() {

    // This should be overridden by subclasses to create a valid route for GET by ID
    protected open val getByIdRoute: String
        get() = "$route/id"

    // This should be overridden by subclasses to create a valid route for DELETE by ID
    protected open val deleteByIdRoute: String
        get() = "$route/id"
    
    // This should be overridden by subclasses to create a route with missing parameters for testing CompositeIDError
    protected open val invalidIdRoute: String
        get() = "$route/missing-params"
    
    abstract fun getDto(id1: Long, id2: Long): T

    @Test
    open fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: T = getDto(1, 1)
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, T>> {
            coEvery { getById(id) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get(getByIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    open fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, T>> {
            coEvery {
                getById(id)
            } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
        }

        setupTestApp(dll) {
            val response = client.get(getByIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

    @Test
    fun `DELETE by id - on success - returns HTTP Code 200, Unit`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, T>> {
            coEvery { delete(id) } returns AsyncResult.Success(Unit)
        }

        setupTestApp(dll) {
            val response = client.delete(deleteByIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
        }
    }

    @Test
    open fun `GET by id - with missing parameters - returns HTTP Code 400, CompositeIDError`() = runBlocking {
        val dll = mockk<DLL<CompositeID, T>>()

        setupTestApp(dll) {
            val response = client.get(invalidIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("CompositeIDError"))
        }
    }

    @Test
    open fun `DELETE by id - with missing parameters - returns HTTP Code 400, CompositeIDError`() = runBlocking {
        val dll = mockk<DLL<CompositeID, T>>()

        setupTestApp(dll) {
            val response = client.delete(invalidIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("CompositeIDError"))
        }
    }

    @Test
    fun `DELETE batch - on success - returns HTTP Code 200, Unit`() = runBlocking {
        val dtoList = listOf(getDto(1,1), getDto(2, 2))
        val dll = mockk<DLL<CompositeID, T>> {
            coEvery { deleteAll(any()) } returns AsyncResult.Success(Unit)
        }

        setupTestApp(dll) {
            val response = client.delete("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
        }
    }

    @Test
    fun `DELETE batch - on serialization error - returns HTTP Code 400, SerializationError`() = runBlocking {
        val dll = mockk<DLL<CompositeID, T>>()

        setupTestApp(dll) {
            val response = client.delete("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("SerializationError"))
        }
    }

    @Test
    fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto(1, 1)
        val expectedResponse = ApiResponse(success = true, data = inputDto)
        val dll = mockk<DLL<CompositeID, T>> {
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
        val dtoList = listOf(getDto(1, 1), getDto(2, 2))
        val dll = mockk<DLL<CompositeID, T>> {
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
        val inputDto: T = getDto(1, 1)
        val expectedResponse = ApiResponse(success = true, data = inputDto)
        val dll = mockk<DLL<CompositeID, T>> {
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
        val dtoList = listOf(getDto(1, 1), getDto(2, 2))
        val expectedResponse = ApiResponse(true, data = dtoList)
        val dll = mockk<DLL<CompositeID, T>> {
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
        val dtoList = listOf(getDto(1, 1), getDto(2, 2))
        val dll = mockk<DLL<CompositeID, T>> {
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
        val inputDto: T = getDto(1, 1)
        val dll = mockk<DLL<CompositeID, T>>()

        // Create a validator that always fails
        val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

        setupTestApp(dll, failingValidator) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
        }
    }

    @Test
    fun `POST createBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto(1, 1)
        val dll = mockk<DLL<CompositeID, T>>()

        // Create a validator that always fails
        val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

        setupTestApp(dll, failingValidator) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, listOf(inputDto)))
            }

            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
        }
    }

    @Test
    fun `PUT update - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto(1, 1)
        val dll = mockk<DLL<CompositeID, T>>()

        // Create a validator that always fails
        val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

        setupTestApp(dll, failingValidator) {
            val response = client.put(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
        }
    }

    @Test
    fun `PUT updateBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
        val inputDto: T = getDto(1, 1)
        val dll = mockk<DLL<CompositeID, T>>()

        // Create a validator that always fails
        val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

        setupTestApp(dll, failingValidator) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, listOf(inputDto)))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
        }
    }

    @Test
    fun `POST create - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
        val dll = mockk<DLL<CompositeID, T>>()
        val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

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
        val dll = mockk<DLL<CompositeID, T>>()
        val reqError = AsyncError.ApiRequestError.RequestBodyMalformed
        val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

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
        val dll = mockk<DLL<CompositeID, T>>()
        val apiError = AsyncError.ApiRequestError.RequestBodyMalformed.run { ApiError(errorCode, message) }
        val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

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
        val dll = mockk<DLL<CompositeID, T>>()
        coEvery { dll.updateAll(any()) } returns AsyncResult.Success(emptyList())
        val apiError = AsyncError.ApiRequestError.RequestBodyMalformed.run { ApiError(errorCode, message) }
        val expectedErrors = listOf(AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) })

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
        val dll = mockk<DLL<CompositeID, T>>()
        val expectedErrors = listOf(AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) })

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, emptyList))
            }


            val apiResponse = json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertEquals(AsyncError.ApiRequestError.EmptyListNotAllowed.message, apiResponse.message)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

    @Test
    fun `PUT update batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
        val emptyList = emptyList<T>()
        val dll = mockk<DLL<CompositeID, T>>()
        coEvery { dll.updateAll(emptyList()) } returns AsyncResult.Success(emptyList())
        val apiError = AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) }
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
        val dll = mockk<DLL<CompositeID, T>>()
        coEvery { dll.deleteAll(emptyList()) } returns AsyncResult.Success(Unit)
        val apiError = AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) }
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