package com.wini.owle.ksp.utils

import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.ksp.utils.ext.findByFqns
import com.wini.owle.ksp.utils.ext.getArgument
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation
import com.wini.owle.schema.wrapper.declaration.ConstrainedField
import com.wini.owle.schema.wrapper.declaration.ModelDeclaration
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration

typealias ConstraintArgFormatter = (FieldConstraintAnnotation, ConstrainedField, String) -> String


object ConstraintExtractor {

    val ConstraintArgFormatters: Map<String, ConstraintArgFormatter> = mapOf(
        "ConstraintStringLenMax" to { ann, prop, className ->
            val max = ann.getArgument<Int>("maxLength") ?: 255
            "${className}::${prop.simpleName.asString()}, dto, $max"
        },
        "ConstraintStringLenExact" to { ann, prop, className ->
            val len = ann.getArgument<Int>("length") ?: 0
            "${className}::${prop.simpleName.asString()}, dto, $len"
        },
        "ConstraintDoubleRange" to { ann, prop, className ->
            val min = ann.getArgument<Double>("minValue") ?: 0.0
            val max = ann.getArgument<Double>("maxValue") ?: 1.0
            "${className}::${prop.simpleName.asString()}, dto, $min, $max"
        },
        "ConstraintLongValueMin" to { ann, prop, className ->
            val min = ann.getArgument<Long>("minValue") ?: 0L
            "${className}::${prop.simpleName.asString()}, dto, $min"
        },
        "ConstraintLongValueMax" to { ann, prop, className ->
            val max = ann.getArgument<Long>("maxValue") ?: 0L
            "${className}::${prop.simpleName.asString()}, dto, $max"
        },
        "ConstraintDecimalScale" to { ann, prop, className ->
            val precision = ann.getArgument<Int>("maxPrecision") ?: 0
            val scale = ann.getArgument<Int>("maxScale") ?: 0
            "${className}::${prop.simpleName.asString()}, dto, $precision, $scale"
        },
        // fallback for @ConstraintSimple or annotations not explicitly mapped
        "ConstraintSimple" to { _, prop, className ->
            "${className}::${prop.simpleName.asString()}, dto"
        }
    )

    fun multiFieldExtract(
        modelClass: ModelDeclaration,
        className: String,
        logger: KSPLogger
    ): List<String> {
        val lines = mutableListOf<String>()

        modelClass.classConstraints.forEach { (annotation, props) ->
            when (annotation.className) {
                "PreventsTimeParadox" -> {
                    val first = "$className::${props[0].simpleName.asString()}"
                    val second = "$className::${props[1].simpleName.asString()}"
                    lines.add("add(PreventsTimeParadoxes($first, $second, dto))")
                }
            }
        }

        return lines
    }

    fun propertyExtract(
        property: ConstrainedField,
        constraints: List<FieldConstraintAnnotation>,
        className: String,
        logger: KSPLogger,
        allErrors: List<ErrorDeclaration>
    ): List<String> {
        val propertyName = property.simpleName.asString()
        val propertyType = property.type.resolve()

        logger.info("Processing property: $propertyName of type ${propertyType.declaration.qualifiedName?.asString()}")

        if (property.hasNoConstraints) {
            logger.info("No field constraints found on $propertyName")
            return emptyList()
        }

        return constraints.flatMap { annotation ->
            val constraintAnnotationShortName = annotation.shortName.asString()

            (allErrors.findByFqns(annotation.errorFqNames) ?: emptyList()).mapNotNull { errorDeclaration: ErrorDeclaration ->
                val errorSimpleName = errorDeclaration.simpleName.asString()

                // A nullable field can only be handed to an error whose `constrainedField` is
                // declared over a nullable property - `KProperty1<T, String?>` accepts
                // `Step::color`, `KProperty1<T, String>` does not. Errors written for nullable
                // fields already treat null as passing. This used to be a blanket skip of every
                // nullable property, which silently dropped the constraint rather than reporting
                // it, so annotated caps on nullable columns were decorative.
                if (propertyType.isMarkedNullable && !errorDeclaration.acceptsNullableField()) {
                    logger.warn(
                        "$className.$propertyName is nullable but $errorSimpleName constrains a " +
                            "non-null property; the annotation has no effect. Declare the error's " +
                            "constrainedField as KProperty1<T, ...?> to apply it."
                    )
                    return@mapNotNull null
                }

                val formatter = ConstraintArgFormatters[constraintAnnotationShortName]
                    ?: ConstraintArgFormatters["ConstraintSimple"]!!

                val argsString = formatter(annotation, property, className)

                "add($errorSimpleName($argsString))"
            }
        }
    }

    /**
     * Whether this error's `constrainedField` parameter is declared over a nullable property,
     * i.e. its type is `KProperty1<T, X?>` rather than `KProperty1<T, X>`.
     */
    private fun ErrorDeclaration.acceptsNullableField(): Boolean {
        val field = primaryConstructor?.parameters?.firstOrNull() ?: return false
        return field.type.resolve().arguments.getOrNull(1)?.type?.resolve()?.isMarkedNullable == true
    }
}
