package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.workflows.DataPointValidator

class InquisitionValidator(
    private val dataPointValidator: DataPointValidator,
    private val onMetWorkflowStepValidator: StepValidator
) : BaseValidator<InquisitionDto>() {
    companion object {
        const val MAX_CLASS_LENGTH = 255
    }

    override fun validate(dto: InquisitionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // step is required (non-nullable reference)
        if (dto.step == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "step"))
        }

        // className is required and has max length
        if (dto.className.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "className"))
        }
        if (dto.className.length > MAX_CLASS_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "className", MAX_CLASS_LENGTH))
        }

        // dataPoint is required (non-nullable reference)
        if (dto.dataPoint == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "dataPoint"))
        }

        // onMetStep is required (non-nullable reference)
        if (dto.onMetWorkflowStep == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "onMetWorkflowStep"))
        }

        // executionOrder must be non-negative
        if (dto.executionOrder < 0) {
            violations.add(TypeValidation.LongRange(dto.idToString(), "executionOrder", 0, Int.MAX_VALUE.toLong()))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}