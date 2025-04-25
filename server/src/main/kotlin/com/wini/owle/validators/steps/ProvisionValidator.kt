package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.Provision
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.workflows.DataPointValidator

class ProvisionValidator(
    private val stepValidator: StepValidator,
    private val toolParamValidator: ToolParamValidator,
    private val dataPointsValidator: DataPointValidator
) : BaseValidator<Provision>() {
    override fun validate(dto: Provision): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        violations.addAll(stepValidator.validate(dto.step).violations)

        violations.addAll(toolParamValidator.validate(dto.toolParam).violations)

        violations.addAll(dataPointsValidator.validateAll(dto.dataPoints).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}