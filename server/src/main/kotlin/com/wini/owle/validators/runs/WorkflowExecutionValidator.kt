package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class WorkflowExecutionValidator : BaseValidator<WorkflowExecutionDto>() {
    override fun validate(dto: WorkflowExecutionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // workflow is required (non-nullable reference)
        if (dto.workflow == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "workflow"))
        }

        // user is required (non-nullable reference)
        if (dto.user == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "user"))
        }

        // startedAt is required (non-nullable timestamp)
        if (dto.startedAt == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "startedAt"))
        }

        // completedAt is optional (nullable timestamp)
        // but if present, must be after startedAt
        if (dto.completedAt != null && dto.startedAt != null) {
            if (dto.completedAt <= dto.startedAt) {
                violations.add(TypeValidation.Generic(
                    dto.idToString(),
                    "completedAt",
                    "Completion time must be after start time"
                ))
            }
        }

        // status is required (non-nullable enum with default)
        if (dto.status == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "status"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}