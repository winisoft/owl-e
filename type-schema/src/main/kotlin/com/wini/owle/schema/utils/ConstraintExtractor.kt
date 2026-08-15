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

        if (propertyType.isMarkedNullable) {
            logger.info("Skipping nullable property: $propertyName as validators typically apply to non-null fields or handle nulls separately.")
            return emptyList()
        }

        if (property.hasNoConstraints) {
            logger.info("No field constraints found on $propertyName")
            return emptyList()
        }

        return constraints.flatMap { annotation ->
            val constraintAnnotationShortName = annotation.shortName.asString()

            (allErrors.findByFqns(annotation.errorFqNames) ?: emptyList()).map { errorDeclaration: ErrorDeclaration ->
                val errorSimpleName = errorDeclaration.simpleName.asString()

                val formatter = ConstraintArgFormatters[constraintAnnotationShortName]
                    ?: ConstraintArgFormatters["ConstraintSimple"]!!

                val argsString = formatter(annotation, property, className)

                "add($errorSimpleName($argsString))"
            }
        }
    }


}
