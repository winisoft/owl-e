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
    
    // This should be overridden by subclasses to create a route with missing parameters for testing CompositeIdError
    protected open val invalidIdRoute: String
        get() = "$route/missing-params"

    @Test
    open fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: T = getDto()
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
            } returns AsyncResult.Failure(AsyncError.NoSuchIdError("Entity with id $id not found"))
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
    open fun `GET by id - with missing parameters - returns HTTP Code 400, CompositeIdError`() = runBlocking {
        val dll = mockk<DLL<CompositeID, T>>()

        setupTestApp(dll) {
            val response = client.get(invalidIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("CompositeIdError"))
        }
    }

    @Test
    open fun `DELETE by id - with missing parameters - returns HTTP Code 400, CompositeIdError`() = runBlocking {
        val dll = mockk<DLL<CompositeID, T>>()

        setupTestApp(dll) {
            val response = client.delete(invalidIdRoute) {
                contentType(ContentType.Application.Json)
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("CompositeIdError"))
        }
    }

    @Test
    fun `DELETE batch - on success - returns HTTP Code 200, Unit`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
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
}