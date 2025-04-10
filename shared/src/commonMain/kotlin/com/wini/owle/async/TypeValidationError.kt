package com.wini.owle.async

import com.wini.owle.async.AsyncError.ApiRequestError
import kotlinx.serialization.Serializable


@Serializable
sealed class TypeValidation : ApiRequestError() {

    @Serializable
    data class InvalidPythonVersion(
        val idString: String,
        val fieldName: String,
        val conditionDesc: String,
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_PY_VERSION"
        override val message: String get() = "The provided value is not a supported Python version."
        override val detail: String? get() = null
    }

    @Serializable
    data class StringEmpty(
        val idString: String,
        val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_EMPTY"
        override val message: String get() = "Must not be an empty string."
        override val detail: String? get() = null
    }

    @Serializable
    data class StringBlank(
        val idString: String,
        val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_BLANK"
        override val message: String get() = "Must not hold a blank string."
        override val detail: String? get() = null
    }

    @Serializable
    data class MinLength(
        val idString: String,
        val fieldName: String,
        val min: Int
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MIN_LEN"
        override val message: String get() = "Must not be fewer than $min characters in length."
        override val detail: String? get() = null
    }

    @Serializable
    data class MaxLength(
        val idString: String,
        val fieldName: String,
        val max: Int
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MAX_LEN"
        override val message: String get() = "Must not exceed $max characters in length."
        override val detail: String? get() = null
    }

    @Serializable
    data class LongRange(
        val idString: String,
        val fieldName: String,
        val min: Long,
        val max: Long
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LONG_RANGE"
        override val message: String get() = "Must not have a value greater than $max or less than $min."
        override val detail: String? get() = null
    }

    @Serializable
    data class DoubleRange(
        val idString: String,
        val fieldName: String,
        val min: Double,
        val max: Double
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_DOUBLE_RANGE"
        override val message: String get() = "Must not have a value greater than $max or less than $min."
        override val detail: String? get() = null
    }

    @Serializable
    data class ListNotEmpty(
        val idString: String,
        val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LIST_EMPTY"
        override val message: String get() = "Must not be an empty list."
        override val detail: String? get() = null
    }
}