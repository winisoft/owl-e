package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class StepExecutionValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator,
    private val dateTimeConverter: DateTimeConverter
): BaseValidator<StepExecutionDto>() {
    override fun validate(dto: StepExecutionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // workflowExecution is required (non-nullable reference)
        if (dto.workflowExecution == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "workflowExecution"))
        }

        // step is required (non-nullable reference)
        if (dto.step == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "step"))
        }

        // startedAt is required (non-nullable timestamp)
        if (dto.startedAt == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "startedAt"))
        }

        // completedAt is optional (nullable timestamp)
        // but if present, must be after startedAt
        if (dto.completedAt != null) {
            if (dateTimeConverter.fromString(dto.completedAt!!) <= dateTimeConverter.fromString(dto.startedAt)) {
                violations.add(TypeValidation.Generic(
                    dto.idToString(),
                    "completedAt",
                    "Completion time must be after start time"
                ))
            }
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}