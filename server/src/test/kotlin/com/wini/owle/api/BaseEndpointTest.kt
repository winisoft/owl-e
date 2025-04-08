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
        validator: (T) -> Boolean = { _-> true },
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

    abstract val route: String
    abstract fun getApiInstance(dll: DLL<ID, T>, validator: (T) -> Boolean): R
    abstract fun getDto(): T
    abstract val serializer: KSerializer<T>
    abstract val listSerializer: KSerializer<List<T>>

    @Test
    fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto()
        val dll = mockk<DLL<ID, T>> {
            coEvery { create(inputDto) } returns AsyncResult.Success(inputDto)
        }

        setupTestApp(dll) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertEquals(inputDto, json.decodeFromString(serializer, response.bodyAsText()))
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

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dtoList, json.decodeFromString(listSerializer, response.bodyAsText()))
        }
    }

    @Test
    fun `PUT update - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto()
        val dll = mockk<DLL<ID, T>> {
            coEvery { update(inputDto) } returns AsyncResult.Success(inputDto)
        }

        setupTestApp(dll) {
            val response = client.put(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(inputDto, json.decodeFromString(serializer, response.bodyAsText()))
        }
    }

    @Test
    fun `POST create batch - on success - returns HTTP Code 201, serialized list of DTO return val`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val dll = mockk<DLL<ID, T>> {
            coEvery { createAll(dtoList) } returns AsyncResult.Success(dtoList)
        }

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            Assertions.assertEquals(HttpStatusCode.Created, response.status)
            Assertions.assertEquals(dtoList, json.decodeFromString(listSerializer, response.bodyAsText()))
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

            Assertions.assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertEquals(dtoList, json.decodeFromString(listSerializer, response.bodyAsText()))
        }
    }

    @Test
    fun `POST create - on validation failure - returns HTTP Code 400, DtoConstraintError`() = runBlocking {
        val inputDto: T = getDto()
        val dll = mockk<DLL<ID, T>>()
        val validator: (T) -> Boolean = { _-> false }
        val apiInstance = getApiInstance(dll, validator)

        testApplication {
            application {
                install(ContentNegotiation) { json(json) }
                routing {
                    apiInstance.register(this)
                }
            }
            
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("DtoConstraintError"))
        }
    }

    @Test
    fun `POST create - on serialization error - returns HTTP Code 400, SerializationError`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()

        setupTestApp(dll) {
            val response = client.post(route) {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("SerializationError"))
        }
    }

    @Test
    fun `PUT update - on validation failure - returns HTTP Code 400, DtoConstraintError`() = runBlocking {
        val inputDto: T = getDto()
        val dll = mockk<DLL<ID, T>>()
        val validator: (T) -> Boolean = { _-> false }

        setupTestApp(dll, validator) {
            val response = client.put(route) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(serializer, inputDto))
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("DtoConstraintError"))
        }
    }

    @Test
    fun `POST create batch - on validation failure - returns HTTP Code 400, DtoConstraintError`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val dll = mockk<DLL<ID, T>>()
        val validator: (T) -> Boolean = { _-> false }

        setupTestApp(dll, validator) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("DtoConstraintError"))
        }
    }

    @Test
    fun `PUT update batch - on validation failure - returns HTTP Code 400, DtoConstraintError`() = runBlocking {
        val dtoList = listOf(getDto(), getDto())
        val dll = mockk<DLL<ID, T>>()
        val validator: (T) -> Boolean = { _-> false }

        setupTestApp(dll, validator) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, dtoList))
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("DtoConstraintError"))
        }
    }

    @Test
    fun `POST create batch - on serialization error - returns HTTP Code 400, SerializationError`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("SerializationError"))
        }
    }

    @Test
    fun `PUT update batch - on serialization error - returns HTTP Code 400, SerializationError`() = runBlocking {
        val dll = mockk<DLL<ID, T>>()

        setupTestApp(dll) {
            val response = client.put("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody("{ invalid json }")
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("SerializationError"))
        }
    }

    @Test
    fun `POST create batch - on empty list - returns HTTP Code 400, EmptyList`() = runBlocking {
        val emptyList = emptyList<T>()
        val dll = mockk<DLL<ID, T>>()

        setupTestApp(dll) {
            val response = client.post("$route/batch") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(listSerializer, emptyList))
            }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
            val responseText = response.bodyAsText()
            Assertions.assertTrue(responseText.contains("EmptyList"))
        }
    }
}
