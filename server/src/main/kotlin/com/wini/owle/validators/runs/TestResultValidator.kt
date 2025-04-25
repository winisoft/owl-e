package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TestResultValidator(
    private val testCaseValidator: TestCaseValidator,
    private val workflowExecutionValidator: WorkflowExecutionValidator
) : BaseValidator<WorkflowTestResult>() {
    override fun validate(dto: WorkflowTestResult): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested testCase
        violations.addAll(testCaseValidator.validate(dto.testCase).violations)

        // Validate nested workflowExecution
        violations.addAll(workflowExecutionValidator.validate(dto.workflowExecution).violations)

        // passed is a primitive boolean, no validation needed
        // executedAt is handled by DB default

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}