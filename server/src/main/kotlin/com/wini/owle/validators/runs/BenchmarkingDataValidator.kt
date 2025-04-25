package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class BenchmarkingDataValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator
) : BaseValidator<BenchmarkReport>() {

    override fun validate(dto: BenchmarkReport): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested workflowExecution
        violations.addAll(workflowExecutionValidator.validate(dto.workflowExecution).violations)

        // totalExecutionTime must be positive and reasonable
        // Using decimal(10,3) in DB suggests max ~9999999.999
        if (dto.totalExecutionTime < 0 || dto.totalExecutionTime > 9999999.999) {
            violations.add(TypeValidation.DoubleRange(
                dto.typeName,
                dto.idToString(),
                "totalExecutionTime",
                0.0,
                9999999.999
            ))
        }

        // totalSteps must be non-negative
        if (dto.totalSteps < 0) {
            violations.add(TypeValidation.LongMin(
                dto.typeName,
                dto.idToString(),
                "totalSteps",
                0
            ))
        }

        // aiDeterminedSteps must be non-negative and not exceed totalSteps
        if (dto.aiDeterminedSteps < 0 || dto.aiDeterminedSteps > dto.totalSteps) {
            violations.add(TypeValidation.LongMin(
                dto.typeName,
                dto.idToString(),
                "aiDeterminedSteps",
                0
            ))
        }

        // predeterminedSteps must be non-negative and not exceed totalSteps
        if (dto.predeterminedSteps < 0) {
            violations.add(TypeValidation.LongMin(
                dto.typeName,
                dto.idToString(),
                "predeterminedSteps",
                0
            ))
        }

        if (dto.predeterminedSteps > dto.totalSteps) {
            violations.add(TypeValidation.PartGreaterThanTotal(
                dto.typeName,
                dto.idToString(),
                "predeterminedSteps",
                dto.totalSteps
            ))
        }

        if (dto.aiDeterminedSteps > dto.totalSteps) {
            violations.add(TypeValidation.PartGreaterThanTotal(
                dto.typeName,
                dto.idToString(),
                "aiDeterminedSteps",
                dto.totalSteps
            ))
        }

        // Sum of aiDetermined and predeterminedSteps should equal totalSteps
        if (dto.aiDeterminedSteps + dto.predeterminedSteps != dto.totalSteps) {
            violations.add(TypeValidation.BenchmarkingSteps(
                dto.typeName,
                dto.idToString()
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}