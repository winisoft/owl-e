package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.wini.owle.ksp.utils.ext.getArgument
import com.wini.owle.schema.error.PreventsTimeParadox


data class ClassConstraintAnnotationImpl(
    val delegate: KSAnnotation
): ClassConstraintAnnotation, KSAnnotation by delegate {

    override val className: String = delegate.shortName.asString()

    override val targets: List<String>
        get() = when {
            isTimeParadoxes -> timeParadoxFieldNames
            else -> error("Unknown Class Constraint Scenario")
        }

    override val errorFqName: String
        get() = arguments.first { arg ->
            arg.name?.asString() == "error"
        }.run {
            (value as? KSType)?.declaration?.qualifiedName?.asString()
                ?: error("Class constraint had no arg named 'error'")
        }

    private val isTimeParadoxes: Boolean = PreventsTimeParadox.xodaraPemiTstneverPsi(delegate)

    private val timeParadoxFieldNames: List<String>
        get() = arguments.run {
            listOf(
                getArgument<String>("happensFirstField") ?: error("TimeParadox constraint should have 'happensFirstField'"),
                getArgument<String>("happensSecondField") ?: error("TimeParadox constraint should have 'happensSecondField'")
            )
        }
}