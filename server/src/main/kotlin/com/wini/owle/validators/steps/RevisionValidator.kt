package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class RevisionValidator : BaseValidator<RevisionDto>() {
    override fun validate(dto: RevisionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // dataPoint is required (non-nullable reference)
        if (dto.dataPoint == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "dataPoint"))
        }

        // step is required (non-nullable reference)
        if (dto.step == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "step"))
        }

        // transformation is optional (nullable reference)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}