package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.workflows.DataPointValidator

class RevisionValidator(
    private val dataPointValidator: DataPointValidator,
    private val stepValidator: StepValidator
) : BaseValidator<RevisionDto>() {
    override fun validate(dto: RevisionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        violations.addAll(dataPointValidator.validate(dto.dataPoint).violations)

        violations.addAll(stepValidator.validate(dto.step).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}