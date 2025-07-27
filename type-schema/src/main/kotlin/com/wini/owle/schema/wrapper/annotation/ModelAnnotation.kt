package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation

/**
 * A simple wrapper around the [KSAnnotation] that KSP resolves for the [com.wini.owle.schema.ModelDefinition]
 * decorator that is applied to models in the Owl-E app.
 */
interface ModelAnnotation: KSAnnotation {

    /**
     * Checks if this `OwleModel` annotation has validation explicitly skipped.
     * This is true if the annotation has an argument named "skipValidation" with a value of `true`.
     */
    val isSkipValidation: Boolean

    /**
     * Checks if this `OwleModel` annotation does not have validation skipped.
     * This is the negation of [isSkipValidation].
     */
    val isNotSkipValidation: Boolean

    companion object {
        const val QUALIFIED_NAME = "com.wini.owle.schema.ModelDefinition"
    }
}