package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data object AuthenticationError : OwleError {
    override val errorCode = "AUTHENTICATION_ERROR"
    override val message = "Authentication Error"
    override val detail = null
}