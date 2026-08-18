package com.wini.owle.error

import com.wini.owle.schema.error.ErrorDefinition
import kotlinx.serialization.Serializable


@Serializable
sealed class DatabaseError : OwleError

@ErrorDefinition("errors.db.dbTransactionError")
@Serializable
data object DbTransactionError : DatabaseError() {
    override val detail = null
}

@ErrorDefinition("errors.db.deadlockDetected")
@Serializable
data object DeadlockDetected: DatabaseError() {
    override val detail: String? get() = null
}

@ErrorDefinition("errors.db.dbConstraintViolation")
@Serializable
data object DbConstraintViolation : DatabaseError() {
    override val detail: String? get() = null
}

@ErrorDefinition("errors.db.connectionFailure")
@Serializable
data object ConnectionFailure : DatabaseError() {
    override val detail: String? get() = null
}

@ErrorDefinition("errors.db.generic")
@Serializable
data class GenericError(override val detail: String? = null) : DatabaseError() {
}

@ErrorDefinition("errors.db.timeout")
@Serializable
data object Timeout : DatabaseError() {
    override val detail: String? get() = null
}