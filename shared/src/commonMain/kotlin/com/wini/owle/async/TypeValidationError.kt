package com.wini.owle.async

import com.wini.owle.async.AsyncError.ApiRequest
import kotlinx.serialization.Serializable

@Serializable
sealed class TypeValidation : ApiRequest() {

    @Serializable
    data class LogicError(
        val fieldName: String,
        override val detail: String?
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_EMPTY"
        override val message: String get() = "'$fieldName' violates a unique logical constraint."
    }

    @Serializable
    data class StringEmpty(val fieldName: String): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_EMPTY"
        override val message: String get() = "'$fieldName' must not be an empty string."
        override val detail: String? get() = null
    }

    @Serializable
    data class StringBlank(val fieldName: String): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_BLANK"
        override val message: String get() = "'$fieldName' must not hold an empty string."
        override val detail: String? get() = null
    }

    @Serializable
    data class MinLength(val fieldName: String, val min: Int): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MIN_LEN"
        override val message: String get() = "'$fieldName' cannot be fewer than $min characters in length."
        override val detail: String? get() = null
    }

    @Serializable
    data class MaxLength(val fieldName: String, val max: Int): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MAX_LEN"
        override val message: String get() = "'$fieldName' cannot exceed $max characters in length."
        override val detail: String? get() = null
    }

    @Serializable
    data class LongRange(
        val fieldName: String,
        val min: Long,
        val max: Long
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LONG_RANGE"
        override val message: String get() = "'$fieldName' cannot have a value greater than $max or less than $min."
        override val detail: String? get() = null
    }

    @Serializable
    data class DoubleRange(
        val fieldName: String,
        val min: Double,
        val max: Double
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_DOUBLE_RANGE"
        override val message: String get() = "'$fieldName' cannot have a value greater than $max or less than $min."
        override val detail: String? get() = null
    }

    @Serializable
    data class ListNotEmpty(
        val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LIST_EMPTY"
        override val message: String get() = "'$fieldName' cannot be an empty list."
        override val detail: String? get() = null
    }
}