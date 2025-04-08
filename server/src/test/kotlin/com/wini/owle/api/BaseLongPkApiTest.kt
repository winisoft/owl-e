package com.wini.owle.api

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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock


abstract class BaseLongPkApiTest<T: BaseDto, R: RoutedApi> {


    private val json = Json { prettyPrint = false; encodeDefaults = true }

    private fun setupTestApp(
        dll: DLL<Long, T>,
        testBlock: suspend ApplicationTestBuilder.() -> Unit
    ) = testApplication {
        application {
            install(ContentNegotiation) { json(json) }
            routing {
                getApiInstance(dll).register(this)
            }
        }
        testBlock()
    }

    abstract val route: String
    abstract fun getApiInstance(dll: DLL<Long, T>): R
    abstract fun getDto(): T
    abstract val serializer: KSerializer<T>
    abstract val listSerializer: KSerializer<List<T>>

    @Test
    fun `POST create - on success - returns HTTP Code 201, serialized DTO return val`() = runBlocking {
        val inputDto: T = getDto()
        val dll = mock<DLL<Long, T>> {
            onBlocking { create(eq(inputDto)) } doReturn AsyncResult.Success(inputDto)
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
        val dll = mock<DLL<Long, T>> {
            onBlocking { getAll() } doReturn AsyncResult.Success(data = dtoList)
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
    
}

