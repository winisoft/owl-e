package com.wini.owle.validators.runs

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = WorkflowTestResult::class)
class TestResultValidator(
    private val testCaseValidator: TestCaseValidator,
    private val workflowExecutionValidator: WorkflowExecutionValidator
) : BaseValidator<WorkflowTestResult>() {

    override fun fieldValidations(dto: WorkflowTestResult):List<FieldValidation> {
        return listOf()
    }
}