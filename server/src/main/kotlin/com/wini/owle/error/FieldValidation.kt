package com.wini.owle.error

import com.wini.owle.content.DoubleValueOutsideValidRange
import com.wini.owle.content.IntValueMustMeetMin
import com.wini.owle.content.InvalidEmailFormat
import com.wini.owle.content.InvalidOAuthProviderEnum
import com.wini.owle.content.InvalidResetTokenFormat
import com.wini.owle.content.ListCannotBeEmpty
import com.wini.owle.content.LongValueMustMeetMin
import com.wini.owle.content.NumericOnly
import com.wini.owle.content.PythonVersionSegFormat
import com.wini.owle.content.StringLengthMustBeGrTh
import com.wini.owle.content.StringLengthMustBeLsTh
import com.wini.owle.content.StringMustHaveRequiredLenExact
import com.wini.owle.content.StringMustNotBeWhitespace
import com.wini.owle.content.StringRestrictedChars
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.mapper.DateTimeConverter
import kotlinx.serialization.Serializable
import java.net.URL
import kotlin.reflect.KProperty1

abstract class CompositeValidation : OwleError, FieldConstraintError() {

    protected abstract val delegateValidations: List<FieldConstraintError>
    protected val firstFailure: FieldConstraintError? by lazy {
        delegateValidations.firstOrNull { it.isInvalid }
    }

    override val isInvalid: Boolean
        get() = firstFailure != null

    override val errorCode: String
        get() = firstFailure?.errorCode ?: throw IllegalStateException("Accessing errorCode but isInvalid == false")

    override val message: String
        get() = firstFailure?.message ?: throw IllegalStateException("Accessing message but isInvalid == false")

    override val detail: String
        get() = firstFailure?.detail ?: throw IllegalStateException("Accessing detail but isInvalid == false")

}

@Serializable
sealed class FieldValidation: OwleError {

    abstract override val errorCode: String
    abstract override val message: String
    abstract override val detail: String?
    abstract val isInvalid: Boolean

    sealed class SingleFieldValidation: FieldConstraintError()

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

        override val isInvalid: Boolean
            get() = value?.any { !isAllowedChar(it) } == true

        override val errorCode: String get() = StringRestrictedChars.ERROR_CODE

        override val message: String get() = StringRestrictedChars.MESSAGE

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
    }

    @Serializable
    data class NotNumeric<T: BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : OwleError, FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val isInvalid: Boolean get() = value?.let { it.all { ch -> ch.isDigit() } } ?: false
        override val errorCode: String = NumericOnly.ERROR_CODE
        override val message: String get() = NumericOnly.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"
    }

    @Serializable
    data class InvalidResetToken<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = InvalidResetTokenFormat.ERROR_CODE
        override val isInvalid: Boolean get() = value
            ?.let { !Regex(REGEX_PATTERN).matches(it) }
            ?: false
        override val message: String get() = "Reset token must be a URL-safe string of sufficient length."
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"

        companion object {
            const val REGEX_PATTERN: String = "^[A-Za-z0-9_-]{32,}$"
        }
    }

    @Serializable
    data class InvalidOAuthProvider<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = InvalidOAuthProviderEnum.ERROR_CODE
        override val message: String get() = InvalidOAuthProviderEnum.MESSAGE
        override val isInvalid: Boolean get() = value != null && value !in ALLOWED_PROVIDERS
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"
        companion object {
            val ALLOWED_PROVIDERS = setOf("google", "github", "facebook", "apple")
        }
    }

    @Serializable
    data class PythonVersionSegmentFormat<T: BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ): FieldConstraintError() {
        override val isInvalid: Boolean get() = if (value == null) false else !Regex(PY_VERSION_SEG_REGEX).matches(value!!)
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = PythonVersionSegFormat.ERROR_CODE
        override val message: String get() = PythonVersionSegFormat.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}"

        companion object {
            const val PY_VERSION_SEG_REGEX = "^\\d+(\\.\\d+)*$"
        }
    }

    /** Fails if the string is blank (contains only whitespace). */
    @Serializable
    data class StringMustNotBeBlank<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = StringMustNotBeWhitespace.ERROR_CODE
        override val isInvalid: Boolean get() = value?.isBlank() ?: false
        override val message: String get() = StringMustNotBeWhitespace.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}"
    }

    /** Fails if the result of the function exceeds the maximum allowed string length. */
    @Serializable
    data class StringLengthMustBeGt<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T,
        val minLength: Int
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = StringLengthMustBeGrTh.ERROR_CODE
        override val isInvalid: Boolean get() = value?.length?.let { it < minLength } ?: false
        override val message: String get() = StringLengthMustBeGrTh.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}.length: expected>=$minLength, actual=${value?.length}"
    }

    /** Fails if the result of the function exceeds the maximum allowed string length. */
    @Serializable
    data class StringLengthMustBeLt<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T,
        val maxLength: Int
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = StringLengthMustBeLsTh.ERROR_CODE
        override val isInvalid: Boolean get() = value?.length?.let { it > maxLength } ?: false
        override val message: String get() = StringLengthMustBeLsTh.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected<=$maxLength, actual=${value?.length}"
    }

    /** Fails if the value of the constrained property is less than the provided minimum. */
    @Serializable
    data class IntValueMustMeetMinimum<T : BaseDto>(
        val constrainedField: KProperty1<T, Int>,
        val receiver: T,
        val minValue: Int
    ) : FieldConstraintError() {
        private val value: Int get() = constrainedField.get(receiver)
        override val errorCode: String = IntValueMustMeetMin.ERROR_CODE
        override val isInvalid: Boolean get() = value < minValue
        override val message: String get() = IntValueMustMeetMin.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: min=$minValue, actual=$value"
    }

    /** Fails if the string does not have exactly the required length. */
    @Serializable
    data class StringMustHaveExactRequiredLength<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T,
        val requirement: Int
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = StringMustHaveRequiredLenExact.ERROR_CODE
        override val isInvalid: Boolean get() = value?.length?.let { it != requirement } ?: true
        override val message: String get() = StringMustHaveRequiredLenExact.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected=$requirement, actual=${value?.length}"
    }

    /** Fails if the value is less than `minValue`. */
    @Serializable
    data class LongValueMustMeetMinimum<T : BaseDto>(
        val constrainedField: KProperty1<T, Long>,
        val receiver: T,
        val minValue: Long
    ) : FieldConstraintError() {
        private val value: Long get() = constrainedField.get(receiver)
        override val errorCode: String = LongValueMustMeetMin.ERROR_CODE
        override val isInvalid: Boolean get() = value < minValue
        override val message: String get() = LongValueMustMeetMin.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected>=$minValue, actual=$value"
    }

    /** Fails if the value is negative. */
    @Serializable
    data class LongValueMustNotBeNegative<T : BaseDto>(
        val constrainedField: KProperty1<T, Long>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: Long get() = constrainedField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_LONG_NEGATIVE"
        override val isInvalid: Boolean get() = value < 0
        override val message: String get() = "Value must not be negative."
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}"
    }

    /** Fails if value is outside the specified range. */
    @Serializable
    data class LongValueNotWithinRange<T : BaseDto>(
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
    data class DoubleRange<T : BaseDto>(
        val constrainedField: KProperty1<T, Double>,
        val receiver: T,
        val minValue: Double,
        val maxValue: Double
    ) : FieldConstraintError() {
        private val value: Double get() = constrainedField.get(receiver)
        override val errorCode: String = DoubleValueOutsideValidRange.ERROR_CODE
        override val isInvalid: Boolean get() = value < minValue || value >= maxValue
        override val message: String get() = DoubleValueOutsideValidRange.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: expected=($minValue..$maxValue), actual=$value"
    }

    /** Fails if list is empty. */
    @Serializable
    data class ListNotEmpty<T : BaseDto>(
        val constrainedField: KProperty1<T, List<*>>,
        val receiver: T
    ) : FieldConstraintError() {
        override val errorCode: String = ListCannotBeEmpty.ERROR_CODE
        override val isInvalid: Boolean get() = constrainedField.get(receiver).isEmpty()
        override val message: String get() = ListCannotBeEmpty.MESSAGE// "Must not be an empty list."
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}"
    }

    /** Fails if value is not a valid email address. */
    @Serializable
    data class InvalidEmail<T : BaseDto>(
        val constrainedField: KProperty1<T, String>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String get() = constrainedField.get(receiver)
        override val errorCode: String = InvalidEmailFormat.ERROR_CODE
        override val isInvalid: Boolean get() =
            !Regex(VALID_EMAIL_REGEX).matches(value)
        override val message: String get() = InvalidEmailFormat.MESSAGE
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"

        companion object {
            const val VALID_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        }
    }

    /** Fails if value is not a well-formed URL. */
    @Serializable
    data class InvalidUrl<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_STR_URL"
        override val isInvalid: Boolean get() = runCatching { URL(value).toURI() }.isFailure
        override val message: String get() = "Must be a valid URL."
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}=$value"
    }

    @Serializable
    data class TimestampFormatMustMatchIso8601<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_TIMESTAMP_FORMAT"
        override val isInvalid: Boolean
            get() = value?.let { !Regex(ISO_8601_UTC_REGEX).matches(it) } ?: false
        override val message: String get() = "Must be a valid ISO 8601 timestamp format (UTC, with Z)."
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: value='$value'"

        companion object {
            const val ISO_8601_UTC_REGEX = """^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,9})?Z$"""
        }
    }

    @Serializable
    data class TimestampMustBeChronologicallyCoherent<T : BaseDto>(
        val constrainedField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val value: String? get() = constrainedField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_TIMESTAMP_VALUE"
        override val isInvalid: Boolean
            get() = value?.let { DateTimeConverter.isChronologicallyCoherent(it) } ?: true
        override val message: String get() = "Timestamp must represent a real point in time."
        override val detail: String get() = "${receiver.idText}::${constrainedField.name}: value='$value'"
    }

    sealed class MultipleFieldValidation: FieldConstraintError()

    /** Fails if both workflow and step are null. */
    @Serializable
    data class TestCaseSubject<T : BaseDto>(
        val workflow: KProperty1<T, Workflow?>,
        val step: KProperty1<T, Step?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val workflowValue: Workflow? = workflow.get(receiver)
        private val stepValue: Step? = step.get(receiver)
        override val errorCode: String = "TYPE_INVALID_TEST_CASE_FOCUS"
        override val isInvalid: Boolean get() = workflowValue == null && stepValue == null
        override val message: String get() = "${TestCase::class.simpleName} must specify either a workflow or step subject."
        override val detail: String get() = if (workflowValue == null) "workflow was null." else "step was null"
    }

    /** Fails if all version constraint values are null. */
    @Serializable
    data class VersionSpecUndefined<T : BaseDto>(
        val versionMinField: KProperty1<T, String?>,
        val versionMaxField: KProperty1<T, String?>,
        val versionExactField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val versionMinValue: String? get() = versionMinField.get(receiver)
        private val versionMaxValue: String? get() = versionMinField.get(receiver)
        private val versionExactValue: String? get() = versionMinField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_VERSION_SPEC_UNDEFINED"
        override val isInvalid: Boolean get() = versionMinValue == null && versionMaxValue == null && versionExactValue == null
        override val message: String get() = "At least one version constraint must be specified."
        override val detail: String get() = receiver.idText
    }

    /** Fails if `completedAt` exists and is before `startedAt`. */
    @Serializable
    data class CompletedTimeBeforeStart<T : BaseDto>(
        val startTimestampField: KProperty1<T, String>,
        val completedTimestampField: KProperty1<T, String?>,
        val receiver: T
    ) : FieldConstraintError() {
        private val startedValue: String get() = startTimestampField.get(receiver)
        private val completedValue: String? get() = completedTimestampField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_TIME_COMPLETED_BEFORE_START"
        override val isInvalid: Boolean get() = if (completedValue == null) false else
            DateTimeConverter.fromString(completedValue!!) < DateTimeConverter.fromString(startedValue)
        override val message: String get() = "Completion timestamp cannot be before its start timestamp."
        override val detail: String get() = "${receiver.idText}::${startTimestampField.name}=$startedValue,::${completedTimestampField.name}=${completedValue}"

        companion object {
            const val error: String = "TYPE_INVALID_TIME_COMPLETED_BEFORE_START"
        }
    }

    /** Fails if `updatedAt` is before `createdAt`. */
    @Serializable
    data class UpdatedBeforeCreatedTimestamp<T : BaseDto>(
        val createdAt: KProperty1<T, String>,
        val updatedAt: KProperty1<T, String>,
        val receiver: T
    ) : FieldConstraintError() {
        private val createdValue: String get() = createdAt.get(receiver)
        private val updatedValue: String get() = updatedAt.get(receiver)
        override val errorCode: String = "TYPE_INVALID_TIME_UPDATE_BEFORE_CREATE"
        override val isInvalid: Boolean get() =
            DateTimeConverter.fromString(createdValue) > DateTimeConverter.fromString(updatedValue)
        override val message: String get() = "Update timestamp cannot be before its creation timestamp."
        override val detail: String get() = "${receiver.idText}::${updatedAt.name}: expected>=${createdValue}, actual=${updatedValue}"
    }

    @Serializable
    data class InconsistentOAuthId<T : BaseDto>(
        val providerField: KProperty1<T, String?>,
        val idField: KProperty1<T, String?>,
        val receiver: T,
    ) : FieldConstraintError() {
        private val provider = providerField.get(receiver)
        private val id = idField.get(receiver)
        override val errorCode: String = "TYPE_INVALID_OAUTH_ID_COMBINATION"
        override val isInvalid: Boolean
            get() = (provider == null && id == null) || (id != null && provider == null)
        override val message: String get() = "OAuth ID and provider must be set together."
        override val detail: String get() = "${receiver.idText}::${providerField.name}=$provider, ${idField.name}=$id"
    }
}

