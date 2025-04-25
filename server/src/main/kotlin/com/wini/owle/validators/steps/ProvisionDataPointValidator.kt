package com.wini.owle.validators.steps

import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ProvisionDataPointValidator : BaseValidator<ProvisionDataPoint>() {
    override fun validate(dto: ProvisionDataPoint): ValidationResult {

        return ValidationResult.valid()
    }
}