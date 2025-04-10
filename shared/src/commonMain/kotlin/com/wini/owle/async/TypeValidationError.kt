package com.wini.owle.async

import com.wini.owle.async.AsyncError.ApiRequestError
import kotlinx.serialization.Serializable


@Serializable
sealed class TypeValidation : ApiRequestError() {
    
    abstract val idString: String
    abstract val typeName: String
    abstract val fieldName: String

    @Serializable
    data class InvalidPythonVersion(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val conditionDesc: String,
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_PY_VERSION"
        override val message: String get() = "The provided value is not a supported Python version."
        override val detail: String get() = "Id: [${idString}] Field: [$fieldName]"
    }

    @Serializable
    data class StringEmpty(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_EMPTY"
        override val message: String get() = "Must not be an empty string."
        override val detail: String get() = "Id: [${idString}] Field: [$fieldName]"
    }
    
    @Serializable
    data class StringBlank(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_BLANK"
        override val message: String get() = "Must not hold a blank string."
        override val detail: String? get() = "Id: [${idString}] Field: [$fieldName]"
    }

    @Serializable
    data class MinLength(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val min: Int
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MIN_LEN"
        override val message: String get() = "Length did not meet the minimum number of characters."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Min: [$min]"
    }

    @Serializable
    data class MaxLength(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val max: Int
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MAX_LEN"
        override val message: String get() = "Length exceeded the maximum number of characters."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Max: [$max]"
    }

    @Serializable
    data class StringLengthExact(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val requirement: Int
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_STR_MAX_LEN"
        override val message: String get() = "Must not be less than or greater than the required length."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Required Length: [$requirement]"
    }

    @Serializable
    data class LongMin(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val min: Long
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LONG_MIN"
        override val message: String get() = "Must have a whole number value greater than the minimum legal value."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Min: [$min]"
    }

    @Serializable
    data class LongRange(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val min: Long,
        val max: Long
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LONG_RANGE"
        override val message: String get() = "Must have a whole number value within upper and lower bounds."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Min: [$min] Max: [$max]"
    }

    @Serializable
    data class PartGreaterThanTotal(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val total: Int
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_PART_GT_TOTAL"
        override val message: String get() = "Must have a value lower than the total."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Total: [$total]"
    }

    @Serializable
    data class TestCaseSubject(
        override val typeName: String,
        override val idString: String
    ): TypeValidation() {
        override val errorCode: String = "TYPE_INVALID_PART_GT_TOTAL"
        override val message: String = "Either workflow or step must be specified"
        override val detail: String = "Id: [${idString}]"
        override val fieldName: String = ""
    }

    @Serializable
    data class DoubleRange(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val min: Double,
        val max: Double
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_DOUBLE_RANGE"
        override val message: String get() = "Must have a floating point value within upper and lower bounds."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Min: [$min] Max: [$max]"
    }
   
    @Serializable
    data class ListNotEmpty(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_LIST_EMPTY"
        override val message: String get() = "Must not be an empty list."
        override val detail: String get() = "Id: [${idString}] Field: [$fieldName]"
    }
    
    @Serializable 
    data class InvalidEmail(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_EMAIL"
        override val message: String get() = "Must be a valid email address."
        override val detail: String get() = "Id: [${idString}] Field: [$fieldName]"
    }

    @Serializable
    data class InvalidUrl(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_URL"
        override val message: String get() = "Must be a valid URL."
        override val detail: String get() = "Id: [${idString}] Field: [$fieldName]"
    }

    @Serializable
    data class VersionSpecUndefined(
        override val typeName: String,
        override val idString: String
    ): TypeValidation() {
        override val errorCode: String = "TYPE_INVALID_VERSION_SPEC"
        override val message: String = "At least one version constraint must be specified"
        override val detail: String? = "Id: [${idString}]"
        override val fieldName: String = ""
    }

    @Serializable
    data class UpdatedBeforeCreatedTimestamp(
        override val typeName: String,
        override val idString: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_CREATE_UPDATE_TIME"
        override val message: String get() = "Timestamp values with update before creation."
        override val detail: String get() = "Id: [${idString}]"
        override val fieldName: String = ""
    }

    @Serializable
    data class StartedAndCompletedTimestamp(
        override val typeName: String,
        override val idString: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_START_COMPLETE_TIME"
        override val message: String get() = "Timestamp values with end before start."
        override val detail: String get() = "Id: [${idString}]"
        override val fieldName: String = ""
    }

    @Serializable
    data class InvalidVersion(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val format: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_VERSION"
        override val message: String get() = "The value provided must follow a valid format."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Format:[$format]"
    }
    
    @Serializable
    data class RequiredField(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_REQUIRED_FIELD"
        override val message: String get() = "Field is required and must not be null."
        override val detail: String? get() = "Id: [${idString}] Field: [$fieldName]"
    }

    @Serializable
    data class InvalidEnum(
        override val typeName: String,
        override val idString: String,
        override val fieldName: String,
        val allowedValues: List<String>
    ): TypeValidation() {
        override val errorCode: String get() = "TYPE_INVALID_ENUM"
        override val message: String get() = "Must match one of a restricted set of values."
        override val detail: String get() = "Id: [$idString] Field: [$fieldName] Values:[${allowedValues.joinToString(", ")}]"
    }

    @Serializable
    data class BenchmarkingSteps(
        override val typeName: String,
        override val idString: String,
    ): TypeValidation() {
        override val errorCode: String = "TYPE_VALIDATION_BENCHMARKING_STEPS"
        override val fieldName: String = "totalSteps, predeterminedSteps, aiDeterminedSteps"
        override val message: String = "Sum of AI-determined and predetermined steps must equal total steps"
        override val detail: String? = null
    }
}