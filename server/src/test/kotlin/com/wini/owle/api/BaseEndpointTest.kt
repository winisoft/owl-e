package com.wini.owle.api

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json


abstract class BaseEndpointTest<ID: Any, T: BaseDto, R: RoutedApi> {

    protected val json = Json { prettyPrint = false; encodeDefaults = true }

    val apiResponseOneSerializer get() = ApiResponse.serializer(serializer)

    val apiResponseListSerializer get() = ApiResponse.serializer(listSerializer)

    abstract val route: String

    abstract val serializer: KSerializer<T>

    abstract val listSerializer: KSerializer<List<T>>


}
