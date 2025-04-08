package com.wini.owle.api

import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeID


abstract class BaseCompositePkApiTest<T: BaseDto, R: RoutedApi>: BaseEndpointTest<CompositeID, T, R>() {

    private val json = Json { prettyPrint = false; encodeDefaults = true }

    private fun setupTestApp(
        dll: DLL<CompositeID, T>,
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

}