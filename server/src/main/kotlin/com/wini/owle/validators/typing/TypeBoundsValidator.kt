package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeBoundsDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeBoundsValidator : BaseValidator<TypeBoundsDto>() {
    override fun validate(dto: TypeBoundsDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // typeVariableDeclaration is required (non-nullable reference)
        if (dto.typeVariableDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeVariableDeclaration"))
        }

        // boundTypeDeclaration is required (non-nullable reference)
        if (dto.boundTypeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "boundTypeDeclaration"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}