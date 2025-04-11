package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.validators.BaseValidator
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
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


abstract class BaseLongPkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<Long, T, R>() {

    abstract fun getDto(id: Long): T
    abstract fun getApiInstance(
        dll: DLL<Long, T>,
        validator: BaseValidator<T>
    ): R

    protected fun setupTestApp(
        dll: DLL<Long, T>,
        validator: BaseValidator<T> = BaseValidator.alwaysValid(),
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

    @Nested
    @DisplayName("Success")
    inner class Success {

        @Nested
        @DisplayName("GET")
        inner class Get {

            @Test
            fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
                val dto: T = getDto(1)
                val id = 1L
                val dll = mockk<DLL<Long, T>> {
                    coEvery { getById(id) } returns AsyncResult.Success(dto)
                }

                setupTestApp(dll) {
                    val response = client.get("$route/$id") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, dto))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.OK, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertEquals(dto, apiResponse.data)
                    Assertions.assertNull(apiResponse.errors)

                }
            }

            @Test
            fun `GET all - on success - returns HTTP Code 200, serialized list of DTO return val`() = runBlocking {
                val dtoList = listOf(getDto(1), getDto(2))
                val dll = mockk<DLL<Long, T>> {
                    coEvery { getAll() } returns AsyncResult.Success(data = dtoList)
                }

                setupTestApp(dll) {
                    val response = client.get(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, dtoList))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.OK, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertNull(apiResponse.errors)
                    Assertions.assertEquals(dtoList, apiResponse.data)
                }
            }

        }

        @Nested
        @DisplayName("POST")
        inner class Post {

            @Test
            fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
                val inputDto: T = getDto(1)
                val expectedResponse = ApiResponse(success = true, data = inputDto)
                val dll = mockk<DLL<Long, T>> {
                    coEvery { create(inputDto) } returns AsyncResult.Success(inputDto)
                }

                setupTestApp(dll) {
                    val response = client.post(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, inputDto))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.Created, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertNull(apiResponse.errors)
                    Assertions.assertEquals(inputDto, apiResponse.data)
                }
            }

            @Test
            fun `POST create batch - on success - returns HTTP Code 201, serialized list of DTO return val`() = runBlocking {
                val dtoList = listOf(getDto(1), getDto(2))
                val expectedResponse = ApiResponse(true, data = dtoList)
                val dll = mockk<DLL<Long, T>> {
                    coEvery { createAll(dtoList) } returns AsyncResult.Success(dtoList)
                }

                setupTestApp(dll) {
                    val response = client.post("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, dtoList))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.Created, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertNull(apiResponse.errors)
                    Assertions.assertEquals(dtoList, apiResponse.data)
                }
            }

        }

        @Nested
        @DisplayName("PUT")
        inner class Put {

            @Test
            fun `PUT update - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
                val inputDto: T = getDto(1)
                val expectedResponse = ApiResponse(success = true, data = inputDto)
                val dll = mockk<DLL<Long, T>> {
                    coEvery { update(inputDto) } returns AsyncResult.Success(inputDto)
                }

                setupTestApp(dll) {
                    val response = client.put(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, inputDto))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.OK, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertNull(apiResponse.errors)
                    Assertions.assertEquals(inputDto, apiResponse.data)
                }
            }

            @Test
            fun `PUT update batch - on success - returns HTTP Code 200, serialized list of DTO return val`() = runBlocking {
                val dtoList = listOf(getDto(1), getDto(2))
                val dll = mockk<DLL<Long, T>> {
                    coEvery { updateAll(dtoList) } returns AsyncResult.Success(dtoList)
                }

                setupTestApp(dll) {
                    val response = client.put("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, dtoList))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.OK, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertNull(apiResponse.errors)
                    Assertions.assertEquals(dtoList, apiResponse.data)
                }
            }

        }

        @Nested
        @DisplayName("DELETE")
        inner class Delete {

            @Test
            fun `DELETE by id - on success - returns HTTP Code 204, Unit`() = runBlocking {
                val id = 1L
                val dll = mockk<DLL<Long, T>> {
                    coEvery { delete(id) } returns AsyncResult.Success(Unit)
                }

                setupTestApp(dll) {
                    val response = client.delete("$route/$id") {
                        contentType(ContentType.Application.Json)
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.NoContent, response.status)
                    Assertions.assertTrue(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertNull(apiResponse.errors)
                }
            }

            @Test
            fun `DELETE batch - on success - returns HTTP Code 200, Unit`() = runBlocking {
                val idList = listOf(1L, 2L, 3L)
                val dll = mockk<DLL<Long, T>> {
                    coEvery { deleteAll(idList) } returns AsyncResult.Success(Unit)
                }

                setupTestApp(dll) {
                    val response = client.delete("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(ListSerializer(Long.serializer()), idList))
                    }

                    Assertions.assertEquals(HttpStatusCode.NoContent, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertNull(apiResponse.errors)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertTrue(apiResponse.success)
                }
            }
        }

    }

    @Nested
    @DisplayName("Validation Errors")
    inner class Validation {

        @Nested
        @DisplayName("POST")
        inner class Post {

            @Test
            fun `POST create - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
                val inputDto: T = getDto(1)
                val dll = mockk<DLL<Long, T>>()

                // Create a validator that always fails
                val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

                setupTestApp(dll, failingValidator) {
                    val response = client.post(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, inputDto))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
                }
            }

            @Test
            fun `POST createBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
                val inputDto: T = getDto(1)
                val dll = mockk<DLL<Long, T>>()

                // Create a validator that always fails
                val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

                setupTestApp(dll, failingValidator) {
                    val response = client.post("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, listOf(inputDto)))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
                }
            }

        }

        @Nested
        @DisplayName("PUT")
        inner class Put {

            @Test
            fun `PUT update - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
                val inputDto: T = getDto(1)
                val dll = mockk<DLL<Long, T>>()

                // Create a validator that always fails
                val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

                setupTestApp(dll, failingValidator) {
                    val response = client.put(route) {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, inputDto))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
                }
            }

            @Test
            fun `PUT updateBatch - on validation failure - returns HTTP Code 400, TypeDefinitionValidation`() = runBlocking {
                val inputDto: T = getDto(1)
                val dll = mockk<DLL<Long, T>>()

                // Create a validator that always fails
                val failingValidator = TestValidators.stringEmptyWithId1OnName<T>()

                setupTestApp(dll, failingValidator) {
                    val response = client.put("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(listSerializer, listOf(inputDto)))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(TestValidators.API_ERRORS_1_STR_EMPTY, apiResponse.errors)
                }
            }

        }

    }

    @Nested
    @DisplayName("Serialization Errors")
    inner class SerializationErrors {

        @Nested
        @DisplayName("POST")
        inner class Post {

            @Test
            fun `POST create - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
                val dll = mockk<DLL<Long, T>>()
                val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

                setupTestApp(dll) {
                    val response = client.post(route) {
                        contentType(ContentType.Application.Json)
                        setBody("{ invalid json }")
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())

                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                }
            }

            @Test
            fun `POST createBatch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
                val dll = mockk<DLL<Long, T>>()
                val reqError = AsyncError.ApiRequestError.RequestBodyMalformed
                val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

                setupTestApp(dll) {
                    val response = client.post("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody("{ invalid json }")
                    }

                    val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(apiResponse.message, reqError.message)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                }
            }

        }

        @Nested
        @DisplayName("PUT")
        inner class Put {

            @Test
            fun `PUT update - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
                val dll = mockk<DLL<Long, T>>()
                val apiError = AsyncError.ApiRequestError.RequestBodyMalformed.run { ApiError(errorCode, message) }
                val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

                setupTestApp(dll) {
                    val response = client.put(route) {
                        contentType(ContentType.Application.Json)
                        setBody("{ invalid json }")
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(apiResponse.message, apiError.message)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                }
            }

            @Test
            fun `PUT updateBatch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
                val dll = mockk<DLL<Long, T>>()
                coEvery { dll.updateAll(any()) } returns AsyncResult.Success(emptyList())
                val apiError = AsyncError.ApiRequestError.RequestBodyMalformed.run { ApiError(errorCode, message) }
                val expectedErrors = AsyncError.ApiRequestError.RequestBodyMalformed.run { listOf(ApiError(errorCode, message)) }

                setupTestApp(dll) {
                    val response = client.put("$route/batch") {
                        contentType(ContentType.Application.Json)
                        setBody("{ invalid json }")
                    }

                    val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertNull(apiResponse.data)
                    Assertions.assertEquals(apiResponse.message, apiError.message)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                }
            }

        }


        @Test
        fun `DELETE batch - on serialization error - returns HTTP Code 400, RequestBodyMalformed`() = runBlocking {
            val dll = mockk<DLL<Long, T>>()
            val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequestError.RequestBodyMalformed))

            setupTestApp(dll) {
                val response = client.delete("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody("{ invalid json }")
                }

                Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                Assertions.assertNull(apiResponse.data)
                Assertions.assertFalse(apiResponse.success)
                Assertions.assertEquals(expectedErrors, apiResponse.errors)
                Assertions.assertEquals(AsyncError.ApiRequestError.RequestBodyMalformed.message, apiResponse.message)
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
                val id = 1L
                val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.NoSuchIdError))
                val dll = mockk<DLL<Long, T>> {
                    coEvery {
                        getById(any<Long>())
                    } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
                }

                setupTestApp(dll) {
                    val response = client.get("$route/$id") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, getDto(1)))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
                    Assertions.assertEquals("No such ID", apiResponse.message)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                }
            }

            @Test
            fun `GET by id - with malformed parameter - returns HTTP Code 400, ParamValueMalformed`() = runBlocking {
                val dll = mockk<DLL<Long, T>>()
                val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequestError.ParamValueMalformedLong("id")))

                setupTestApp(dll) {
                    val response = client.get("$route/not-a-number") {
                        contentType(ContentType.Application.Json)
                    }

                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                    Assertions.assertNull(apiResponse.data)
                }
            }

        }

        @Nested
        @DisplayName("DELETE")
        inner class Delete {

            @Test
            fun `DELETE by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
                val id = 1L
                val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.NoSuchIdError))
                val dll = mockk<DLL<Long, T>> {
                    coEvery {
                        delete(id)
                    } returns AsyncResult.Failure(AsyncError.NoSuchIdError)
                }

                setupTestApp(dll) {
                    val response = client.delete("$route/$id") {
                        contentType(ContentType.Application.Json)
                        setBody(Json.encodeToString(serializer, getDto(1)))
                    }

                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
                    Assertions.assertEquals("No such ID", apiResponse.message)
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                }
            }

            @Test
            fun `DELETE by id - with malformed parameter - returns HTTP Code 400, ParamValueMalformed`() = runBlocking {
                val dll = mockk<DLL<Long, T>>()
                val expectedErrors = listOf(ApiError.fromAsyncError(AsyncError.ApiRequestError.ParamValueMalformedLong("id")))

                setupTestApp(dll) {
                    val response = client.delete("$route/not-a-number") {
                        contentType(ContentType.Application.Json)
                    }

                    Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                    val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
                    Assertions.assertFalse(apiResponse.success)
                    Assertions.assertEquals(expectedErrors, apiResponse.errors)
                    Assertions.assertNull(apiResponse.data)
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
            val dll = mockk<DLL<Long, T>>()
            val expectedErrors = listOf(AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) })

            setupTestApp(dll) {
                val response = client.post("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, emptyList))
                }


                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
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
            val dll = mockk<DLL<Long, T>>()
            coEvery { dll.updateAll(emptyList()) } returns AsyncResult.Success(emptyList())
            val apiError = AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) }
            val expectedResponse = ApiResponse<T>(success = false, message = apiError.message, errors = listOf(apiError))

            setupTestApp(dll) {
                val response = client.put("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, emptyList))
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
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
            val dll = mockk<DLL<Long, T>>()
            coEvery { dll.deleteAll(emptyList()) } returns AsyncResult.Success(Unit)
            val apiError = AsyncError.ApiRequestError.EmptyListNotAllowed.run { ApiError(errorCode, message) }
            val expectedResponse = ApiResponse<T>(success = false, message = apiError.message, errors = listOf(apiError))

            setupTestApp(dll) {
                val response = client.delete("$route/batch") {
                    contentType(ContentType.Application.Json)
                    setBody(Json.encodeToString(listSerializer, emptyList))
                }

                val apiResponse = Json.decodeFromString(apiResponseListSerializer, response.bodyAsText())
                Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
                Assertions.assertFalse(apiResponse.success)
                Assertions.assertNull(apiResponse.data)
                Assertions.assertEquals(apiResponse.message, apiError.message)
                Assertions.assertEquals(expectedResponse.errors, apiResponse.errors)
            }
        }

    }

}
