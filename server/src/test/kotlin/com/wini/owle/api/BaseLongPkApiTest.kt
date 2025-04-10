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
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


abstract class BaseLongPkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<Long, T, R>() {


    @Test
    fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: T = getDto()
        val id = 1L
        val dll = mockk<DLL<Long, T>> {
            coEvery { getById(id) } returns AsyncResult.Success(dto)
        }

        setupTestApp(dll) {
            val response = client.get("$route/$id") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, dto))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertTrue(apiResponse.success)
            Assertions.assertEquals(dto, apiResponse.data)
            Assertions.assertNull(apiResponse.errors)

        }
    }

    @Test
    fun `GET by id - on not found - returns HTTP Code 400, NoSuchIdError`() = runBlocking {
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
                setBody(json.encodeToString(serializer, getDto()))
            }

            val apiResponse = json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            Assertions.assertEquals("Entity with id $id not found", apiResponse.message)
            Assertions.assertFalse(apiResponse.success)
            Assertions.assertEquals(expectedErrors, apiResponse.errors)
        }
    }

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
    fun `GET by id - with invalid id format - returns HTTP Code 400, ParamValueMalformed`() = runBlocking {
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

    @Test
    fun `DELETE by id - with invalid id format - returns HTTP Code 400, ParamValueMalformed`() = runBlocking {
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

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            val apiResponse = Json.decodeFromString(apiResponseOneSerializer, response.bodyAsText())
            Assertions.assertNull(apiResponse.errors)
            Assertions.assertNull(apiResponse.data)
            Assertions.assertTrue(apiResponse.success)
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