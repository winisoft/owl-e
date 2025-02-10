package com.wini.owle.air.ktor.feature


import io.ktor.server.application.*
import io.ktor.server.engine.*

interface ServerFeature {
    fun install(application: Application)
}