package com.wini.owle.api

import com.wini.owle.AppInitializer
import io.ktor.server.application.*

suspend fun Application.startup() {
    AppInitializer
}