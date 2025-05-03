package com.wini.owle.api

import com.wini.owle.dto.BaseDto
import io.ktor.http.HttpMethod
import kotlin.reflect.KClass

data class RouteSpec<T: BaseDto>(
    val method: HttpMethod,
    val path: String,
    val requestBody: KClass<T>?,
    val responseBody: KClass<*>,
    val errors: List<ApiError>,
    val description: String? = null,
)