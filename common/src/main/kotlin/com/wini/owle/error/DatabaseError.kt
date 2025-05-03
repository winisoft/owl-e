package com.wini.owle.error

import kotlinx.serialization.Serializable


@Serializable
sealed class DatabaseError : OwleError {

    @Serializable
    data object DbTransactionError : OwleError {
        override val errorCode = "DB_TRANSACTION_ERROR"
        override val message = "Database transaction error."
        override val detail = null
    }

    @Serializable
    data object DbConstraintViolation : DatabaseError() {
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