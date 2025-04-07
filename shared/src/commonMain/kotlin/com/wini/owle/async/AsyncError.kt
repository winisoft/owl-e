package com.wini.owle.async

import kotlinx.serialization.Serializable


@Serializable
sealed class AsyncError {

    abstract val message: String?

    /**
     * Convenience method called from the site of a detected AsyncError that points to its containing AsyncResult.Failure
     * type, which is what the function intends to return. In other words, AsyncError is a bundle of data elaborating
     * and error, and when one is arrived upon, it is almost always a logical dead-end from which a result-encapsulating
     * type will be resulting.
     */
    fun asFailure(): AsyncResult.Failure {
        return  AsyncResult.Failure(this)
    }

    /**
     * The highest order of error, the 'last line of defense' that represents a complete failure to anticipate the
     * error that occurred at any and every layer of dependencies it may have been introduced
     */
    @Serializable
    data class ApplicationError(override val message: String) : AsyncError()

    /**
     * Errors that represent any failure taking place in the workings of the MySQL persistence layer
     */
    @Serializable
    data class Database(override val message: String) : AsyncError()

    /**
     * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
     * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
     * with the database and the application's efficacy representing the concurrent operations globally
     */
    @Serializable
    data class NoSuchIdError(override val message: String) : AsyncError()


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
    sealed class ApiRequest: AsyncError() {

        @Serializable
        data object LongIdInvalid: ApiRequest() {
            override val message: String = "The expected default parameter with Long type named 'id' was not provided or invalid"
        }

        @Serializable
        data object SerializationError: ApiRequest() {
            override val message: String = "The HTTP call's json body was not legal structurally to fulfill the contract of the controller"
        }

        @Serializable
        data object DtoConstraintError: ApiRequest() {
            override val message: String = "The validation function of the DTO class detected data that violates a constraint on it"
        }

        @Serializable
        data object CompositeIdError: ApiRequest() {
            override val message: String = "One or more of the expected request parameters specifying the composite id element values by name was not provided or invalid"
        }

        @Serializable
        data object EmptyList: ApiRequest() {
            override val message: String = "The expected list of references to data members to operate on contained no values"
        }

    }




    @Serializable
    data class ServiceLayerError(override val message: String) : AsyncError()

}
