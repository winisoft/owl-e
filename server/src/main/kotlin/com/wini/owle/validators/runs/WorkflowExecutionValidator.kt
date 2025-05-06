package com.wini.owle.validators.runs

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.user.UserValidator
import com.wini.owle.validators.workflows.WorkflowValidator


@OwleValidator(model = WorkflowExecution::class)
class WorkflowExecutionValidator(
    private val workflowValidator: WorkflowValidator,
    private val userValidator: UserValidator,
    private val stepExecutionValidator: StepExecutionValidator,
): BaseValidator<WorkflowExecution>() {

    override fun fieldValidations(dto: WorkflowExecution):List<FieldConstraintError> {
        return listOf()
    }
}