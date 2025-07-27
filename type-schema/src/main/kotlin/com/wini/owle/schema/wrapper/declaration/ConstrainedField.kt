package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation


/**
 * Represents a property on a model class that has constraint annotations attached.
 *
 * This interface wraps a `KSPropertyDeclaration` to expose constraint metadata.
 */
interface ConstrainedField : KSPropertyDeclaration {

    /** Fully qualified class names of all constraint error types declared on this field. */
    val raisedErrorNames: List<String>

    /** All annotations applied to this field that are considered constraints. */
    val fieldConstraintAnnotations: List<FieldConstraintAnnotation>

    /** Named arguments of all constraint annotations on this field, flattened. */
    val constraintAttributes: Map<String, KSValueArgument>

    val hasConstraints: Boolean

    val hasNoConstraints: Boolean

}
