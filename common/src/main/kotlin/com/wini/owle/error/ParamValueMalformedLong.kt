package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data class ParamValueMalformedLong(
    val paramKey: String,
    val formatDescr: String = "number"
) : OwleError {
    override val errorCode = "MALFORMED_PARAM_VALUE"
    override val message = "Parameter provided in an invalid format."
    override val detail = "'$paramKey' must be a valid whole number."
}