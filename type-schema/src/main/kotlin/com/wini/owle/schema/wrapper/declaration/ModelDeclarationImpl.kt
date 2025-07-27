package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.wini.owle.schema.wrapper.annotation.ModelAnnotation
import com.wini.owle.schema.wrapper.annotation.ModelAnnotationImpl
import com.wini.owle.schema.wrapper.annotation.error.ClassConstraintAnnotation
import com.wini.owle.schema.wrapper.annotation.error.ClassConstraintAnnotationImpl
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation


/**
 * Wraps a class annotated with [com.wini.owle.schema.ModelDefinition], exposing its properties and constraint annotations
 * for OpenAPI schema or validator generation.
 */
data class ModelDeclarationImpl(
    private val delegate: KSClassDeclaration
) : ModelDeclaration, KSClassDeclaration by delegate {

    /**
     * References the shouldSkipValidation property of the ModelDefinition annotation on this type to determine
     * whether the type is one that has been opted out of having [com.wini.owle.ksp.ValidatorGeneratorProcessor]
     * generate a validator class into the build directory
     */
    override val isValidatorGenTarget: Boolean
        get() = isNotValidatorGenTarget.not()

    /**
     * References the shouldSkipValidation property of the ModelDefinition annotation on this type to determine
     * whether the type is one that has been opted out of having [com.wini.owle.ksp.ValidatorGeneratorProcessor]
     * generate a validator class into the build directory
     */
    override val isNotValidatorGenTarget: Boolean
        get() = modelAnnotation.isSkipValidation

    /**
     * The [com.wini.owle.schema.ModelDefinition] annotation this KClassDefinition wrapper was decorated with
     */
    override val modelAnnotation: ModelAnnotation = annotations.first {
        it.annotationType.resolve().declaration.qualifiedName?.asString() == ModelAnnotation.QUALIFIED_NAME
    }.let {
        ModelAnnotationImpl(it)
    }

    /**
     * The lists of [FieldConstraintAnnotation] that denote the constraints that apply to a single field within this model
     * type, indexed by a [ConstrainedField], which wraps the basic [KSPropertyDeclaration].
     */
    override val constrainedFields: Map<ConstrainedField, List<FieldConstraintAnnotation>> =
        makeConstrainedFields(getAllProperties())
        .associateWith { it.fieldConstraintAnnotations }

    override val fqName: String
        get() = qualifiedName?.asString().toString()

    override val classConstraints: Map<ClassConstraintAnnotation, List<KSPropertyDeclaration>> = annotations.filter {
        ClassConstraintAnnotation.isClassConstraint(it)
    }.map {
        ClassConstraintAnnotationImpl(it)
    }.associateWith {
        it.targets.map { targetedProperty ->
            getAllProperties().firstOrNull { prop ->
                prop.simpleName.asString() == targetedProperty
            } ?: error("Model Declaration $fqName did not have the expected property $targetedProperty")
        }
    }

    private fun isConstrainedField(property: KSPropertyDeclaration): Boolean {
        return ConstrainedFieldImpl(property).run { hasConstraints }
    }

    private fun makeConstrainedFields(properties: Sequence<KSPropertyDeclaration>): List<ConstrainedField> {
        return properties.filter { isConstrainedField(it) }.map { ConstrainedFieldImpl(it) }.toList()
    }

}