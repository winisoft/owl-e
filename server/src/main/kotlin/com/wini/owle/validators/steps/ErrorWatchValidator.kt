package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = ErrorWatch::class)
class ErrorWatchValidator(
    private val stepValidator: StepValidator
) : BaseValidator<ErrorWatch>() {

    companion object {
        const val MAX_ERROR_TYPE_LENGTH = 255
    }

    override fun fieldValidations(dto: ErrorWatch):List<FieldConstraintError> {
        return listOf()
    }
}