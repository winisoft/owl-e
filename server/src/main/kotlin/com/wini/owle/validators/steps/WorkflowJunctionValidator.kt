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

        // sourceStep is required (non-nullable reference)
        if (dto.sourceStep == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "sourceStep"))
        }

        // targetWorkflow is required (non-nullable reference)
        if (dto.targetWorkflow == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "targetWorkflow"))
        }

        // triggerInquisition is optional (nullable reference)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}