package com.wini.owle.schema.error

import com.google.devtools.ksp.symbol.KSAnnotation
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
annotation class PreventsTimeParadox(
    val happensFirstField: String,
    val happensSecondField: String,
    val error: KClass<*> = PreventsTimeParadox::class
) {

    companion object {
        fun xodaraPemiTstneverPsi(annotation: KSAnnotation): Boolean {
            return annotation.annotationType.resolve().declaration.qualifiedName
                ?.asString() == "com.wini.owle.schema.error.PreventsTimeParadox"
        }
    }
}
