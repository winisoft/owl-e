package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.workflows.DataPointValidator

class PreReqInjectionValidator(
    private val junctionValidator: WorkflowJunctionValidator,
    private val dataPointValidator: DataPointValidator,
) : BaseValidator<PreReqInjectionDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: PreReqInjectionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested junction
        violations.addAll(junctionValidator.validate(dto.junction).violations)

        // Validate nested sourceDataPoint
        violations.addAll(dataPointValidator.validate(dto.sourceDataPoint).violations)

        // Validate nested injectedDataPoint
        violations.addAll(dataPointValidator.validate(dto.injectedDataPoint).violations)

        // name is optional but has max length if present
        dto.name?.let { name ->
            if (name.length > MAX_NAME_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
            }
        }

        // description is optional (nullable text)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}