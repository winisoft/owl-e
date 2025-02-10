package com.wini.owle.air.ktor.interceptor

import io.ktor.http.HttpMethod

abstract class CallParamRoutingInterceptor(
    val httpMethod: HttpMethod,
    val path: String
) : IApplicationCallHook