package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.PhaseDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class PhaseValidator : BaseValidator<PhaseDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 512
    }

    override fun validate(dto: PhaseDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is optional (nullable text)

        // displayOrder must be non-negative
        if (dto.displayOrder < 0) {
            violations.add(TypeValidation.LongRange(dto.idToString(), "displayOrder", 0, Int.MAX_VALUE.toLong()))
        }

        // parentPhase is optional (nullable reference)

        // workflow is required (non-nullable reference)
        if (dto.workflow == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "workflow"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}