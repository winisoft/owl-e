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
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock


abstract class BaseLongPkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<Long, T, R>() {


    
}

