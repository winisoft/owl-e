package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.wini.owle.ksp.utils.ext.isConstraintDecor
import com.wini.owle.ksp.utils.ConstraintRegistry
import kotlin.sequences.mapNotNull

/**
 * Checks if this annotation is an `OwleModel` annotation.
 * An annotation is considered an `OwleModel` if its fully qualified name is "com.wini.owle.schema.OwleModel".
 */
val KSAnnotation.isOwleModel: Boolean
    get() = annotationType.resolve().declaration.qualifiedName?.asString() == "com.wini.owle.schema.OwleModel"

/**
 * Checks if this `OwleModel` annotation has validation explicitly skipped.
 * This is true if the annotation [isOwleModel] and it has an argument named "skipValidation" with a value of `true`.
 */
val KSAnnotation.isSkipValidation: Boolean
    get() = isOwleModel && arguments.any { arg ->
        arg.name?.asString() == "skipValidation" && arg.value == true
    }

/**
 * Checks if this `OwleModel` annotation does not have validation skipped.
 * This is the negation of [isSkipValidation].
 */
val KSAnnotation.isNotSkipValidation: Boolean
    get() = isSkipValidation.not()

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
 * Finds an annotation on a [KSAnnotated] element by its fully qualified name.
 *
 * @param fqName The fully qualified name of the annotation to find.
 * @return The [KSAnnotation] if found, or `null` otherwise.
 */
fun KSAnnotated.findAnnotation(fqName: String): KSAnnotation? = annotations.firstOrNull {
    it.annotationType.resolve().declaration.qualifiedName?.asString() == fqName
}

/**
 * Checks if this annotation is an Owle API-related annotation.
 * An annotation is considered an Owle API annotation if its fully qualified name
 * starts with "com.wini.owle.schema.api.OwleApi".
 */
fun KSAnnotation.isOwleApiAnnotation(): Boolean =
    annotationType.resolve().declaration.qualifiedName?.asString()
        ?.startsWith("com.wini.owle.schema.api.OwleApi") == true

/**
 * Checks if this annotation is an Owle Error definition annotation.
 * An annotation is considered an Owle Error if its fully qualified name
 * starts with "com.wini.owle.schema.error".
 */
fun KSAnnotation.isOwleError(): Boolean =
    annotationType.resolve().declaration.qualifiedName?.asString()
        ?.startsWith("com.wini.owle.schema.error") == true

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

/**
 * Filters a list of [KSAnnotation]s to include only those that are considered field constraint decorators.
 * This determination is made by checking the [isConstraintDecor] property on the annotation's declaration.
 *
 * @return A list containing only the field constraint annotations.
 */
fun List<KSAnnotation>.filterIsFieldConstraint(): List<KSAnnotation> {
    return filter { ann ->
        ann.annotationType.resolve().declaration.isConstraintDecor
    }
}

/**
 * Finds the error ID associated with a given field constraint annotation from a list of [KSClassDeclaration]s.
 *
 * This function performs the following steps:
 * 1. Resolves the fully qualified name (FQN) of the provided `constraintAnnotation`.
 * 2. Uses [ConstraintRegistry.getErrorQualifiedName] to get the FQN of the OwleError class linked to this constraint.
 * 3. Searches the provided list of `KSClassDeclaration`s for a class matching the error FQN.
 * 4. If a matching error class is found, it looks for an annotation on that class that [isOwleError].
 * 5. Retrieves the "id" argument from this OwleError annotation.
 *
 * @param constraintAnnotation The field constraint annotation for which to find the associated error ID.
 * @return The error ID (as a [String]) if found.
 * @throws IllegalStateException if any step in the process fails to find the required elements (e.g.,
 *         the constraint FQN cannot be determined, no corresponding error FQN is registered,
 *         no matching error class is found in the list, the error class is not annotated with an OwleError,
 *         or the OwleError annotation does not have an "id" property).
 */
fun List<KSClassDeclaration>.findConstraintRaisedErrorId(constraintAnnotation: KSAnnotation): String {

    return constraintAnnotation.annotationType.resolve().declaration
        .qualifiedName?.asString()?.let { constraintFqn ->
            ConstraintRegistry.getErrorQualifiedName(constraintFqn)
        }?.let { errorFqn ->
            firstOrNull { it.qualifiedName?.asString() == errorFqn }
        }?.let { matchingErrorClass: KSClassDeclaration ->
            matchingErrorClass.annotations
                .firstOrNull { it.isOwleError() }
                ?.get("id")
                ?.toString()
        } ?: error("Could not find a KSClassDeclaration for a registered FieldConstraint Anno & raised OwleError pair")
}
