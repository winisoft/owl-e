package com.wini.owle.schema

import kotlin.reflect.KClass




@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class OwleConstraint(
    val value: KClass<*>
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class OwleIntConstraint(
    val validator: KClass<*>,
    val value: Int
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OwleConstraints(
    val value: Array<OwleConstraint>
)