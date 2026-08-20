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
import org.jetbrains.exposed.v1.jdbc.Database


object Server {

    /**
     * The development database.
     *
     * Overridable by environment so this is not the only way to point the server somewhere else,
     * but defaulted so `./gradlew :server:run` works with no setup. Without a connection every
     * route answers with Exposed's `NotInitializedTransactionManager` cast failure, wrapped as a
     * generic database error - which reads like a query problem rather than an absent connection.
     */
    private fun connectDatabase() {
        Database.connect(
            url = System.getenv("OWLE_DB_URL") ?: "jdbc:mysql://localhost:3306/owle_dev",
            driver = "com.mysql.cj.jdbc.Driver",
            user = System.getenv("OWLE_DB_USER") ?: "dev_assistant",
            password = System.getenv("OWLE_DB_PASSWORD") ?: "*SandboxSquabble2"
        )
    }

    fun startServer() {

        connectDatabase()

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