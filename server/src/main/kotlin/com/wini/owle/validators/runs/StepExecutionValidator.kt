package com.wini.owle.validators.runs


import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.steps.StepValidator


@OwleValidator(model = StepExecution::class)
class StepExecutionValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator,
    private val stepValidator: StepValidator
): BaseValidator<StepExecution>() {

    override fun fieldValidations(dto: StepExecution):List<FieldConstraintError> {
        return listOf()
    }
}