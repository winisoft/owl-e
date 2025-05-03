package com.wini.owle.ksp

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OwleValidator(
    val model: KClass<*>
)
