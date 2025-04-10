package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeParameterDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeParameterValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator,
    private val parameterTypeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeParameterDto>() {
    override fun validate(dto: TypeParameterDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested typeDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.typeDeclaration).violations)

        // position must be non-negative
        if (dto.position < 0) {
            violations.add(TypeValidation.LongRange(dto.typeName, dto.idToString(), "position", 0, Int.MAX_VALUE.toLong()))
        }

        // Validate nested parameterTypeDeclaration
        violations.addAll(parameterTypeDeclarationValidator.validate(dto.parameterTypeDeclaration).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}