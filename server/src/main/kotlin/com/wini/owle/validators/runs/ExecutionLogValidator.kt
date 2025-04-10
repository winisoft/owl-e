package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ExecutionLogValidator(
    private val stepExecutionValidator: StepExecutionValidator
) : BaseValidator<ExecutionLogDto>() {
    override fun validate(dto: ExecutionLogDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested stepExecution
        violations.addAll(stepExecutionValidator.validate(dto.stepExecution).violations)

        // message is required (non-nullable text)
        if (dto.message.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "message"))
        }

        // timestamp is handled by DB default if not provided

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}