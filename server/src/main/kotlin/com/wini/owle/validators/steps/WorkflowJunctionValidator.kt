package com.wini.owle.validators.steps


import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.WorkflowValidator

@OwleValidator(model = WorkflowJunction::class)
class WorkflowJunctionValidator(
    private val sourceStepValidator: StepValidator,
    private val targetWorkflowValidator: WorkflowValidator,
    private val triggerInquisitionValidator: InquisitionValidator
) : BaseValidator<WorkflowJunction>() {

    override fun fieldValidations(dto: WorkflowJunction):List<FieldConstraintError> {
        return listOf()
    }
}