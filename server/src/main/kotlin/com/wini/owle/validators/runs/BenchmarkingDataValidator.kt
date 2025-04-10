package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class BenchmarkingDataValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator,
) : BaseValidator<BenchmarkingDataDto>() {
    override fun validate(dto: BenchmarkingDataDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // workflowExecution is required (non-nullable reference)
        if (dto.workflowExecution == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "workflowExecution"))
        }

        // totalExecutionTime must be positive and reasonable
        // Using decimal(10,3) in DB suggests max ~9999999.999
        if (dto.totalExecutionTime < 0 || dto.totalExecutionTime > 9999999.999) {
            violations.add(TypeValidation.DoubleRange(
                dto.idToString(),
                "totalExecutionTime",
                0.0,
                9999999.999
            ))
        }

        // totalSteps must be non-negative
        if (dto.totalSteps < 0) {
            violations.add(TypeValidation.LongRange(
                dto.idToString(),
                "totalSteps",
                0,
                Int.MAX_VALUE.toLong()
            ))
        }

        // aiDeterminedSteps must be non-negative and not exceed totalSteps
        if (dto.aiDeterminedSteps < 0 || dto.aiDeterminedSteps > dto.totalSteps) {
            violations.add(TypeValidation.LongRange(
                dto.idToString(),
                "aiDeterminedSteps",
                0,
                dto.totalSteps.toLong()
            ))
        }

        // predeterminedSteps must be non-negative and not exceed totalSteps
        if (dto.predeterminedSteps < 0 || dto.predeterminedSteps > dto.totalSteps) {
            violations.add(TypeValidation.LongRange(
                dto.idToString(),
                "predeterminedSteps",
                0,
                dto.totalSteps.toLong()
            ))
        }

        // Sum of aiDetermined and predeterminedSteps should equal totalSteps
        if (dto.aiDeterminedSteps + dto.predeterminedSteps != dto.totalSteps) {
            violations.add(TypeValidation.Generic(
                dto.idToString(),
                "steps",
                "Sum of AI-determined and predetermined steps must equal total steps"
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}