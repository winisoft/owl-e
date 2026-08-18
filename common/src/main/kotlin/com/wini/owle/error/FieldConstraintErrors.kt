package com.wini.owle.error

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.schema.error.ErrorDefinition
import com.wini.owle.mapper.DateTimeConverter
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.net.URL
import kotlin.reflect.KProperty1

/**
 * Errors that are raised when the validation process for some data object has failed.
 */
@Serializable
sealed interface ConstraintError: OwleError {
    abstract val isInvalid: Boolean
}



/** Fails if the value is negative. */
@ErrorDefinition("errors.constraint.positiveValueIntError")
@Serializable
data class PositiveValueIntError<T : BaseDto>(
    val constrainedField: KProperty1<T, Int>,
    val receiver: T
) : ConstraintError {
    private val value: Int get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value < 0
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: min=0, actual=$value"
}

/**
 * Fails if the value is the empty string. Null passes: on a nullable field null means "not set",
 * which is a different and legitimate state from "set to nothing".
 */
@ErrorDefinition("errors.constraint.stringNotEmptyError")
@Serializable
data class StringNotEmptyError<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.isEmpty() ?: false
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: min=0, actual=$value"
}

/** Fails if the string is blank (contains only whitespace). */
@ErrorDefinition("errors.constraint.stringNotBlankError")
@Serializable
data class StringNotBlankError<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.isBlank() ?: false
}


/**
 * Validates that all characters in the input string are from safe character ranges.
 * Intended to permit international language input, but disallow invisible or spoof-prone symbols.
 */
/**
 * Fails unless the value is a full-form CSS hex colour: `#` followed by exactly six hex digits,
 * either case. Null passes - the column is nullable and "no colour set" is a legitimate state.
 *
 * The short form (`#abc`) is deliberately rejected rather than expanded: the column is
 * `varchar(7)`, and accepting two spellings of the same colour would mean every comparison and
 * every round-trip has to normalise first.
 */
@ErrorDefinition("errors.constraint.hexColorFormat")
@Serializable
data class HexColorFormat<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {

    private val value: String? get() = constrainedField.get(receiver)

    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected=#RRGGBB, actual=$value"
    override val isInvalid: Boolean get() = value?.matches(PATTERN)?.not() == true

    companion object {
        private val PATTERN = Regex("^#[0-9a-fA-F]{6}$")
    }
}


@ErrorDefinition("errors.constraint.stringSafeCharsError")
@Serializable
data class StringContainsRestrictedChars<T : BaseDto>(
    private val constrainedField: KProperty1<T, String?>,
    private val receiver: T
) : ConstraintError {

    private val value: String? get() = constrainedField.get(receiver)

    override val isInvalid: Boolean get() = value?.any { !isAllowedChar(it) } == true

    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}"

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
}

/** Fails if the value contains anything other than digits. */
@ErrorDefinition("errors.constraint.mustContainOnlyNumerals")
@Serializable
data class MustContainOnlyNumerals<T: BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : OwleError, ConstraintError {

    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.let { s -> !s.all { it.isDigit() } } ?: false
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}=$value"

}

@ErrorDefinition("errors.constraint.resetTokenCharsError")
@Serializable
data class InvalidResetToken<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {

    private val value: String? get() = constrainedField.get(receiver)
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}=$value"
    override val isInvalid: Boolean get() = value
        ?.let { !Regex(REGEX_PATTERN).matches(it) }
        ?: false

    companion object {
        const val REGEX_PATTERN: String = "^[A-Za-z0-9_-]{32,}$"
    }

}

@ErrorDefinition("errors.constraint.invalidOAuthProvider")
@Serializable
data class InvalidOAuthProvider<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {

    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}=$value"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value != null && value !in ALLOWED_PROVIDERS

    companion object {
        val ALLOWED_PROVIDERS = setOf("google", "github", "facebook", "apple")
    }
}

@ErrorDefinition("errors.constraint.pythonVersionSegmentFormat")
@Serializable
data class PythonVersionSegmentFormat<T: BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
): ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}"
    override val isInvalid: Boolean get() = if (value == null) false else !Regex(PY_VERSION_SEG_REGEX).matches(value!!)
    private val value: String? get() = constrainedField.get(receiver)
    companion object {
        const val PY_VERSION_SEG_REGEX = "^\\d+(\\.\\d+)*$"
        const val ERROR_CODE: String = "TYPE_INVALID_PY_VERSION_FORMAT"
        const val MESSAGE: String = "must have a value that complies with Python library release versioning."
    }
}

/** Fails if the string is blank (contains only whitespace). */
@ErrorDefinition("errors.constraint.stringMustNotBeBlank")
@Serializable
data class StringMustNotBeBlank<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {
    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.isBlank() ?: false
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_STR_BLANK"
        const val MESSAGE: String = "must not be a blank string (containing only whitespace)."
    }
}

/** Fails if the result of the function exceeds the maximum allowed string length. */
@ErrorDefinition("errors.constraint.stringLengthMustBeGt")
@Serializable
data class StringLengthMustBeGt<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val minLength: Int
) : ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}.length: expected>=$minLength, actual=${value?.length}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.length?.let { it < minLength } ?: false
}


/** Fails if the string does not have exactly the required length. */
@ErrorDefinition("errors.constraint.stringLengthExactError")
@Serializable
data class StringMustHaveExactRequiredLength<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val requirement: Int
) : ConstraintError {

    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected=$requirement, actual=${value?.length}"
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value?.length?.let { it != requirement } ?: true

}

/** Fails if the result of the function exceeds the maximum allowed string length. */
@ErrorDefinition("errors.constraint.stringMaxLengthError")
@Serializable
data class StringMaxLengthError<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val maxLength: Int
) : ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected<=$maxLength, actual=${value?.length}"
    override val isInvalid: Boolean get() = value?.length?.let { it > maxLength } ?: false
    private val value: String? get() = constrainedField.get(receiver)
}

/** Fails if the result of the function exceeds the maximum allowed string length. */
@ErrorDefinition("errors.constraint.stringLengthMustBeLt")
@Serializable
data class StringLengthMustBeLt<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T,
    val maxLength: Int
) : ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected<=$maxLength, actual=${value?.length}"
    override val isInvalid: Boolean get() = value?.length?.let { it > maxLength } ?: false
    private val value: String? get() = constrainedField.get(receiver)
}


/** Fails if the value of the constrained property is less than the provided minimum. */
@ErrorDefinition("errors.constraint.intValueMustMeetMinimum")
@Serializable
data class IntValueMustMeetMinimum<T : BaseDto>(
    val constrainedField: KProperty1<T, Int>,
    val receiver: T,
    val minValue: Int
) : ConstraintError {
    private val value: Int get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value < minValue
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: min=$minValue, actual=$value"
}

/** Fails if the value is less than `minValue`. */
@ErrorDefinition("errors.constraint.longValueMustMeetMinimum")
@Serializable
data class LongValueMustMeetMinimum<T : BaseDto>(
    val constrainedField: KProperty1<T, Long>,
    val receiver: T,
    val minValue: Long
) : ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected>=$minValue, actual=$value"
    override val isInvalid: Boolean get() = value < minValue
    private val value: Long get() = constrainedField.get(receiver)
}

/** Fails if the value exceeds `maxValue`. Pairs with [LongValueMustMeetMinimum]; kept as a
 *  separate check rather than a combined range for a more specific per-violation message. */
@ErrorDefinition("errors.constraint.longValueMustNotExceedMaximum")
@Serializable
data class LongValueMustNotExceedMaximum<T : BaseDto>(
    val constrainedField: KProperty1<T, Long>,
    val receiver: T,
    val maxValue: Long
) : ConstraintError {
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected<=$maxValue, actual=$value"
    override val isInvalid: Boolean get() = value > maxValue
    private val value: Long get() = constrainedField.get(receiver)
}

/** Fails if value is outside the specified range. */
@ErrorDefinition("errors.constraint.longValueOoB")
@Serializable
data class LongValueOoB<T : BaseDto>(
    val constrainedField: KProperty1<T, Long>,
    val receiver: T,
    val minValue: Long,
    val maxValue: Long
) : ConstraintError {
    private val value: Long get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = value < minValue || value > maxValue
    override val detail: String get() =
        "${receiver.owleTypedId}::${constrainedField.name}: expected=($minValue..$maxValue), actual=$value"
}

/** Fails if value is outside the specified double range. */
@ErrorDefinition("errors.constraint.doubleValueRangeError")
@Serializable
data class DoubleValueOoB<T : BaseDto>(
    val constrainedField: KProperty1<T, Double>,
    val receiver: T,
    val minValue: Double,
    val maxValue: Double
) : ConstraintError {

    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: expected=($minValue..$maxValue), actual=$value"
    override val isInvalid: Boolean get() = value.isNaN() || value < minValue || value > maxValue
    private val value: Double get() = constrainedField.get(receiver)

}

/** Fails if list is empty. */
@ErrorDefinition("errors.constraint.listMustNotBeEmpty")
@Serializable
data class ListMustNotBeEmpty<T : BaseDto>(
    val constrainedField: KProperty1<T, List<@Contextual Any>>,
    val receiver: T
) : ConstraintError {

    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}"
    override val isInvalid: Boolean get() = constrainedField.get(receiver).isEmpty()

}

/** Fails if value is not a valid email address. */
@ErrorDefinition("errors.constraint.validEmailFormatError")
@Serializable
data class InvalidEmailFormat<T : BaseDto>(
    val constrainedField: KProperty1<T, String>,
    val receiver: T
) : ConstraintError {

    private val value: String get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = !Regex(VALID_EMAIL_REGEX).matches(value)
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}=$value"

    companion object {
        const val VALID_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        const val ERROR_CODE: String = "TYPE_INVALID_STR_EMAIL"
        const val MESSAGE: String = "must have a valid email address."
    }

}

/** Fails if value is not a well-formed URL. */
@ErrorDefinition("errors.constraint.invalidUrlFormatError")
@Serializable
data class InvalidUrlFormat<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = runCatching { URL(value).toURI() }.isFailure
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}=$value"
}

@ErrorDefinition("errors.constraint.timestampFormatError")
@Serializable
data class TimestampFormatMustMatchIso8601<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean
        get() = value?.let { !Regex(ISO_8601_UTC_REGEX).matches(it) } ?: false
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: value='$value'"

    companion object {
        const val ISO_8601_UTC_REGEX = """^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,9})?Z$"""
    }
}


@ErrorDefinition("errors.constraint.incoherentInstantError")
@Serializable
data class TimestampMustBeChronologicallyCoherent<T : BaseDto>(
    val constrainedField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {
    private val value: String? get() = constrainedField.get(receiver)
    override val isInvalid: Boolean
        get() = value?.let { DateTimeConverter.isChronologicallyCoherent(it) } ?: true
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: value='$value'"
}

/** Fails if both workflow and step are null. */
@ErrorDefinition("errors.constraint.testCaseSubject")
@Serializable
data class TestCaseSubject<T : BaseDto>(
    val workflow: KProperty1<T, Workflow?>,
    val step: KProperty1<T, Step?>,
    val receiver: T
) : ConstraintError {

    override val errorCode: String = ERROR_CODE
    override val message: String get() = MESSAGE

    private val workflowValue: Workflow? = workflow.get(receiver)
    private val stepValue: Step? = step.get(receiver)

    override val isInvalid: Boolean get() = workflowValue == null && stepValue == null
    override val detail: String get() = if (workflowValue == null) "workflow was null." else "step was null"

    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_TEST_CASE_FOCUS"
        const val MESSAGE: String = "TestCase must specify either a workflow or step subject."
    }

}

/** Fails if all version constraint values are null. */
@ErrorDefinition("errors.constraint.versionSpecUndefined")
@Serializable
data class VersionSpecUndefined<T : BaseDto>(
    val versionMinField: KProperty1<T, String?>,
    val versionMaxField: KProperty1<T, String?>,
    val versionExactField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {

    private val versionMinValue: String? get() = versionMinField.get(receiver)
    private val versionMaxValue: String? get() = versionMinField.get(receiver)
    private val versionExactValue: String? get() = versionMinField.get(receiver)
    override val detail: String get() = receiver.owleTypedId
    override val isInvalid: Boolean get() = versionMinValue == null && versionMaxValue == null && versionExactValue == null

}

/** Fails if `completedAt` exists and is before `startedAt`. */
@Serializable
@ErrorDefinition("errors.constraint.preventsTimeParadoxes")
data class PreventsTimeParadoxes<T : BaseDto>(
    val happensFirstField: KProperty1<T, String?>,
    val happensSecondField: KProperty1<T, String?>,
    val receiver: T
) : ConstraintError {

    override val detail: String get() = "${receiver.owleTypedId}::${happensFirstField.name}=$beforeTimestamp,::${happensSecondField.name}=${afterTimestamp}"

    private val beforeTimestamp: String? get() = happensFirstField.get(receiver)
    private val afterTimestamp: String? get() = happensSecondField.get(receiver)

    override val isInvalid: Boolean
        get() {
            val before = beforeTimestamp ?: return false
            val after = afterTimestamp ?: return false
            return DateTimeConverter.fromString(after) < DateTimeConverter.fromString(before)
        }

}

@Serializable
@ErrorDefinition("errors.constraint.tag.userSourceNullUserId")
data class TagWithUserSourceUserIdError<T : BaseDto>(
    val userIdField: KProperty1<T, String>,
    val sourceField: KProperty1<T, String?>,
    val receiver: Tag,
) : ConstraintError {


    override val detail: String get() = "${receiver.owleTypedId}::${sourceField.name}=${receiver.source?.name}, ${userIdField.name}=[null]"
    override val isInvalid: Boolean
        //TODO
        get() = (receiver.source?.name == TagSource.USER.name) //.userId == null)

}

@Serializable
@ErrorDefinition("errors.constraint.tag.nonuserSourceNonNullUserId")
data class TagWithNonSourceUserIdError<T : BaseDto>(
    val userIdField: KProperty1<T, String>,
    val sourceField: KProperty1<T, String?>,
    val receiver: Tag,
) : ConstraintError {
    //TODO
    override val detail: String get() = "${receiver.owleTypedId}::${sourceField.name}=${receiver.source?.name}, ${userIdField.name}=[" //${receiver.userId}]"
    override val isInvalid: Boolean
        //TODO
        get() = (receiver.source?.name != TagSource.USER.name)//.userId != null)
}

@Serializable
@ErrorDefinition("errors.constraint.inconsistentOAuthId")
data class InconsistentOAuthId<T : BaseDto>(
    val providerField: KProperty1<T, String?>,
    val idField: KProperty1<T, String?>,
    val receiver: T,
) : ConstraintError {


    override val detail: String get() = "${receiver.owleTypedId}::${providerField.name}=${providerField.name}, ${idField.name}=${idField.get(receiver)}"
    override val isInvalid: Boolean
        get() = (providerField == null && idField == null) || (idField != null && providerField == null)


}

/**
 * Retained for documentation: [ULong] cannot hold a negative value, so the invariant this names is
 * enforced by the type and this check never fires.
 *
 * It previously read `value >= 0UL`, which is *always true* — every field carrying it was
 * reported as violating on every validation.
 */
@ErrorDefinition("errors.constraint.positiveValueULongError")
@Serializable
data class PositiveValueULongError<T: BaseDto>(
    val constrainedField: KProperty1<T, ULong>,
    val receiver: T
) : ConstraintError {
    private val value: ULong get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = false
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: min=0, actual=$value"
}

/**
 * Fails if the value carries more than [maxScale] digits after the decimal point, or more than
 * [maxPrecision] significant digits overall — the two facts a DB `DECIMAL(precision, scale)`
 * column enforces natively and a Kotlin `Double` does not. Comparing against a `DECIMAL` type's
 * own semantics (not float rounding) means going through [java.math.BigDecimal].
 */
@ErrorDefinition("errors.constraint.decimalScaleExceeded")
@Serializable
data class DecimalScaleExceeded<T : BaseDto>(
    val constrainedField: KProperty1<T, Double>,
    val receiver: T,
    val maxPrecision: Int,
    val maxScale: Int
) : ConstraintError {
    private val value: Double get() = constrainedField.get(receiver)
    private val asBigDecimal: java.math.BigDecimal get() = java.math.BigDecimal.valueOf(value)
    override val isInvalid: Boolean
        get() = asBigDecimal.scale() > maxScale || asBigDecimal.precision() > maxPrecision
    override val detail: String
        get() = "${receiver.owleTypedId}::${constrainedField.name}: expected<=DECIMAL($maxPrecision,$maxScale), actual=$value"
}

/**
 * Retained for documentation: [UInt] cannot hold a negative value, so the invariant this names is
 * enforced by the type and this check never fires.
 *
 * It previously read `value >= 0U`, which is *always true* — every field carrying it was
 * reported as violating on every validation.
 */
@ErrorDefinition("errors.constraint.positiveValueUIntError")
@Serializable
data class PositiveValueUIntError<T: BaseDto>(
    val constrainedField: KProperty1<T, UInt>,
    val receiver: T
) : ConstraintError {
    private val value: UInt get() = constrainedField.get(receiver)
    override val isInvalid: Boolean get() = false
    override val detail: String get() = "${receiver.owleTypedId}::${constrainedField.name}: min=0, actual=$value"
}