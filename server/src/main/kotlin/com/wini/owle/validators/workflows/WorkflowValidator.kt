package com.wini.owle.validators.workflows

import com.wini.owle.validators.BaseValidator
import com.wini.owle.dto.workflows.WorkflowDto

/**
 * Validator for Workflow DTOs.
 * Validates workflow data against business rules and constraints.
 */
class WorkflowValidator : BaseValidator<WorkflowDto>() {
    
    companion object {
        // Constants derived from ExposedTables.kt
        const val MAX_NAME_LENGTH = 255
        const val MIN_PYTHON_VERSION_LENGTH = 3
    }
    
    override fun validate(dto: WorkflowDto): Boolean {
        // Validate required fields
        if (!validateStringNotEmpty(dto.name, "name")) {
            return false
        }
        
        // Validate field lengths
        if (!validateMaxLength(dto.name, MAX_NAME_LENGTH, "name")) {
            return false
        }
        
        // Validate python version format if present
        if (dto.pythonVersion != null) {
            if (!isPythonVersionValid(dto.pythonVersion)) {
                return false
            }
        }
        
        // All validations passed
        return true
    }
    
    /**
     * Validates that a python version string is in the correct format.
     * Accepts formats like "3.6", "3.11", etc.
     */
    private fun isPythonVersionValid(version: String): Boolean {
        return version.matches(Regex("^\\d+(\\.\\d+)+$"))
    }
} 