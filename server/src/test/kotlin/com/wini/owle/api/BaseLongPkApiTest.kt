package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
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
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id = 1L
        val dll = mockk<DLL<Long, T>> {
            coEvery {
                getById(id)
            } returns AsyncResult.Failure(AsyncError.NoSuchIdError("Entity with id $id not found"))
        }

        setupTestApp(dll) {
            val response = client.get("$route/$id") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("NoSuchIdError"))
        }
    }

    @Test
    fun `DELETE by id - on success - returns HTTP Code 200, Unit`() = runBlocking {
        val id = 1L
        val dll = mockk<DLL<Long, T>> {
            coEvery { delete(id) } returns AsyncResult.Success(Unit)
        }

        setupTestApp(dll) {
            val response = client.delete("$route/$id") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
        }
    }

    @Test
    fun `GET by id - with invalid id format - returns HTTP Code 400, LongIdInvalid`() = runBlocking {
        val dll = mockk<DLL<Long, T>>()

        setupTestApp(dll) {
            val response = client.get("$route/not-a-number") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("LongIdInvalid"))
        }
    }

    @Test
    fun `DELETE by id - with invalid id format - returns HTTP Code 400, LongIdInvalid`() = runBlocking {
        val dll = mockk<DLL<Long, T>>()

        setupTestApp(dll) {
            val response = client.delete("$route/not-a-number") {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("LongIdInvalid"))
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
        }
    }

    @Test
    fun `DELETE batch - on serialization error - returns HTTP Code 400, SerializationError`() = runBlocking {
        val dll = mockk<DLL<Long, T>>()

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
}