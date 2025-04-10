package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.TestResultDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TestResultValidator : BaseValidator<TestResultDto>() {
    override fun validate(dto: TestResultDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // testCase is required (non-nullable reference)
        if (dto.testCase == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "testCase"))
        }

        // workflowExecution is required (non-nullable reference)
        if (dto.workflowExecution == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "workflowExecution"))
        }

        // passed is required (non-nullable boolean)
        // No validation needed as it's a primitive boolean

        // executedAt is handled by DB default

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}