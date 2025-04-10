package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class WorkflowFolderValidator(
    private val dateTimeConverter: DateTimeConverter
) : BaseValidator<WorkflowFolderDto>() {
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

        // parentFolder is optional (nullable reference)

        // timestamps (createdAt, updatedAt)
        // TODO


        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}