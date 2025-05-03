package com.wini.owle.content

object MustHaveOneSpecialChar {
    const val ERROR_CODE: String = "TYPE_INVALID_PW_1SCH"
    const val MESSAGE: String = "must have at least one special character."
}

object MustHaveOneNumeral {
    const val ERROR_CODE: String = "TYPE_INVALID_PW_1NUM"
    const val MESSAGE: String = "must have at least one numeric character."
}

object MustHaveOneCapitalLetter  {
    const val ERROR_CODE: String =  "TYPE_INVALID_PW_1UC"
    const val MESSAGE: String = "must have at least one capital letter."
}

object MustHaveOneLowercaseLetter {
    const val ERROR_CODE: String = "TYPE_INVALID_PW_1LC"
    const val MESSAGE: String = "must have at least one capital letter."
}

object StringRestrictedChars {
    const val ERROR_CODE: String = "TYPE_INVALID_RESTRICTED_CHARS"
    const val MESSAGE: String = "must not contain restricted characters such as those that are invisible, prone to spoofing, or unsafe to display."
}

object NumericOnly {
    const val ERROR_CODE: String = "TYPE_INVALID_NUMERIC_ONLY"
    const val MESSAGE: String = "must only contain numeric values."
}

object InvalidResetTokenFormat {
    const val ERROR_CODE: String = "TYPE_INVALID_RESET_TOKEN"
    const val MESSAGE: String = "must hold a URL-safe string of sufficient length for use as a reset token."
}

object InvalidOAuthProviderEnum {
    const val ERROR_CODE: String = "TYPE_INVALID_OAUTH_PROVIDER"
    const val MESSAGE: String = "must contain only one of the legal values representing an OAuth provider."
}

object PythonVersionSegFormat {
    const val ERROR_CODE: String = "TYPE_INVALID_PY_VERSION_FORMAT"
    const val MESSAGE: String = "must have a value that complies with Python library release versioning."
}

/** Fails if the string is blank (contains only whitespace). */
object StringMustNotBeWhitespace {
    const val ERROR_CODE: String = "TYPE_INVALID_STR_BLANK"
    const val MESSAGE: String = "must not be a blank string (containing only whitespace)."
}

/** Fails if the result of the function exceeds the maximum allowed string length. */
object StringLengthMustBeGrTh {
    //object minLength: Int
    const val ERROR_CODE: String = "TYPE_INVALID_STR_LEN_BELOW_MIN"
    const val MESSAGE: String = "must have length longer than the minimum required count of characters."
}

/** Fails if the result of the function exceeds the maximum allowed string length. */
object StringLengthMustBeLsTh {
    //object maxLength: Int
    const val ERROR_CODE: String = "TYPE_INVALID_STR_LEN_ABOVE_MAX"
    const val MESSAGE: String = "must length less than the maximum permitted count of characters."

}

/** Fails if the value of the constrained property is less than the provided minimum. */
object IntValueMustMeetMin {
    //object minValue: Int
    const val ERROR_CODE: String = "TYPE_INVALID_INT_BELOW_MIN"
    const val MESSAGE: String = "must have a value greater than or equal to the minimum permitted value"
}

/** Fails if the string does not have exactly the required length. */
object StringMustHaveRequiredLenExact {
    //object requirement: Int
    const val ERROR_CODE: String = "TYPE_INVALID_STR_LEN_EXACT"
    const val MESSAGE: String = "must have a length equal to the required number of characters."

}

/** Fails if the value is less than `minValue`. */
object LongValueMustMeetMin {
    //object minValue: Long
    const val ERROR_CODE: String = "TYPE_INVALID_LONG_BELOW_MIN"
    const val MESSAGE: String = "must have a value greater than or equal to the minimum required value."

}

/** Fails if both workflow and step are null. */
object TestCaseSubject {
    const val ERROR_CODE: String = "TYPE_INVALID_TEST_CASE_FOCUS"
    const val MESSAGE: String = "{TestCase::class.simpleName} must specify either a workflow or step subject."
}

/** Fails if value is outside the specified double range. */
object DoubleValueOutsideValidRange {
    const val ERROR_CODE: String = "TYPE_INVALID_DOUBLE_OUTSIDE_RANGE"
    const val MESSAGE: String = "must have a value that falls between the minimum and maximum permissible values."
//    object minValue: Double,
//    object maxValue: Double
}

/** Fails if list is empty. */
object ListCannotBeEmpty {
    const val ERROR_CODE: String = "TYPE_INVALID_LIST_EMPTY"
    const val MESSAGE: String = "Must not be an empty list."
}

/** Fails if value is not a valid email address. */
object InvalidEmailFormat {
    const val ERROR_CODE: String = "TYPE_INVALID_STR_EMAIL"
    const val MESSAGE: String = "must have a valid email address."
}

/** Fails if value is not a well-formed URL. */
object InvalidUrl {
    const val ERROR_CODE: String = "TYPE_INVALID_STR_URL"
    const val MESSAGE: String = "must hold a valid URL."
}

/** Fails if all version constraint values are null. */
object VersionSpecUndefined {
    const val ERROR_CODE: String = "TYPE_INVALID_VERSION_SPEC_UNDEFINED"
    const val MESSAGE: String = "At least one version constraint must be specified."
    //    object versionMinField: KProperty1<T, String?>,
    //    object versionMaxField: KProperty1<T, String?>,
    //    object versionExactField: KProperty1<T, String?>,
}

/** Fails if `completedAt` exists and is before `startedAt`. */
object CompletedTimeBeforeStart {
    const val ERROR_CODE: String = "TYPE_INVALID_TIME_COMPLETED_BEFORE_START"
    const val MESSAGE: String = "The timestamp of a process's completion cannot occur before the process was started."
    //    object startTimestampField: KProperty1<T, String>,
    //    object completedTimestampField: KProperty1<T, String?>,
}

object TimestampFormatMustMatchIso8601 {
    const val ERROR_CODE: String = "TYPE_INVALID_TIMESTAMP_FORMAT"
    const val MESSAGE: String = "must comply with the ISO 8601 specification format (UTC, with Z)."
}

object TimestampMustBeChronologicallyCoherent {
    const val ERROR_CODE: String = "TYPE_INVALID_TIMESTAMP_VALUE"
    const val MESSAGE: String = "must represent a real point in time."
}

/** Fails if `updatedAt` is before `createdAt`. */
object UpdatedBeforeCreatedTimestamp {
    //    object createdAt: KProperty1<T, String>,
//    object updatedAt: KProperty1<T, String>,
    const val ERROR_CODE: String = "TYPE_INVALID_TIME_UPDATE_BEFORE_CREATE"
    const val MESSAGE: String = "The timestamp representing the most recent update cannot occur before the timestamp of the data member's creation."
}

object InconsistentOAuthId {
    //    object providerField: KProperty1<T, String?>,
//    object idField: KProperty1<T, String?>,
    const val ERROR_CODE: String = "TYPE_INVALID_OAUTH_ID_COMBINATION"
    const val MESSAGE: String = "OAuth ID and provider must be set together."
}