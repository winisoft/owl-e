package com.wini.owle.schema.error

import kotlin.reflect.KClass


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConstraintSimple(val errors: Array<KClass<*>>)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConstraintStringLenMax(
    val error: KClass<*>,
    val maxLength: Int,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConstraintStringLenExact(
    val error: KClass<*>,
    val length: Int,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConstraintDoubleRange(
    val error: KClass<*>,
    val minValue: Double,
    val maxValue: Double,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConstraintLongValueMin(
    val error: KClass<*>,
    val minValue: Long,
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConstraintLongValueMax(
    val error: KClass<*>,
    val maxValue: Long,
)