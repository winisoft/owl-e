package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.wini.owle.ksp.doc.models.OwleErrorDoc
import com.wini.owle.ksp.utils.ConstraintRegistry
import com.wini.owle.ksp.utils.LocalizedStringResolver


val KSPropertyDeclaration.errorThrownFqn: String
    get() = annotations.map {
        it.annotationType.resolve().declaration.qualifiedName?.asString()
    }.filter {
        it in ConstraintRegistry.constraintAnnotationFqns
    }.filterNotNull().mapNotNull {
        ConstraintRegistry.getErrorQualifiedName(it)
    }.first()

fun KSPropertyDeclaration.findRaisedErrors(errors: List<KSClassDeclaration>): List<KSClassDeclaration> {

    val errorFqns = annotations.map { it: KSAnnotation ->
        val annoDeclaration = it.annotationType.resolve().declaration
        val annoFqn = annoDeclaration.qualifiedName?.asString()!!

        ConstraintRegistry.getErrorQualifiedName(annoFqn)!!
    }

    return errorFqns.map { errorFqn ->
        errors.first { error ->
            error.qualifiedName?.asString()!! == errorFqn
        }
    }.toList()

}
