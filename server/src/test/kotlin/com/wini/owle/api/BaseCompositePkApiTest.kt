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

    @Test
    open fun `GET by id - on success - returns HTTP Code 200, serialized DTO return val`() = runBlocking {
        val dto: T = getDto()
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, T>> {
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
    open fun `GET by id - on not found - returns HTTP Code 404, NoSuchIdError`() = runBlocking {
        val id: CompositeID = mockk()
        val dll = mockk<DLL<CompositeID, T>> {
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

}