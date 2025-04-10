package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.validators.BaseValidator
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.validators.ValidationResult

/**
 * Validator for Workflow DTOs.
 * Validates workflow data against business rules and constraints.
 */
class WorkflowValidator(
    private val parentFolderValidator: WorkflowFolderValidator
) : BaseValidator<WorkflowDto>() {
    
    companion object {
        // Constants derived from ExposedTables.kt
        const val MAX_NAME_LENGTH = 255
        const val MIN_PYTHON_VERSION_LENGTH = 3
    }
    
    override fun validate(dto: WorkflowDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate required fields
        if (!validateStringNotEmpty(dto.name, "name")) {
            violations.add(TypeValidation.StringEmpty(dto.idToString(), "name"))
        }
        
        // Validate field lengths
        if (!validateMaxLength(dto.name, MAX_NAME_LENGTH, "name")) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
    
    /**
     * Validates that a python version string is in the correct format.
     * Accepts formats like "3.6", "3.11", etc.
     */
    private fun isPythonVersionValid(version: String): Boolean {
        return version.matches(Regex("^\\d+(\\.\\d+)+$"))
    }
} 