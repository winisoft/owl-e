package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.wini.owle.schema.error.ErrorDefinition
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration


/**
 * Whether this annotation is Owl-E's [ErrorDefinition] decorator — i.e. it marks the
 * annotated class as a structured, documentable API error.
 */
fun KSAnnotation.isOwleError(): Boolean =
    annotationType.resolve().declaration.qualifiedName?.asString() == ErrorDefinition.QUALIFIED_NAME

/**
 * Retrieves the value of an annotation argument by its name.
 *
 * @param name The name of the argument to retrieve.
 * @return The value of the argument, or `null` if the argument is not found.
 */
fun KSAnnotation.get(name: String): Any? = arguments.firstOrNull {
    it.name?.asString() == name
}?.value

/**
 * Retrieves and casts an annotation argument to the specified type [T].
 *
 * @param T The type to cast the argument value to.
 * @param name The name of the argument to retrieve.
 * @return The argument value cast to type [T], or `null` if the argument is not found or cannot be cast.
 */
inline fun <reified T> KSAnnotation.getArgument(name: String): T? {
    return arguments.find { it.name?.asString() == name }?.value as? T
}