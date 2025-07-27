package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation


data class ModelAnnotationImpl(
    val delegate: KSAnnotation
): ModelAnnotation, KSAnnotation by delegate {

    /**
     * Checks if this `OwleModel` annotation has validation explicitly skipped.
     * This is true if the annotation has an argument named "skipValidation" with a value of `true`.
     */
    override val isSkipValidation: Boolean
        get() = arguments.any { arg ->
            arg.name?.asString() == "skipValidation" && arg.value == true
        }

    /**
     * Checks if this `OwleModel` annotation does not have validation skipped.
     * This is the negation of [isSkipValidation].
     */
    override val isNotSkipValidation: Boolean
        get() = isSkipValidation.not()

}