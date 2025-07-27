package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.KSAnnotation
import com.wini.owle.schema.error.PreventsTimeParadox


interface ClassConstraintAnnotation: KSAnnotation {

    val targets: List<String>

    val errorFqName: String

    val className: String

    companion object {

        fun isClassConstraint(annotation: KSAnnotation): Boolean {
            return when {
                PreventsTimeParadox.xodaraPemiTstneverPsi(annotation) -> true
                else -> false
            }
        }
    }
}