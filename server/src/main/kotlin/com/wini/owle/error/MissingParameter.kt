package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data class MissingParameter(
    val paramKey: String
) : OwleError {
    override val errorCode = "MISSING_PARAM_VALUE"
    override val message = "Missing required parameter"
    override val detail = "'$paramKey' was not provided"
}