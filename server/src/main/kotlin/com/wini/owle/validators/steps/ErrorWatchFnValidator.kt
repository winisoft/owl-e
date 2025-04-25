package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ErrorWatchFnValidator(
    private val errorWatchValidator: ErrorWatchValidator
) : BaseValidator<ErrorWatchFn>() {
    companion object {
        const val MAX_FUNCTION_NAME_LENGTH = 255
    }

    override fun validate(dto: ErrorWatchFn): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested errorWatch
        violations.addAll(errorWatchValidator.validate(dto.errorWatch).violations)

        // functionName is required and has max length
        if (dto.functionName.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "functionName"))
        }
        if (dto.functionName.length > MAX_FUNCTION_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "functionName", MAX_FUNCTION_NAME_LENGTH))
        }

        // position must be non-negative
        if (dto.position < 0) {
            violations.add(TypeValidation.LongRange(dto.typeName, dto.idToString(), "position", 0, Int.MAX_VALUE.toLong()))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}