package com.wini.owle.error

import com.wini.owle.schema.error.ErrorDefinition
import kotlinx.serialization.Serializable


@Serializable
sealed class DatabaseError : OwleError

@ErrorDefinition("error.db.dbTransactionError")
@Serializable
data object DbTransactionError : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = null
}

@ErrorDefinition("error.db.deadlockDetected")
@Serializable
data object DeadlockDetected: DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@ErrorDefinition("error.db.dbConstraintViolation")
@Serializable
data object DbConstraintViolation : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@ErrorDefinition("error.db.connectionFailure")
@Serializable
data object ConnectionFailure : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@ErrorDefinition("error.db.generic")
@Serializable
data class Generic(override val detail: String? = null) : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
}

@ErrorDefinition("error.db.timeout")
@Serializable
data object Timeout : DatabaseError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}