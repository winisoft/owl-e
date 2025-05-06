package com.wini.owle.validators.runs

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = WorkflowTestResult::class)
class TestResultValidator(
    private val testCaseValidator: TestCaseValidator,
    private val workflowExecutionValidator: WorkflowExecutionValidator
) : BaseValidator<WorkflowTestResult>() {

    override fun fieldValidations(dto: WorkflowTestResult):List<FieldConstraintError> {
        return listOf()
    }
}