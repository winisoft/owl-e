package com.wini.owle.api

import com.wini.owle.async.AsyncError
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: String,
    val message: String,
    val detail: String? = null
) {

    fun fromAsyncError(asyncError: AsyncError): ApiError {
        return ApiError(
            code = asyncError.errorCode,
            message = asyncError.message
        )
    }

    companion object {
        fun fromAsyncError(error: AsyncError): ApiError {
            return ApiError(
                code = error.errorCode,
                message = error.message
            )
        }
    }
}