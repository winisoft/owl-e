package com.wini.owle.schema.error

import kotlin.reflect.KClass

enum class ConstraintTargeting {
    STR, INT, LONG, FIELD_PAIR
}
enum class ConstraintArgSignature {
    SINGLE_LONG, SINGLE_INT, SINGLE_STRING,
    LONG_RANGE, INT_RANGE, DOUBLE_RANGE
}
enum class ConstraintFailCondition {
    COMPOUND,
    SUBJ_LESS_THAN_ARG,
    SUBJ_LESS_THAN_OR_EQ_ARG,
    SUBJ_EQUAL_TO_ARG,
    SUBJ_NOT_EQUAL_TO_ARG,
    SUBJ_GREATER_THAN_ARG,
    SUBJ_GREATER_THAN_OR_EQ_ARG,
    SUBJ_MATCHES_STR_FORMAT_ARG,
    SUBJ_NOT_MATCHES_STR_FORMAT_ARG
}

enum class ArgTypeName {
    STRING,
    INT_32,
    INT_64
}

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class PositiveValueLong(
    val error: KClass<*>,
    val subject: ConstraintTargeting = ConstraintTargeting.LONG,
    val argSignature: ConstraintArgSignature = ConstraintArgSignature.SINGLE_INT,
    val rule: ConstraintFailCondition = ConstraintFailCondition.SUBJ_LESS_THAN_ARG,
    val arg0Name: String = "minimumValue",
    val arg0Value: String = "0",
    val arg0Type: ArgTypeName = ArgTypeName.INT_32
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringSafeChars(
    val error: KClass<*>,
    val subject: ConstraintTargeting = ConstraintTargeting.STR,
    val rule: ConstraintFailCondition = ConstraintFailCondition.COMPOUND
)


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordSecurityPolicy(
    val subject: ConstraintTargeting = ConstraintTargeting.STR,
    val rule: ConstraintFailCondition = ConstraintFailCondition.COMPOUND
)


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResetTokenCharsOnly(
    val error: KClass<*>,
    val subject: ConstraintTargeting = ConstraintTargeting.STR,
    val rule: ConstraintFailCondition = ConstraintFailCondition.SUBJ_NOT_MATCHES_STR_FORMAT_ARG,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringNotBlank(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListHasMembers(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailFormat(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class UrlFormat(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampIso8601Format(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimestampCoherent(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class NumeralsOnly(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class PositiveValueInt(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringNotEmpty(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OAuthProviderEnum(val error: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthMax(
    val error: KClass<*>,
    val maxLength: Int,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringLengthExact(
    val error: KClass<*>,
    val length: Int,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntValueMin(
    val error: KClass<*>,
    val minValue: Int,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class LongValueMin(
    val error: KClass<*>,
    val minValue: Long,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DoubleInRange(
    val error: KClass<*>,
    val minValue: Double,
    val maxValue: Double,
)

