package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotationImpl


/**
 * Wraps a [KSPropertyDeclaration] annotated with one or more constraint annotations.
 * Provides typed access to constraint metadata for OpenAPI or validation purposes.
 */
data class ConstrainedFieldImpl(
    val delegate: KSPropertyDeclaration
): ConstrainedField, KSPropertyDeclaration by delegate {

    override val fieldConstraintAnnotations: List<FieldConstraintAnnotation> = delegate.annotations.filter {
        it.annotationType.resolve().declaration.qualifiedName?.asString()?.startsWith(FieldConstraintAnnotation.FQN_STARTS_WITH) == true
    }.map {
        FieldConstraintAnnotationImpl(it)
    }.toList()

    override val constraintAttributes: Map<String, KSValueArgument>
        get() = mutableMapOf<String, KSValueArgument>().apply {
            fieldConstraintAnnotations.forEach {
                putAll(it.attributes)
            }
        }

    override val raisedErrorNames: List<String>
        get() = fieldConstraintAnnotations.flatMap { it.errorFqNames }

    val attributes: Map<String, Any?> = mutableMapOf<String, Any?>().apply {
        fieldConstraintAnnotations.forEach { anno ->
            val tagValue = anno.arguments.find { it.name?.asString() == "tags" }?.value
        }
    }

    override val hasConstraints: Boolean
        get() = fieldConstraintAnnotations.isNotEmpty()

    override val hasNoConstraints: Boolean
        get() = hasConstraints.not()

}