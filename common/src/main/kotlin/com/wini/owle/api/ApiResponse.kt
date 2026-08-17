package com.wini.owle.api

import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

@ModelDefinition(skipValidation = true)
@Serializable
data class ApiResponse<T>(

    /**
     * Indicates the request executed without error.
     */
    val success: Boolean,

    /**
     * A summary of the primary error underlying a request's failure
     */
    val message: String? = null,

    /**
     * The result data of a successful request
     */
    val data: T? = null,

    /**
     * Errors that represent the cause of the request's failure
     */
    val errors: List<ApiError>? = null

)