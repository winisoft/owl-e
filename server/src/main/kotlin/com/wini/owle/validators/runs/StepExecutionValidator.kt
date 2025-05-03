package com.wini.owle.validators.runs


import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.steps.StepValidator


@OwleValidator(model = StepExecution::class)
class StepExecutionValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator,
    private val stepValidator: StepValidator
): BaseValidator<StepExecution>() {

    override fun fieldValidations(dto: StepExecution):List<FieldValidation> {
        return listOf()
    }
}