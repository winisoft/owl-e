package com.wini.owle

import com.wini.owle.api.KtorApi
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


object Server {

    fun startServer() {


        try {
            embeddedServer(
                factory = Netty,
                port = SERVER_PORT,
                host = "0.0.0.0",
                module = { ktorModule() }
            ).start(wait = true)
        } catch (e: Exception) {
            println("Failed to start server: ${e.message}")
            e.printStackTrace()
        }
    }

    private val koinApp: KoinApplication = startKoin {
        printLogger(Level.DEBUG)
        modules(allModules)
    }

    private fun Application.ktorModule() {

        routing {

            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true; ignoreUnknownKeys = true })
            }

            koinApp.koin.getAll<KtorApi<*>>()
                .forEach { it.register(this) }
        }

    }

}