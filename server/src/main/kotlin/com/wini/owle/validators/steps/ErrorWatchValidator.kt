package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ErrorWatchValidator(
    private val stepValidator: StepValidator,
    private val triggerStepValidator: StepValidator
) : BaseValidator<ErrorWatchDto>() {
    companion object {
        const val MAX_ERROR_TYPE_LENGTH = 255
    }

    override fun validate(dto: ErrorWatchDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // step is required (non-nullable reference)
        if (dto.step == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "step"))
        }

        // errorType is required and has max length
        if (dto.errorType.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "errorType"))
        }
        if (dto.errorType.length > MAX_ERROR_TYPE_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "errorType", MAX_ERROR_TYPE_LENGTH))
        }

        // onMetStep is required (non-nullable reference)
        if (dto.triggerStep == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "onMetStep"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}