package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeVarianceDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeVarianceValidator : BaseValidator<TypeVarianceDto>() {
    override fun validate(dto: TypeVarianceDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // typeVariableDeclaration is required (non-nullable reference)
        if (dto.typeVariableDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeVariableDeclaration"))
        }

        // variance is required (non-nullable enum)
        if (dto.variance == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "variance"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}