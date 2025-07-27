package com.wini.owle.ksp.utils.ext

import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation


fun KSPropertyDeclaration.isConstrainedField(): Boolean {
    return annotations.any {
        it.annotationType.resolve().declaration.qualifiedName?.asString()?.startsWith(FieldConstraintAnnotation.FQN_STARTS_WITH) == true
    }
}

fun List<KSPropertyDeclaration>.filterIsConstrainedField(): List<KSPropertyDeclaration> {
    return filter { it.isConstrainedField() }
}