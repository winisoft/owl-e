package com.wini.owle.async

import kotlinx.serialization.Serializable

@Serializable
sealed class AsyncError {

    abstract val message: String?

    fun asFailure(): AsyncResult.Failure {
        return  AsyncResult.Failure(this)
    }

    @Serializable
    data class DatabaseError(override val message: String) : AsyncError()

    @Serializable
    data class NoSuchIdError(override val message: String) : AsyncError()

    @Serializable
    data class ApplicationError(override val message: String) : AsyncError()

    @Serializable
    data class NetworkError(
        override val message: String?,
        val statusCode: Int
    ) : AsyncError()

    @Serializable
    data class AuthenticationError(override val message: String) : AsyncError()

    @Serializable
    data class AuthorizationError(override val message: String) : AsyncError()

    @Serializable
    data class ValidationError(override val message: String, val validationErrors: List<String>) : AsyncError()



    @Serializable
    data class UnknownError(override val message: String) : AsyncError()

    @Serializable
    data class ServiceLayerError(override val message: String) : AsyncError()

}
