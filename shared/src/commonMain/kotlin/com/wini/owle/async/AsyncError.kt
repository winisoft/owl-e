package com.wini.owle.async

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable


@Serializable
sealed class AsyncError {

    abstract val errorCode: String
    abstract val message: String
    abstract val detail: String?

    /**
     * Convenience method called from the site of a detected AsyncError that points to its containing AsyncResult.Failure
     * type, which is what the function intends to return. In other words, AsyncError is a bundle of data elaborating
     * and error, and when one is arrived upon, it is almost always a logical dead-end from which a result-encapsulating
     * type will be resulting.
     */
    fun asFailure(): AsyncResult.Failure {
        return AsyncResult.Failure(this)
    }

    @Serializable
    data object DbTransactionError : AsyncError() {
        override val errorCode: String get() = "DB_TRANSACTION_ERROR"
        override val message: String get() = "Database transaction error."
        override val detail: String? get() = null
    }

    /**
     * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
     * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
     * with the database and the application's efficacy representing the concurrent operations globally
     */
    @Serializable
    data object NoSuchIdError : AsyncError() {
        override val errorCode: String get() = "NO_SUCH_ID"
        override val message: String get() = "No such ID"
        override val detail: String? get() = null
    }

    @Serializable
    data object AuthenticationError : AsyncError() {
        override val errorCode: String get() = "AUTHENTICATION_ERROR"
        override val message: String get() = "Authentication Error"
        override val detail: String? get() = null
    }

    @Serializable
    data object AuthorizationError : AsyncError() {
        override val errorCode: String get() = "AUTHORIZATION_ERROR"
        override val message: String get() = "Authorization Error"
        override val detail: String? get() = null
    }

    @Serializable
    sealed class ApiRequest : AsyncError() {

        @Serializable
        data class MissingParameter(
            val paramKey: String
        ) : ApiRequest() {
            override val errorCode: String get() = "MISSING_PARAM_VALUE"
            override val message: String get() = "Missing required parameter '$paramKey'"
            override val detail: String? get() = null
        }

        @Serializable
        data class ParamValueMalformed(
            val paramKey: String,
            val formatDescr: String = "number"
        ) : ApiRequest() {
            override val errorCode: String get() = "MALFORMED_PARAM_VALUE"
            override val message: String get() = "Parameter '$paramKey' must be a valid $formatDescr"
            override val detail: String? get() = null
        }

        @Serializable
        data object EmptyListNotAllowed : ApiRequest() {
            override val errorCode: String get() = "BODY_EMPTY_LIST"
            override val message: String get() = "Request body contained no items"
            override val detail: String? get() = null
        }

        @Serializable
        data object RequestBodyMalformed : ApiRequest() {
            override val errorCode: String get() = "MALFORMED_REQUEST_BODY"
            override val message: String get() = "Request body is missing or not readable as JSON"
            override val detail: String? get() = null
        }



    }

    @Serializable
    sealed class DatabaseError : AsyncError() {
        override val errorCode: String get() = "DATABASE_ERROR"

        @Serializable
        data object ConstraintViolation : DatabaseError() {
            override val errorCode: String get() = "DB_CONSTRAINT_VIOLATION"
            override val message: String get() = "The operation violated a database constraint."
            override val detail: String? get() = null
        }

        @Serializable
        data class Generic(override val detail: String? = null) : DatabaseError() {
            override val errorCode: String get() = "DB_GENERIC_ERROR"
            override val message: String get() = "The operation could not be completed."
        }

        @Serializable
        data object ConnectionFailure : DatabaseError() {
            override val errorCode: String get() = "DB_CONNECTION_FAILURE"
            override val message: String get() = "Could not connect to the database."
            override val detail: String? get() = null
        }

        @Serializable
        data object Timeout : DatabaseError() {
            override val errorCode: String get() = "DB_TIMEOUT"
            override val message: String get() = "The database operation timed out."
            override val detail: String? get() = null
        }

        @Serializable
        data object DeadlockDetected: DatabaseError() {
            override val errorCode: String get() = "DB_DEADLOCK"
            override val message: String get() = "A deadlock was detected and the transaction was rolled back."
            override val detail: String? get() = null
        }
    }

    @Serializable
    data class PartialRequestBodyFailure<T : BaseDto>(
        val failed: List<AsyncError>,
        val successful: List<T>
    ) : AsyncError() {
        override val errorCode: String get() = "PARTIAL_REQ_BODY_FAILURE"
        override val message: String get() = ""
        override val detail: String? get() = null
    }

    @Serializable
    data class MultipleParamFailure(
        val failed: List<AsyncError.ApiRequest>
    ) : AsyncError() {
        override val errorCode: String get() = "MULTI_PARAM_FAILURE"
        override val message: String get() = ""
        override val detail: String? get() = null
    }

}

