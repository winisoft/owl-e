package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class PreReqInjectionValidator : BaseValidator<PreReqInjectionDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: PreReqInjectionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // junction is required (non-nullable reference)
        if (dto.junction == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "junction"))
        }

        // sourceDataPoint is required (non-nullable reference)
        if (dto.sourceDataPoint == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "sourceDataPoint"))
        }

        // injectedDataPoint is required (non-nullable reference)
        if (dto.injectedDataPoint == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "injectedDataPoint"))
        }

        // name is optional but has max length if present
        dto.name?.let { name ->
            if (name.length > MAX_NAME_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
            }
        }

        // description is optional (nullable text)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}