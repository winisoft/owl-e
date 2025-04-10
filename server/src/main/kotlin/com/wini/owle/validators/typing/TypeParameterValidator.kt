package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeParameterDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeParameterValidator : BaseValidator<TypeParameterDto>() {
    override fun validate(dto: TypeParameterDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // typeDeclaration is required (non-nullable reference)
        if (dto.typeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeDeclaration"))
        }

        // position must be non-negative
        if (dto.position < 0) {
            violations.add(TypeValidation.LongRange(dto.idToString(), "position", 0, Int.MAX_VALUE.toLong()))
        }

        // parameterTypeDeclaration is required (non-nullable reference)
        if (dto.parameterTypeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "parameterTypeDeclaration"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}