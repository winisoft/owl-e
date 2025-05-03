package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data object AuthorizationError : OwleError {
    override val errorCode = "AUTHORIZATION_ERROR"
    override val message = "Authorization Error"
    override val detail = null
}