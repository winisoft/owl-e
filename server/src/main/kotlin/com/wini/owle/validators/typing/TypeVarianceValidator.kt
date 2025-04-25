package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeVarianceValidator(
    private val typeVariableDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeVariance>() {
    override fun validate(dto: TypeVariance): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested typeVariableDeclaration
        violations.addAll(typeVariableDeclarationValidator.validate(dto.typeVariableDeclaration).violations)

        // variance is required (non-nullable enum)
        // No validation needed as it's a non-nullable enum that will have a default value

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}