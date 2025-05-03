package com.wini.owle.validators.steps

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = ErrorWatchFn::class)
class ErrorWatchFnValidator: BaseValidator<ErrorWatchFn>() {


    override fun fieldValidations(dto: ErrorWatchFn):List<FieldValidation> {

        return listOf()
    }}