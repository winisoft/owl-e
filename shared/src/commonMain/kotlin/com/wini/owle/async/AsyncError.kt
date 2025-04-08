package com.wini.owle.async

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable


@Serializable
sealed class AsyncError {

    abstract val errorCode: String

    /**
     * Convenience method called from the site of a detected AsyncError that points to its containing AsyncResult.Failure
     * type, which is what the function intends to return. In other words, AsyncError is a bundle of data elaborating
     * and error, and when one is arrived upon, it is almost always a logical dead-end from which a result-encapsulating
     * type will be resulting.
     */
    fun asFailure(): AsyncResult.Failure {
        return AsyncResult.Failure(this)
    }

    /**
     * Subclasses of AsyncError, rooted in invariant failure, illegal state, or domain misuse.
     */
    @Serializable
    data class InvariantViolation(
        val invariant: String,
        val message: String? = null
    ) : AsyncError() {
        override val errorCode: String get() = "INVARIANT_VIOLATION"
    }

    @Serializable
    data class InvalidStateTransition(
        val fromState: String,
        val toState: String,
        val message: String? = null
    ) : AsyncError() {
        override val errorCode: String get() = "INVALID_STATE_TRANSITION"
    }



    /**
     * The highest order of error, the 'last line of defense' that represents a complete failure to anticipate the
     * error that occurred at any and every layer of dependencies it may have been introduced
     */
    @Serializable
    data class ApplicationError(val message: String) : AsyncError() {
        override val errorCode: String get() = "APPLICATION_ERROR"
    }

    @Serializable
    data object DbTransactionError : AsyncError() {
        override val errorCode: String get() = "DB_TRANSACTION_ERROR"
    }

    /**
     * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
     * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
     * with the database and the application's efficacy representing the concurrent operations globally
     */
    @Serializable
    data class NoSuchIdError(val id: String) : AsyncError() {
        override val errorCode: String get() = "NO_SUCH_ID"
    }

    @Serializable
    data object AuthenticationError : AsyncError() {
        override val errorCode: String get() = "AUTHENTICATION_ERROR"
    }

    @Serializable
    data object AuthorizationError : AsyncError() {
        override val errorCode: String get() = "AUTHORIZATION_ERROR"
    }

    @Serializable
    sealed class ApiRequest : AsyncError() {

        @Serializable
        data object MissingId : ApiRequest() {
            override val errorCode: String get() = "MISSING_ID"
        }

        @Serializable
        data object InvalidLongId : ApiRequest() {
            override val errorCode: String get() = "INVALID_ID"
        }

        @Serializable
        data object MalformedBodyData : ApiRequest() {
            override val errorCode: String get() = "MALFORMED_REQUEST_BODY"
        }

        @Serializable
        data object ConstraintViolationData : ApiRequest() {
            override val errorCode: String get() = "OBJECT_CONSTRAINT_VIOLATION"
        }

        @Serializable
        data object MissingCompositeIdElement : ApiRequest() {
            override val errorCode: String get() = "MISSING_COMPOSITE_ID_ELEMENT"
        }

        @Serializable
        data class InvalidCompositeIdElement(val paramName: String) : ApiRequest() {
            override val errorCode: String get() = "INVALID_COMPOSITE_ID_ELEMENT"
        }

        @Serializable
        data class UnexpectedField(val field: String) : ApiRequest() {
            override val errorCode: String get() = "UNEXPECTED_FIELD"
        }

        @Serializable
        data class MissingField(val field: String) : ApiRequest() {
            override val errorCode: String get() = "MISSING_FIELD"
        }

        @Serializable
        data class InvalidField(val field: String, val reason: String) : ApiRequest() {
            override val errorCode: String get() = "INVALID_FIELD"
        }


    }

    @Serializable
    data object ServiceLayerError : AsyncError() {
        override val errorCode: String get() = "SERVICE_FAILURE"
    }

    @Serializable
    sealed class DatabaseError : AsyncError() {
        override val errorCode: String get() = "DATABASE_ERROR"

        @Serializable
        data class ConstraintViolation(
            val message: String? = "The operation violated a database constraint."
        ) : DatabaseError() {
            override val errorCode: String get() = "DB_CONSTRAINT_VIOLATION"
        }

        @Serializable
        data class Unspecified(
            val message: String? = "The operation could not be completed."
        ) : DatabaseError() {
            override val errorCode: String get() = "DB_UNSPECIFIED"
        }

        @Serializable
        data class ConnectionFailure(
            val message: String? = "Could not connect to the database."
        ) : DatabaseError() {
            override val errorCode: String get() = "DB_CONNECTION_FAILURE"
        }

        @Serializable
        data class Timeout(
            val message: String? = "The database operation timed out."
        ) : DatabaseError() {
            override val errorCode: String get() = "DB_TIMEOUT"
        }

        @Serializable
        data class DeadlockDetected(
            val message: String? = "A deadlock was detected and the transaction was rolled back."
        ) : DatabaseError() {
            override val errorCode: String get() = "DB_DEADLOCK"
        }
    }


    @Serializable
    data class PartialFailure<T : BaseDto>(
        val failed: List<Pair<T, AsyncError>>,
        val successful: List<T>
    ) : AsyncError() {
        override val errorCode: String get() = "PARTIAL_FAILURE"
    }


    @Serializable
    data class ValidationError(
        val fieldErrors: Map<String, String>
    ) : AsyncError() {
        override val errorCode: String get() = "VALIDATION_ERROR"
    }

    @Serializable
    sealed class ExternalDependencyError : AsyncError() {
        @Serializable
        data class HttpFailure(val service: String, val status: Int, val body: String? = null) : ExternalDependencyError() {
            override val errorCode: String get() = "EXTERNAL_HTTP_FAILURE"
        }

        @Serializable
        data class Timeout(val service: String) : ExternalDependencyError() {
            override val errorCode: String get() = "EXTERNAL_TIMEOUT"
        }

        @Serializable
        data class UnexpectedResponse(val service: String, val detail: String) : ExternalDependencyError() {
            override val errorCode: String get() = "EXTERNAL_BAD_RESPONSE"
        }
    }


}

