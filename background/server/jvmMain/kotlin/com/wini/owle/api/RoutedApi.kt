package com.wini.owle.api

import io.ktor.server.application.*
import io.ktor.server.routing.*


interface RoutedApi {
    fun register(routing: Routing)
}