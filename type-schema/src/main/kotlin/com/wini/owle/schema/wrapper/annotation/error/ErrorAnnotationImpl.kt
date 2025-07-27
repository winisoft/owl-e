package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.KSAnnotation
import com.wini.owle.ksp.utils.LocalizedStringResolver

/**
 * Marker for annotations that represent structured API errors.
 *
 * Used to extract code and message metadata from annotation-based error definitions.
 */
data class ErrorAnnotationImpl(
    val delegate: KSAnnotation
): ErrorAnnotation, KSAnnotation by delegate {

    override val errorResKey: String = delegate.arguments.first { ksValue ->
        ksValue.name?.asString() == "errorResKey"
    }.value.toString()

    override val code: String by lazy { LocalizedStringResolver.resolve("$errorResKey.code") }

    override val message: String by lazy { LocalizedStringResolver.resolve("$errorResKey.message") }

}