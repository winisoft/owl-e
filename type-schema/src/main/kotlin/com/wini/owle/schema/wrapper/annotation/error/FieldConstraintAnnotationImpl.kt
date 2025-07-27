package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueArgument

/**
 * Marker interface for annotations that enforce validation constraints on DTO fields.
 *
 * Used to extract constraint metadata and associate errors with annotated properties.
 */
data class FieldConstraintAnnotationImpl(
    val delegate: KSAnnotation
): FieldConstraintAnnotation, KSAnnotation by delegate {

    override val attributes: Map<String, KSValueArgument> = arguments.associateBy { (it.name?.asString() ?: "?") }

    /**
     * Returns true if the annotation represented by this type was a [com.wini.owle.schema.error.ConstraintSimple],
     * whose shape is unique from other 'ad hoc' annotations that have unique concerns specific to them.
     */
    private val isSimpleConstraint: Boolean = arguments.any {
        it.name?.asString() == "errors"
    }

    /**
     * Returns the name of the argument that needs to be called on to determine the error or errors this annotation
     * exposes through its arguments
     */
    private val primaryArgName: KSValueArgument = arguments.first {
        it.name?.asString() == if (isSimpleConstraint) "errors" else "error"
    }

    /**
     * The fully qualified names of the OwleError types derived from the arguments of this [FieldConstraintAnnotation]
     * that point to the error or errors that are defined.
     *
     * Note that [com.wini.owle.schema.error.ConstraintSimple] annotation
     */
    override val errorFqNames: List<String> = primaryArgName.run {
        if (isSimpleConstraint)
            (this.value as List<*>).mapNotNull { elem ->
                (elem as? KSType)?.declaration?.qualifiedName?.asString()
            }
        else
            listOfNotNull((value as? KSType)?.declaration?.qualifiedName?.asString())
    }

}