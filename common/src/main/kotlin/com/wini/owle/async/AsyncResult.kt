package com.wini.owle.async

import com.wini.owle.error.OwleError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class AsyncResult<out T> {

    @Serializable
    @SerialName("success")
    data class Success<T>(val data: T): AsyncResult<T>()

    @Serializable
    @SerialName("failure")
    data class Failure(
        val error: OwleError
    ): AsyncResult<Nothing>()

}
