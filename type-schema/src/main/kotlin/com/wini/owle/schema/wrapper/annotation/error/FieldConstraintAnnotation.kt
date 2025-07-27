package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueArgument

/**
 * Marker interface for annotations that enforce validation constraints on DTO fields.
 *
 * Used to extract constraint metadata and associate errors with annotated properties.
 */
interface FieldConstraintAnnotation : KSAnnotation {

    val attributes: Map<String, KSValueArgument>

    /**
     * The fully qualified names of all possible error types that may be emitted by this constraint.
     */
    val errorFqNames: List<String>

    companion object {
        /**
         * Prefix used to identify annotations that are treated as validation constraints.
         */
        const val FQN_STARTS_WITH = "com.wini.owle.schema.error.Constrain"
    }

}



