package com.wini.owle.schema

import com.wini.owle.content.*
import com.wini.owle.error.DoubleValueOoB
import com.wini.owle.error.IntValueMustMeetMinimum
import com.wini.owle.error.InvalidEmailFormat
import com.wini.owle.error.InvalidOAuthProvider
import com.wini.owle.error.InvalidResetToken
import com.wini.owle.error.InvalidUrlFormat
import com.wini.owle.error.ListMustNotBeEmpty
import com.wini.owle.error.LongValueMustMeetMinimum
import com.wini.owle.error.MustContainOnlyNumerals
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringLengthMustBeGt
import com.wini.owle.error.StringLengthMustBeLt
import com.wini.owle.error.StringMustHaveExactRequiredLength
import com.wini.owle.error.StringMustNotBeBlank
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent

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

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordSecurityPolicy

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringMustNotContainRestricted(
    val errorCode: String = "", // StringContainsRestrictedChars.ERROR_CODE/,
    val message: String = "" // StringContainsRestrictedChars.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResetTokenCharsOnly(
    val errorCode: String = "", //InvalidResetToken.ERROR_CODE,
    val message: String = "" //InvalidResetToken.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringMustNotBeBlank(
    val errorCode: String = "", //StringMustNotBeBlank.ERROR_CODE,
    val message: String = "" //StringMustNotBeBlank.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMin(
    val minLength: Int,
    val errorCode: String = "", //StringLengthMustBeGt.ERROR_CODE,
    val message: String = "" //StringLengthMustBeGt.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMax(
    val maxLength: Int,
    val errorCode: String = "", //StringLengthMustBeLt.ERROR_CODE,
    val message: String = "" //StringLengthMustBeLt.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthExact(
    val length: Int,
    val errorCode: String = "", //StringMustHaveExactRequiredLength.ERROR_CODE,
    val message: String = "" //StringMustHaveExactRequiredLength.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntValueMin(
    val minValue: Int,
    val errorCode: String = "", //IntValueMustMeetMinimum.ERROR_CODE,
    val message: String = "" //IntValueMustMeetMinimum.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongValueMin(
    val minValue: Long,
    val errorCode: String = "", //LongValueMustMeetMinimum.ERROR_CODE,
    val message: String = "" //LongValueMustMeetMinimum.MESSAGE,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DoubleMinAndMax(
    val minValue: Double,
    val maxValue: Double,
    val errorCode: String = "", //DoubleValueOoB.ERROR_CODE,
    val message: String = "" //DoubleValueOoB.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListHasMembers(
    val errorCode: String = "", //ListMustNotBeEmpty.ERROR_CODE,
    val message: String = "" //ListMustNotBeEmpty.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailTerms(
    val errorCode: String = "", //InvalidEmailFormat.ERROR_CODE,
    val message: String = "" //InvalidEmailFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class InvalidUrl(
    val errorCode: String = "", //InvalidUrlFormat.ERROR_CODE,
    val message: String = "" //InvalidUrlFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampIso8601Format(
    val errorCode: String = "", //TimestampFormatMustMatchIso8601.ERROR_CODE,
    val message: String = "" //TimestampFormatMustMatchIso8601.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampCoherent(
    val errorCode: String = "", //TimestampMustBeChronologicallyCoherent.ERROR_CODE,
    val message: String = "" //TimestampMustBeChronologicallyCoherent.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class NumeralsOnly(
    val errorCode: String = "", //MustContainOnlyNumerals.ERROR_CODE,
    val message: String = "" //MustContainOnlyNumerals.MESSAGE
)
