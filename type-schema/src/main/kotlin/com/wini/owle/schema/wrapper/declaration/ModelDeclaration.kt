package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.wini.owle.schema.wrapper.annotation.ModelAnnotation
import com.wini.owle.schema.wrapper.annotation.error.ClassConstraintAnnotation
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation

/**
 * Represents a class annotated with `@ModelDefinition`.
 *
 * This wraps a `KSClassDeclaration` and models an API-visible data model (DTO).
 */
interface ModelDeclaration : KSClassDeclaration {

    /** The raw annotation data from `@ModelDefinition`. */
    val modelAnnotation: ModelAnnotation

    /**
     * A map of constrained properties to their associated constraint annotations.
     *
     * Only includes properties decorated with one or more annotations recognized as constraints.
     */
    val constrainedFields: Map<ConstrainedField, List<FieldConstraintAnnotation>>

    /** True if this model is marked to skip validator generation. */
    val isNotValidatorGenTarget: Boolean
        get() = modelAnnotation.isSkipValidation

    /** Inverse of `isNotValidatorGenTarget` — true if validator should be generated. */
    val isValidatorGenTarget: Boolean
        get() = isNotValidatorGenTarget.not()

    val fqName: String

    val classConstraints: Map<ClassConstraintAnnotation, List<KSPropertyDeclaration>>


}
