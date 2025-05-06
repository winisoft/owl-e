package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data object RequestBodyMalformed : OwleError {
    override val errorCode: String get() = "MALFORMED_REQUEST_BODY"
    override val message: String get() = "Request body is missing or not readable as JSON"
    override val detail: String? get() = null
}