package com.wini.owle.air.ktor.interceptor

import io.ktor.server.application.ApplicationCall

interface IApplicationCallHook {
    suspend fun intercept(call: ApplicationCall)
}