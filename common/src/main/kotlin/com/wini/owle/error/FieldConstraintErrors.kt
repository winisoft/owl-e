package com.wini.owle.error

import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.DateTimeConverter
import kotlinx.serialization.Serializable
import java.net.URL
import kotlin.reflect.KProperty1


interface Constraint {
    val isInvalid: Boolean
}

@Serializable
sealed class FieldConstraintError: OwleError, Constraint {
    abstract override val errorCode: String
    abstract override val message: String
    abstract override val detail: String?
}


/**
 * Validates that all characters in the input string are from safe character ranges.
 * Intended to permit international language input, but disallow invisible or spoof-prone symbols.
 */
@Serializable
data class StringContainsRestrictedChars<T : BaseDto>(
    private val constrainedField: KProperty1<T, String?>,
    private val receiver: T
) : FieldConstraintError() {

    private val value: String? get() = constrainedField.get(receiver)

    override val isInvalid: Boolean get() = value?.any { !isAllowedChar(it) } == true

    override val errorCode: String get() = ERROR_CODE

    override val message: String get() = MESSAGE

    override val detail: String get() = "${receiver.idText}::${constrainedField.name}"

    private fun isAllowedChar(c: Char): Boolean {
        return c.isLetterOrDigit() || c.isWhitespace() || isPunctuation(c) || isEmoji(c)
    }

    private fun isPunctuation(c: Char): Boolean {
        return c in '!'..'/' || c in ':'..'@' || c in '['..'`' || c in '{'..'~'
    }

    private fun isEmoji(c: Char): Boolean {
        val type = Character.getType(c)
        return type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()
    }

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_RESTRICTED_CHARS"
        const val MESSAGE: String = "must not contain restricted characters such as those that are invisible, prone to spoofing, or unsafe to display."
    }
}

@Serializable
data class MustContainOnlyNumerals<T: BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : OwleError, FieldConstraintError() {

    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.let { it.all { ch -> ch.isDigit() } } ?: false
    override val errorCode: String = ERROR_CODE
    override val message: String get() =MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_NUMERIC_ONLY"
        const val MESSAGE: String = "must only contain numeric values."
    }

}

@Serializable
data class InvalidResetToken<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : FieldConstraintError() {

    private val value: String? get() = constrainedField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"
    override val isInvalid: Boolean get() = value
        ?.let { !Regex(REGEX_PATTERN).matches(it) }
        ?: false

    companion object {
        const val REGEX_PATTERN: String = "^[A-Za-z0-9_-]{32,}$"
        const val ERROR_CODE: String = "TYPE_INVALID_RESET_TOKEN"
        const val MESSAGE: String = "must hold a URL-safe string of sufficient length for use as a reset token."
    }

}

@Serializable
data class InvalidOAuthProvider<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : FieldConstraintError() {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value != null && value !in ALLOWED_PROVIDERS

    companion object {
        val ALLOWED_PROVIDERS = setOf("google", "github", "facebook", "apple")
        const val ERROR_CODE: String = "TYPE_INVALID_OAUTH_PROVIDER"
        const val MESSAGE: String = "must contain only one of the legal values representing an OAuth provider."
    }
}

@Serializable
data class PythonVersionSegmentFormat<T: BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
): FieldConstraintError() {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}"
    override val isInvalid: Boolean get() = if (value == null) false else !Regex(PY_VERSION_SEG_REGEX).matches(value!!)
    private val value: String? get() = constrainedField.get(receiver)
    companion object {
        const val PY_VERSION_SEG_REGEX = "^\\d+(\\.\\d+)*$"
        const val ERROR_CODE: String = "TYPE_INVALID_PY_VERSION_FORMAT"
        const val MESSAGE: String = "must have a value that complies with Python library release versioning."
    }
}

/** Fails if the string is blank (contains only whitespace). */
@Serializable
data class StringMustNotBeBlank<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : FieldConstraintError() {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.isBlank() ?: false
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_STR_BLANK"
        const val MESSAGE: String = "must not be a blank string (containing only whitespace)."
    }
}

/** Fails if the result of the function exceeds the maximum allowed string length. */
@Serializable
data class StringLengthMustBeGt<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val minLength: Int
) : FieldConstraintError() {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}.length: expected>=$minLength, actual=${value?.length}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.length?.let { it < minLength } ?: false
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_STR_LEN_BELOW_MIN"
        const val MESSAGE: String = "must have length longer than the minimum required count of characters."
    }
}


/** Fails if the string does not have exactly the required length. */
@Serializable
data class StringMustHaveExactRequiredLength<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val requirement: Int
) : FieldConstraintError() {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected=$requirement, actual=${value?.length}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.length?.let { it != requirement } ?: true

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_STR_LEN_EXACT"
        const val MESSAGE: String = "must have a length equal to the required number of characters."
    }
}

/** Fails if the result of the function exceeds the maximum allowed string length. */
@Serializable
data class StringLengthMustBeLt<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val maxLength: Int
) : FieldConstraintError() {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected<=$maxLength, actual=${value?.length}"
    override val isInvalid: Boolean get() = value?.length?.let { it > maxLength } ?: false
    private val value: String? get() = constrainedField.get(receiver)
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_STR_LEN_ABOVE_MAX"
        const val MESSAGE: String = "must length less than the maximum permitted count of characters."
    }
}

/** Fails if the value of the constrained property is less than the provided minimum. */
@Serializable
data class IntValueMustMeetMinimum<T : BaseDto>(
    val constrainedField: KProperty1<T, Int>,
    val receiver: T,
    val minValue: Int
) : FieldConstraintError() {
    private val value: Int get() = constrainedField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val isInvalid: Boolean get() = value < minValue
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: min=$minValue, actual=$value"

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_INT_BELOW_MIN"
        const val MESSAGE: String = "must have a value greater than or equal to the minimum permitted value"
    }
}

/** Fails if the value is less than `minValue`. */
@Serializable
data class LongValueMustMeetMinimum<T : BaseDto>(
    val constrainedField: KProperty1<T, Long>,
    val receiver: T,
    val minValue: Long
) : FieldConstraintError() {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected>=$minValue, actual=$value"
    override val isInvalid: Boolean get() = value < minValue
    private val value: Long get() = constrainedField.get(receiver)

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_LONG_BELOW_MIN"
        const val MESSAGE: String = "must have a value greater than or equal to the minimum required value."
    }
}

/** Fails if value is outside the specified range. */
@Serializable
data class LongValueOoB<T : BaseDto>(
    val constrainedField: KProperty1<T, Long>,
    val receiver: T,
    val minValue: Long,
    val maxValue: Long
) : FieldConstraintError() {
    private val value: Long get() = constrainedField.get(receiver)
    override val errorCode: String = "TYPE_INVALID_LONG_OUTSIDE_RANGE"
    override val isInvalid: Boolean get() = value < minValue || value >= maxValue
    override val message: String get() = "Must be within $minValue (inclusive) and $maxValue (exclusive)."
    override val detail: String get() =
        "${receiver.idText}::${constrainedField.name}: expected=($minValue..$maxValue), actual=$value"
}

/** Fails if value is outside the specified double range. */
@Serializable
data class DoubleValueOoB<T : BaseDto>(
    val constrainedField: KProperty1<T, Double>,
    val receiver: T,
    val minValue: Double,
    val maxValue: Double
) : FieldConstraintError() {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected=($minValue..$maxValue), actual=$value"
    override val isInvalid: Boolean get() = value < minValue || value >= maxValue
    private val value: Double get() = constrainedField.get(receiver)

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_DOUBLE_OUTSIDE_RANGE"
        const val MESSAGE: String = "must have a value that falls between the minimum and maximum permissible values."
    }
}

/** Fails if list is empty. */
@Serializable
data class ListMustNotBeEmpty<T : BaseDto>(
    val constrainedField: KProperty1<T, List<*>>,
    val receiver: T
) : FieldConstraintError() {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}"
    override val isInvalid: Boolean get() = constrainedField.get(receiver).isEmpty()

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_LIST_EMPTY"
        const val MESSAGE: String = "Must not be an empty list."
    }
}

/** Fails if value is not a valid email address. */
@Serializable
data class InvalidEmailFormat<T : BaseDto>(
    val constrainedField: KProperty1<T, String>,
    val receiver: T
) : FieldConstraintError() {

    private val value: String get() = constrainedField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val isInvalid: Boolean get() = !Regex(VALID_EMAIL_REGEX).matches(value)
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"

    companion object {
        const val VALID_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        const val ERROR_CODE: String = "TYPE_INVALID_STR_EMAIL"
        const val MESSAGE: String = "must have a valid email address."
    }

}

/** Fails if value is not a well-formed URL. */
@Serializable
data class InvalidUrlFormat<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : FieldConstraintError() {
    private val value: String? get() = constrainedField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val isInvalid: Boolean get() = runCatching { URL(value).toURI() }.isFailure
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"

    companion object {
        const val ERROR_CODE = "TYPE_INVALID_STR_URL"
        const val MESSAGE = "Must be a valid URL."
    }
}

@Serializable
data class TimestampFormatMustMatchIso8601<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : FieldConstraintError() {
    private val value: String? get() = constrainedField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val isInvalid: Boolean
        get() = value?.let { !Regex(ISO_8601_UTC_REGEX).matches(it) } ?: false
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: value='$value'"

    companion object {
        const val ISO_8601_UTC_REGEX = """^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,9})?Z$"""
        const val ERROR_CODE: String = "TYPE_INVALID_TIMESTAMP_FORMAT"
        const val MESSAGE: String = "must comply with the ISO 8601 specification format (UTC, with Z)."
    }
}


@Serializable
data class TimestampMustBeChronologicallyCoherent<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : FieldConstraintError() {
    private val value: String? get() = constrainedField.get(receiver)
    override val errorCode: String = ERROR_CODE
    override val isInvalid: Boolean
        get() = value?.let { DateTimeConverter.isChronologicallyCoherent(it) } ?: true
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.idText}::${constrainedField.name}: value='$value'"

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_TIMESTAMP_VALUE"
        const val MESSAGE: String = "must represent a real point in time."
    }

}



