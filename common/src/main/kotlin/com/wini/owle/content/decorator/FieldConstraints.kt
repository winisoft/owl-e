package com.wini.owle.content.decorator

import com.wini.owle.error.InvalidEmailFormat
import com.wini.owle.error.StringMustNotBeBlank


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
annotation class StringNotWhitespace(
    val errorCode: String = StringMustNotBeBlank.ERROR_CODE,
    val message: String = StringMustNotBeBlank.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMin(
    val minLength: Int,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMax(
    val maxLength: Int,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthExact(
    val requiredLength: Int,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntValueMin(
    val minValue: Int,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongValueMin(
    val minValue: Long,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DoubleRange(
    val minValue: Double,
    val maxValue: Double,
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListHasMembers(
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailFormat(
    val errorCode: String = InvalidEmailFormat.ERROR_CODE,
    val message: String = InvalidEmailFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class UrlFormat(
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampIso8601Format(
    val errorCode: String,
    val message: String
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampCoherent(
    val errorCode: String,
    val message: String
)