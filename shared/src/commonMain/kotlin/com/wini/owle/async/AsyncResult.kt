package com.wini.owle.async

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
sealed class AsyncResult<out T> {

    @Serializable
    @SerialName("success")
    data class Success<T>(val data: T): AsyncResult<T>()

    @Serializable
    @SerialName("failure")
    data class Failure(
        val error: AsyncError
    ): AsyncResult<Nothing>()

}