package com.wini.owle.api

import com.wini.owle.error.OwleError
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

@ModelDefinition(skipValidation = true)
@Serializable
data class ApiError(
    /**
     * A unique identifier pointing to full set of errors that may be the cause of an API request's failure
     */
    val code: String,

    /**
     * A categorically true summary of the cause for the raised class of error.
     */
    val message: String,

    /**
     * Additional insights, such as the context in which the error occurred, the class name and / or the unique
     * identifier of a data element that could not be processed, the expected value vs. the actual value, etc.
     */
    val detail: String? = null
) {

    constructor(owleError: OwleError): this(
        code = owleError.errorCode,
        message = owleError.message,
        detail = owleError.detail
    )

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