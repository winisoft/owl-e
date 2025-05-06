package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data object EmptyListNotAllowed : OwleError {
    override val errorCode: String get() = "BODY_EMPTY_LIST"
    override val message: String get() = "Request body contained no items"
    override val detail: String? get() = null
}