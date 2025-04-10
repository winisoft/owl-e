package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ErrorWatchFnDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ErrorWatchFnValidator : BaseValidator<ErrorWatchFnDto>() {
    companion object {
        const val MAX_FUNCTION_NAME_LENGTH = 255
    }

    override fun validate(dto: ErrorWatchFnDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // errorWatch is required (non-nullable reference)
        if (dto.errorWatch == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "errorWatch"))
        }

        // functionName is required and has max length
        if (dto.functionName.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "functionName"))
        }
        if (dto.functionName.length > MAX_FUNCTION_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "functionName", MAX_FUNCTION_NAME_LENGTH))
        }

        // position must be non-negative
        if (dto.position < 0) {
            violations.add(TypeValidation.LongRange(dto.idToString(), "position", 0, Int.MAX_VALUE.toLong()))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}