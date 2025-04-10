package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class WorkflowFolderValidator : BaseValidator<WorkflowFolderDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: WorkflowFolderDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is optional (nullable text)

        // img is optional (nullable blob)

        // parentFolder is optional (nullable reference)

        // timestamps (createdAt, updatedAt) are managed by the database
        // No validation needed

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}