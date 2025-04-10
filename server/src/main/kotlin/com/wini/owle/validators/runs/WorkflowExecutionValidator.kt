package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.user.UserValidator
import com.wini.owle.validators.workflows.WorkflowValidator


class WorkflowExecutionValidator(
    private val workflowValidator: WorkflowValidator,
    private val userValidator: UserValidator,
    private val dateTimeConverter: DateTimeConverter
): BaseValidator<WorkflowExecutionDto>() {
    override fun validate(dto: WorkflowExecutionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested workflow
        violations.addAll(workflowValidator.validate(dto.workflow).violations)

        // Validate nested user
        violations.addAll(userValidator.validate(dto.user).violations)

        // completedAt if present, must be after startedAt
        dto.completedAt?.apply {
            if (dateTimeConverter.fromString(this) <= dateTimeConverter.fromString(dto.startedAt)) {
                violations.add(TypeValidation.StartedAndCompletedTimestamp(
                    dto.typeName,
                    dto.idToString()
                ))
            }
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}