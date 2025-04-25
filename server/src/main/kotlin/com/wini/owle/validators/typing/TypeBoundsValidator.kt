package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeBound
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeBoundsValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeBound>() {
    override fun validate(dto: TypeBound): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested typeVariableDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.typeVariableDeclaration).violations)

        // Validate nested boundTypeDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.boundTypeDeclaration).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}