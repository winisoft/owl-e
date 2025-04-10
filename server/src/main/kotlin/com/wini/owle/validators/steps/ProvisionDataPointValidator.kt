package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ProvisionDataPointValidator : BaseValidator<ProvisionDataPointDto>() {
    override fun validate(dto: ProvisionDataPointDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // provision is required (non-nullable reference)
        if (dto.provision == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "provision"))
        }

        // dataPoint is required (non-nullable reference)
        if (dto.dataPoint == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "dataPoint"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}