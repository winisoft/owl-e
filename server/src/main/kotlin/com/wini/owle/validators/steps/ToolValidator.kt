package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ToolDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ToolValidator : BaseValidator<ToolDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 512
    }

    override fun validate(dto: ToolDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is optional (nullable text)

        // body is required (non-nullable text)
        if (dto.body.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "body"))
        }

        // returnTypeDeclaration is optional (nullable reference)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}