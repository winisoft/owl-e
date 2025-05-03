package com.wini.owle.validators.environs

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.validators.BaseValidator

/**
 * Validator for VersionSpec DTOs.
 * Validates version specification data against business rules and constraints.
 */
@OwleValidator(model = VersionSpec::class)
class VersionSpecValidator : BaseValidator<VersionSpec>() {


    override fun fieldValidations(dto: VersionSpec):List<FieldValidation> {

        return listOf()
    }
}