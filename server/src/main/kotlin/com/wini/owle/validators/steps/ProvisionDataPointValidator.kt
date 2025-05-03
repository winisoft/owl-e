package com.wini.owle.validators.steps

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = ProvisionDataPoint::class)
class ProvisionDataPointValidator : BaseValidator<ProvisionDataPoint>() {

    override fun fieldValidations(dto: ProvisionDataPoint):List<FieldValidation> {
        return listOf()
    }}