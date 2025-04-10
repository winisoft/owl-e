package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeBoundsDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeBoundsValidator(
    private val boundTypeDeclarationValidator: TypeDeclarationValidator,
    private val typeVariableDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeBoundsDto>() {
    override fun validate(dto: TypeBoundsDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested typeVariableDeclaration
        violations.addAll(typeVariableDeclarationValidator.validate(dto.typeVariableDeclaration).violations)

        // Validate nested boundTypeDeclaration
        violations.addAll(boundTypeDeclarationValidator.validate(dto.boundTypeDeclaration).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}