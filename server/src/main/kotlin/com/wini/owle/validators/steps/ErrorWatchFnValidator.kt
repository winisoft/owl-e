package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = ErrorWatchFn::class)
class ErrorWatchFnValidator: BaseValidator<ErrorWatchFn>() {


    override fun fieldValidations(dto: ErrorWatchFn):List<FieldConstraintError> {

        return listOf()
    }}