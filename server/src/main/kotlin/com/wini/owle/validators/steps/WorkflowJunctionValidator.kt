package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.WorkflowJunctionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.workflows.WorkflowValidator

class WorkflowJunctionValidator(
    private val sourceStepValidator: StepValidator,
    private val targetWorkflowValidator: WorkflowValidator,
    private val triggerInquisitionValidator: InquisitionValidator
) : BaseValidator<WorkflowJunctionDto>() {
    override fun validate(dto: WorkflowJunctionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested sourceStep
        violations.addAll(sourceStepValidator.validate(dto.sourceStep).violations)

        // Validate nested targetWorkflow
        violations.addAll(targetWorkflowValidator.validate(dto.targetWorkflow).violations)

        // Validate optional triggerInquisition if present
        dto.triggerInquisition?.let { inquisition ->
            violations.addAll(triggerInquisitionValidator.validate(inquisition).violations)
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}