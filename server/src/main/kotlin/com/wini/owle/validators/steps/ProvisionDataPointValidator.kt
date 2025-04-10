package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ProvisionDataPointValidator : BaseValidator<ProvisionDataPointDto>() {
    override fun validate(dto: ProvisionDataPointDto): ValidationResult {

        return ValidationResult.valid()
    }
}