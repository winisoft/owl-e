package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.workflows.PhaseValidator
import com.wini.owle.validators.workflows.WorkflowValidator

class StepValidator(
    private val workflowValidator: WorkflowValidator,
    private val toolValidator: ToolValidator,
    private val parentPhaseValidator: PhaseValidator
) : BaseValidator<StepDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 512
    }

    override fun validate(dto: StepDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Validate optional parentPhase if present
        dto.parentPhase?.let { phase ->
            violations.addAll(parentPhaseValidator.validate(phase).violations)
        }

        // Validate nested workflow
        violations.addAll(workflowValidator.validate(dto.workflow).violations)

        // Validate nested tool
        violations.addAll(toolValidator.validate(dto.tool).violations)

        // displayOrder must be non-negative
        if (dto.displayOrder < 0) {
            violations.add(TypeValidation.LongRange(dto.typeName, dto.idToString(), "displayOrder", 0, Int.MAX_VALUE.toLong()))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}