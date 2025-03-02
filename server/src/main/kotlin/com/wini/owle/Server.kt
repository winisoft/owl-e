package com.wini.owle

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun startServerSafely() {
    try {
        startServer()
    } catch (e: Exception) {
        println("Failed to start server: ${e.message}")
        e.printStackTrace()
    }
}

fun startServer() {

    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)

}

fun Application.module() {
    routing {
        get("/") {}
    }
}