package com.wini.owle.api

import com.wini.owle.error.OwleError
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: String,
    val message: String,
    val detail: String? = null
) {

    constructor(owleError: OwleError): this(
        code = owleError.errorCode,
        message = owleError.message,
        detail = owleError.detail
    )

    fun fromAsyncError(owleError: OwleError): ApiError {
        return ApiError(
            code = owleError.errorCode,
            message = owleError.message,
            detail = owleError.detail
        )
    }

    companion object {
        fun fromAsyncError(error: OwleError): ApiError {
            return ApiError(
                code = error.errorCode,
                message = error.message,
                detail = error.detail
            )
        }
    }
}