package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ProvisionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ProvisionValidator : BaseValidator<ProvisionDto>() {
    override fun validate(dto: ProvisionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // step is required (non-nullable reference)
        if (dto.step == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "step"))
        }

        // toolParam is required (non-nullable reference)
        if (dto.toolParam == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "toolParam"))
        }

        // dataPoints list is required but can be empty
        if (dto.dataPoints == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "dataPoints"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}