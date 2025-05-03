package com.wini.owle.ksp

import com.wini.owle.content.*

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntValueLessThan(
    val errorCode: String,
    val maximumValue: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntValueGreaterThan(
    val errorCode: String,
    val minimumValue: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongValueLessThan(
    val errorCode: String,
    val maximumValue: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongValueGreaterThan(
    val errorCode: String,
    val minimumValue: Long
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthLessThan(
    val errorCode: String,
    val maximumLength: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthGreaterThan(
    val errorCode: String,
    val minimumLength: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthExactReq(
    val errorCode: String,
    val requiredLength: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidEmailFormat(
    val errorCode: String,
    val minimumLength: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidUrlFormat(
    val errorCode: String,
    val minimumLength: Int
)

annotation class StringValidation(
    val errorCode: String = StringRestrictedChars.ERROR_CODE
)

annotation class LongValidation(
    val errorCode: String = StringRestrictedChars.ERROR_CODE
)

annotation class IntValidation(
    val errorCode: String = StringRestrictedChars.ERROR_CODE
)


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringMustNotContainRestricted(
    val errorCode: String = StringRestrictedChars.ERROR_CODE,
    val message: String = StringRestrictedChars.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResetTokenCharsOnly(
    val errorCode: String = InvalidResetTokenFormat.ERROR_CODE,
    val message: String = InvalidResetTokenFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OAuthProviderEnum(
    val errorCode: String = InvalidResetTokenFormat.ERROR_CODE,
    val message: String = InvalidResetTokenFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneSpecialCharMinimum(
    val errorCode: String = MustHaveOneSpecialChar.ERROR_CODE,
    val message: String = MustHaveOneSpecialChar.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneUppercaseMinimum(
    val errorCode: String = MustHaveOneCapitalLetter.ERROR_CODE,
    val message: String = MustHaveOneCapitalLetter.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneLowercaseMinimum(
    val errorCode: String = MustHaveOneLowercaseLetter.ERROR_CODE,
    val message: String = MustHaveOneLowercaseLetter.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneDigitMinimum(
    val errorCode: String = MustHaveOneNumeral.ERROR_CODE,
    val message: String = MustHaveOneNumeral.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringMustNotBeBlank(
    val errorCode: String = StringMustNotBeWhitespace.ERROR_CODE,
    val message: String = StringMustNotBeWhitespace.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMin(
    val minLength: Int,
    val errorCode: String = StringLengthMustBeGrTh.ERROR_CODE,
    val message: String = StringLengthMustBeGrTh.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMax(
    val maxLength: Int,
    val errorCode: String = StringLengthMustBeLsTh.ERROR_CODE,
    val message: String = StringLengthMustBeLsTh.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthExact(
    val length: Int,
    val errorCode: String = StringMustHaveRequiredLenExact.ERROR_CODE,
    val message: String = StringMustHaveRequiredLenExact.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntValueMin(
    val minValue: Int,
    val errorCode: String = IntValueMustMeetMin.ERROR_CODE,
    val message: String = IntValueMustMeetMin.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongValueMin(
    val minValue: Long,
    val errorCode: String = LongValueMustMeetMin.ERROR_CODE,
    val message: String = LongValueMustMeetMin.MESSAGE,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DoubleMinAndMax(
    val minValue: Double,
    val maxValue: Double,
    val errorCode: String = DoubleValueOutsideValidRange.ERROR_CODE,
    val message: String = DoubleValueOutsideValidRange.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListHasMembers(
    val errorCode: String = "BLAH BLAH BLAH",
    val message: String = "BLAH BLAH BLAH"
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailTerms(
    val errorCode: String = InvalidEmailFormat.ERROR_CODE,
    val message: String = InvalidEmailFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class InvalidUrl(
    val errorCode: String = com.wini.owle.content.InvalidUrl.ERROR_CODE,
    val message: String = com.wini.owle.content.InvalidUrl.MESSAGE
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CompletedTimeBeforeStart(
    val startedName: String,
    val completedName: String,
    val errorCode: String = com.wini.owle.content.CompletedTimeBeforeStart.ERROR_CODE,
    val message: String = com.wini.owle.content.CompletedTimeBeforeStart.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampIso8601Format(
    val errorCode: String = TimestampFormatMustMatchIso8601.ERROR_CODE,
    val message: String = TimestampFormatMustMatchIso8601.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampCoherent(
    val errorCode: String = TimestampMustBeChronologicallyCoherent.ERROR_CODE,
    val message: String = TimestampMustBeChronologicallyCoherent.MESSAGE
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class UpdatedBeforeCreatedTimestamp(
    val createdAtName: String,
    val updatedAtName: String,
    val errorCode: String = com.wini.owle.content.UpdatedBeforeCreatedTimestamp.ERROR_CODE,
    val message: String = com.wini.owle.content.UpdatedBeforeCreatedTimestamp.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class NumeralsOnly(
    val errorCode: String = NumericOnly.ERROR_CODE,
    val message: String = NumericOnly.MESSAGE
)



annotation class InconsistentOAuthId(val oauthProviderName: String, val oauthIdName: String) {
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_OAUTH_ID_COMBINATION"
    }
}

annotation class LongValueRange(val min: Long, val max: Long) {
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_LONG_OUTSIDE_RANGE"
    }
}

annotation class VersionSpecUndefined(val minName: String, val maxName: String, val exactName: String) {
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_VERSION_SPEC_UNDEFINED"
    }
}


annotation class PythonVersionSegmentFormat {
    companion object {
        const val ERROR_CODE: String = "TYPE_INVALID_PY_VERSION_FORMAT"
    }
}
