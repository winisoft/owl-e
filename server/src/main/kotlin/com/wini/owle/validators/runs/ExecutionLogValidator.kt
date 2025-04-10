package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ExecutionLogValidator : BaseValidator<ExecutionLogDto>() {
    override fun validate(dto: ExecutionLogDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // stepExecution is required (non-nullable reference)
        if (dto.stepExecution == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "stepExecution"))
        }

        // logLevel is required (non-nullable enum)
        if (dto.logLevel == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "logLevel"))
        }

        // message is required (non-nullable text)
        if (dto.message.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "message"))
        }

        // timestamp is handled by DB default if not provided

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}