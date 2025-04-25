package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ErrorWatchValidator(
    private val stepValidator: StepValidator
) : BaseValidator<ErrorWatch>() {
    companion object {
        const val MAX_ERROR_TYPE_LENGTH = 255
    }

    override fun validate(dto: ErrorWatch): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested step
        violations.addAll(stepValidator.validate(dto.step).violations)

        // errorType is required and has max length
        if (dto.errorType.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "errorType"))
        }
        if (dto.errorType.length > MAX_ERROR_TYPE_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "errorType", MAX_ERROR_TYPE_LENGTH))
        }

        // Validate nested triggerStep
        violations.addAll(stepValidator.validate(dto.triggerStep).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}