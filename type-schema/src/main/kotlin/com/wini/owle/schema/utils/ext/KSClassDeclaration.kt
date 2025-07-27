package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration


val KSClassDeclaration.isResolved: Boolean
    get() = superTypes.all { typeRef ->
        typeRef.resolve().run {
            declaration is KSClassDeclaration && declaration.qualifiedName != null
        }
    }
//
//val ErrorDeclaration.isConstraintDecor: Boolean
//    get() = qualifiedName?.asString()?.let { it ->
//        it in ConstraintRegistry.annoQualifiedNames
//    } ?: false
fun List<ErrorDeclaration>.findByFqm(errorFqn: String): ErrorDeclaration? {
    return find  {
        it.qualifiedName?.asString() == errorFqn
    }
}

fun List<ErrorDeclaration>.findByFqns(errorFqns: List<String>): List<ErrorDeclaration>? {
    return errorFqns.mapNotNull { errorFqn ->
        findByFqm(errorFqn)
    }
}

fun List<ErrorDeclaration>.findByResKey(errorFqn: String): ErrorDeclaration? {
    return find  {
        LocalizedStringResolver.resolve(it.errorResKey) == errorFqn
    }
}

fun List<ErrorDeclaration>.findByResKeys(errorFqns: List<String>): List<ErrorDeclaration>? {
    return errorFqns.mapNotNull { errorFqn ->
        findByResKey(errorFqn)
    }
}

fun List<ErrorDeclaration>.codeAndMessage(qualifiedName: String): Pair<String, String>? {
    return find {
        it.qualifiedName?.asString() == qualifiedName
    }?.run {
        code to message
    }
}