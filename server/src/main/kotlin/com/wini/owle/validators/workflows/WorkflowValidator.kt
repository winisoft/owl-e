package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class WorkflowValidator(
    private val parentFolderValidator: WorkflowFolderValidator
) : BaseValidator<WorkflowDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MIN_PYTHON_VERSION_LENGTH = 3
    }

    override fun validate(dto: WorkflowDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Validate optional parentFolder if present
        dto.parentFolder?.let { folder ->
            violations.addAll(parentFolderValidator.validate(folder).violations)
        }

        // pythonVersion must be valid format
        if (!isPythonVersionValid(dto.pythonVersion)) {
            violations.add(TypeValidation.InvalidPythonVersion(
                dto.typeName,
                dto.idToString(),
                "pythonVersion",
                "Must be in format X.Y[.Z]"
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }

    private fun isPythonVersionValid(version: String): Boolean {
        return version.matches(Regex("^\\d+(\\.\\d+)+$"))
    }
}