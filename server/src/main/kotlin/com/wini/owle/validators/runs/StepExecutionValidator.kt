package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.steps.StepValidator


class StepExecutionValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator,
    private val stepValidator: StepValidator,
    private val dateTimeConverter: DateTimeConverter
): BaseValidator<StepExecution>() {
    override fun validate(dto: StepExecution): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested workflowExecution
        violations.addAll(workflowExecutionValidator.validate(dto.workflowExecution).violations)

        // Validate nested step
        violations.addAll(stepValidator.validate(dto.step).violations)

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