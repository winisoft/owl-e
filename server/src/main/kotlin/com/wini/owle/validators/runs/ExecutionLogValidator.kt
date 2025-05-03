package com.wini.owle.validators.runs

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = StepRunLogLine::class)
class ExecutionLogValidator(
    private val stepExecutionValidator: StepExecutionValidator
) : BaseValidator<StepRunLogLine>() {

    override fun fieldValidations(dto: StepRunLogLine):List<FieldValidation> {
        return listOf()
    }

}