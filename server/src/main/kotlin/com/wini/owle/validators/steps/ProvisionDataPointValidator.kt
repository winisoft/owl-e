package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = ProvisionDataPoint::class)
class ProvisionDataPointValidator : BaseValidator<ProvisionDataPoint>() {

    override fun fieldValidations(dto: ProvisionDataPoint):List<FieldConstraintError> {
        return listOf()
    }}