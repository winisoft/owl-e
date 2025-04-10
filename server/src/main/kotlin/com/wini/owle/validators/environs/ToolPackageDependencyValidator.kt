package com.wini.owle.validators.environs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class ToolPackageDependencyValidator : BaseValidator<ToolPackageDependencyDto>() {
    override fun validate(dto: ToolPackageDependencyDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // All fields are required as they form the composite primary key
        // and are non-nullable references in ToolPackageDependencies table
        if (dto.toolId <= 0) {
            violations.add(TypeValidation.LongRange(dto.typeName, dto.idToString(), "toolId", 1, Long.MAX_VALUE))
        }

        if (dto.packageId <= 0) {
            violations.add(TypeValidation.LongRange(dto.typeName, dto.idToString(), "packageId", 1, Long.MAX_VALUE))
        }

        if (dto.versionSpecId <= 0) {
            violations.add(TypeValidation.LongRange(dto.typeName, dto.idToString(), "versionSpecId", 1, Long.MAX_VALUE))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}


