package com.wini.owle.validators.environs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

/**
 * Validator for VersionSpec DTOs.
 * Validates version specification data against business rules and constraints.
 */
class VersionSpecValidator : BaseValidator<VersionSpecDto>() {
    
    companion object {
        // Constants derived from ExposedTables.kt
        const val MAX_VERSION_LENGTH = 127
        private val VERSION_REGEX = Regex("^\\d+(\\.\\d+)*$")
    }
    
    override fun validate(dto: VersionSpecDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()
        
        // At least one version field must be specified
        if (dto.versionMin == null && dto.versionMax == null && dto.versionExact == null) {
            violations.add(TypeValidation.Generic(
                idString = dto.idToString(),
                fieldName = "version",
                message = "At least one version constraint must be specified"
            ))
        }
        
        // Validate versionMin if present
        dto.versionMin?.let { version ->
            if (!validateMaxLength(version, MAX_VERSION_LENGTH, "versionMin")) {
                violations.add(TypeValidation.MaxLength(
                    idString = dto.idToString(),
                    fieldName = "versionMin",
                    max = MAX_VERSION_LENGTH
                ))
            }
            if (!isValidVersionFormat(version)) {
                violations.add(TypeValidation.InvalidVersion(
                    idString = dto.idToString(),
                    fieldName = "versionMin",
                    format = "x.y.z"
                ))
            }
        }
        
        // Validate versionMax if present
        dto.versionMax?.let { version ->
            if (!validateMaxLength(version, MAX_VERSION_LENGTH, "versionMax")) {
                violations.add(TypeValidation.MaxLength(
                    idString = dto.idToString(),
                    fieldName = "versionMax",
                    max = MAX_VERSION_LENGTH
                ))
            }
            if (!isValidVersionFormat(version)) {
                violations.add(TypeValidation.InvalidVersion(
                    idString = dto.idToString(),
                    fieldName = "versionMax",
                    format = "x.y.z"
                ))
            }
        }
        
        // Validate versionExact if present
        dto.versionExact?.let { version ->
            if (!validateMaxLength(version, MAX_VERSION_LENGTH, "versionExact")) {
                violations.add(TypeValidation.MaxLength(
                    idString = dto.idToString(),
                    fieldName = "versionExact",
                    max = MAX_VERSION_LENGTH
                ))
            }
            if (!isValidVersionFormat(version)) {
                violations.add(TypeValidation.InvalidVersion(
                    idString = dto.idToString(),
                    fieldName = "versionExact",
                    format = "x.y.z"
                ))
            }
        }
        
        // If both min and max are specified, ensure min <= max
//        if (dto.versionMin != null && dto.versionMax != null) {
//            if (!isVersionLessOrEqual(dto.versionMin.toInt(), dto.versionMax.toInt())) {
//                violations.add(TypeValidation.Generic(
//                    idString = dto.idToString(),
//                    fieldName = "version",
//                    message = "Minimum version must be less than or equal to maximum version"
//                ))
//            }
//        }
        
        return if (violations.isEmpty()) {
            ValidationResult.valid()
        } else {
            ValidationResult.invalid(violations)
        }
    }
    
    private fun isValidVersionFormat(version: String): Boolean {
        return VERSION_REGEX.matches(version)
    }
    
    private fun isVersionLessOrEqual(v1: String, v2: String): Boolean {
        val v1Parts = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val v2Parts = v2.split(".").map { it.toIntOrNull() ?: 0 }
        
        // Compare each part numerically
        val maxLength = maxOf(v1Parts.size, v2Parts.size)
        for (i in 0 until maxLength) {
            val p1 = v1Parts.getOrNull(i) ?: 0
            val p2 = v2Parts.getOrNull(i) ?: 0
            if (p1 > p2) return false
            if (p1 < p2) return true
        }
        return true
    }
} 