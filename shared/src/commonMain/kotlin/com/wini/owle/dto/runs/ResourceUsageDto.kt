package com.wini.owle.dto.runs

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class ResourceUsageDto(
    val name: String? = null
) {
    companion object {
        val jsonConfig = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
}