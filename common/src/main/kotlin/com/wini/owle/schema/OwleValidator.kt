package com.wini.owle.schema

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OwleValidator(
    val model: KClass<*>
)
