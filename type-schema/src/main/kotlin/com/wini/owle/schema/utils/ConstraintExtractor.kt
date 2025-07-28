package com.wini.owle.ksp.utils

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.wini.owle.ksp.utils.ext.filterIsFieldConstraint
import com.wini.owle.ksp.utils.ext.findConstraintRaisedErrorId
import com.wini.owle.ksp.utils.ext.getArgument

object ConstraintExtractor {

    fun multiFieldExtract(
        modelClass: KSClassDeclaration,
        className: String,
        logger: KSPLogger
    ): List<String> {
        val lines = mutableListOf<String>()

        modelClass.annotations.forEach { annotation ->
            val annotationName = annotation.annotationType.resolve().declaration.simpleName.asString()

            when (annotationName) {
                "PreventsTimeParadoxes" -> {
                    val first = annotation.getArgument<String>("happensFirstField")
                    val second = annotation.getArgument<String>("happensSecondField")
                    if (first != null && second != null) {
                        lines.add("// Multi-field: PreventsTimeParadoxes")
                        lines.add("add(PreventsTimeParadoxes($className::$first, $className::$second, dto))")
                    }
                }

                "InconsistentOAuthId" -> {
                    val provider = annotation.getArgument<String>("oauthProviderName")
                    val id = annotation.getArgument<String>("oauthIdName")
                    if (provider != null && id != null) {
                        lines.add("// Multi-field: InconsistentOAuthId")
                        lines.add("add(InconsistentOAuthId($className::$provider, $className::$id, dto))")
                    }
                }

                "TestCaseSubject" -> {
                    lines.add("// Multi-field: TestCaseSubject")
                    lines.add("add(TestCaseSubject($className::workflow, $className::step, dto))")
                }
            }
        }

        return lines
    }

    fun propertyExtract(
        property: KSPropertyDeclaration,
        className: String,
        logger: KSPLogger
    ): List<String> {
        val propertyName = property.simpleName.asString()
        val propertyType = property.type.resolve()

        logger.info("Processing property: $propertyName of type ${propertyType.declaration.qualifiedName?.asString()}")

        if (propertyType.isMarkedNullable) {
            logger.info("Skipping nullable property: $propertyName")
            return emptyList()
        }

        val annotations = property.annotations.toList().filterIsFieldConstraint()
        if (annotations.isEmpty()) {
            logger.info("No field constraints found on $propertyName")
        }

        val properties: List<String> = annotations.mapNotNull { annotation ->
            val annotationName = annotation.annotationType.resolve().declaration.simpleName.asString()
            val constraintFn = ConstraintRegistry.getErrorQualifiedName(annotationName)

            if (constraintFn == null) {
                logger.warn("No constraint function found for annotation: $annotationName on property $propertyName")
                return@mapNotNull null
            }

            val argString = when (annotationName) {
                "StringLengthMax" -> {
                    val max = annotation.arguments.find { it.name?.asString() == "maxLength" }?.value as? Int ?: 255
                    "$className::$propertyName, $max, dto"
                }
                "StringLengthMin" -> {
                    val min = annotation.arguments.find { it.name?.asString() == "minLength" }?.value as? Int ?: 0
                    "$className::$propertyName, $min, dto"
                }
                "StringLengthExact" -> {
                    val len = annotation.arguments.find { it.name?.asString() == "length" }?.value as? Int ?: 0
                    "$className::$propertyName, $len, dto"
                }
                "IntValueMin" -> {
                    val min = annotation.arguments.find { it.name?.asString() == "minValue" }?.value as? Int ?: 0
                    "$className::$propertyName, $min, dto"
                }
                "LongValueMin" -> {
                    val min = annotation.arguments.find { it.name?.asString() == "minValue" }?.value as? Long ?: 0L
                    "$className::$propertyName, $min, dto"
                }
                else -> "$className::$propertyName, dto"
            }

            logger.info("Resolved constraint for $propertyName: $constraintFn($argString)")
            "$constraintFn($argString)"
        }

        return properties
    }

}
