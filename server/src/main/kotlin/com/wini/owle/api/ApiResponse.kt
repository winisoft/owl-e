package com.wini.owle.api

import com.wini.owle.ksp.OwleModel
import kotlinx.serialization.Serializable

@OwleModel(

)
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val errors: List<ApiError>? = null
)

