package com.wini.owle.schema.error

import kotlin.reflect.KClass


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InconsistentOAuthId(
    val providerName: String,
    val oauthName: String,
    val error: KClass<*> = InconsistentOAuthId::class
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PreventsTimeParadoxes(
    val happensFirstField: String,
    val happensSecondField: String,
    val error: KClass<*> = PreventsTimeParadoxes::class
)
