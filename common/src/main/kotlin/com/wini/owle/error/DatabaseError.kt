package com.wini.owle.error

import com.wini.owle.schema.error.OwleDocsError
import kotlinx.serialization.Serializable


@Serializable
sealed class DatabaseError : OwleError

@OwleDocsError("error.db.dbTransactionError")
@Serializable
data object DbTransactionError : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = null
}

@OwleDocsError("error.db.deadlockDetected")
@Serializable
data object DeadlockDetected: DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("error.db.dbConstraintViolation")
@Serializable
data object DbConstraintViolation : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("error.db.connectionFailure")
@Serializable
data object ConnectionFailure : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("error.db.generic")
@Serializable
data class Generic(override val detail: String? = null) : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
}

@OwleDocsError("error.db.timeout")
@Serializable
data object Timeout : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}