package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data class MultipleParamFailure(
    val failed: List<OwleError>
) : OwleError {
    override val errorCode: String get() = "MULTI_PARAM_FAILURE"
    override val message: String get() = "The required values were not provided in valid format."
    override val detail: String? get() = null
}