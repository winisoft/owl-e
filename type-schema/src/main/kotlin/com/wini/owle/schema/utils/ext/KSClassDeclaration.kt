package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.squareup.kotlinpoet.ClassName
import com.wini.owle.ksp.utils.ConstraintRegistry


fun KSClassDeclaration.isResolved(resolver: Resolver): Boolean {
    // If the supertype is missing (e.g., still being processed), this will return null
    return this.superTypes.all { typeRef ->
        val resolved = typeRef.resolve()
        resolved.declaration is KSClassDeclaration && resolved.declaration.qualifiedName != null
    }
}

fun KSClassDeclaration.toClassName(): ClassName {
    val pkg = this.packageName.asString()
    val name = this.simpleName.asString()
    return ClassName(pkg, name)
}

/**
 * Whether this class is assignable to another by FQ name.
 */
fun KSClassDeclaration.isSubclassOf(
    superFqName: String,
    resolver: Resolver
): Boolean {
    return resolver.getClassDeclarationByName(superFqName)?.let { base ->
        base.asStarProjectedType().isAssignableFrom(base.asStarProjectedType())
    } ?: false
}

//val KSClassDeclaration.isConstraintDecor: Boolean
//    get() = qualifiedName?.asString()?.let { it ->
//        it in ConstraintRegistry.annoQualifiedNames
//    } ?: false

val KSDeclaration.isConstraintDecor: Boolean
    get() = qualifiedName?.asString()?.let { it ->
        it in ConstraintRegistry.annoQualifiedNames
    } ?: false


fun List<KSClassDeclaration>.findErrorIdRaised(fieldConstraintFqn: String): String {
    return firstOrNull {
        it.qualifiedName?.asString() == fieldConstraintFqn
    }?.annotations
        ?.firstOrNull { it.isOwleError() }
        ?.get("id")
        ?.toString()!!
}

val KSClassDeclaration.isValidatorGenTarget: Boolean
    get() = isNotValidatorGenTarget.not()

val KSClassDeclaration.isNotValidatorGenTarget: Boolean
    get() = annotations.any { it.isSkipValidation }
