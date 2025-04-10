package com.wini.owle.validators.environs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class PackageValidator : BaseValidator<PackageDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DISAMBIGUATION_LENGTH = 255
    }

    override fun validate(dto: PackageDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Name is required and must not be blank
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }

        // Check name length
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Check disambiguation length if present
        dto.disambiguation?.let { disambiguation ->
            if (disambiguation.length > MAX_DISAMBIGUATION_LENGTH) {
                violations.add(TypeValidation.MaxLength(dto.idToString(), "disambiguation", MAX_DISAMBIGUATION_LENGTH))
            }
        }

        // versionSpec is required (based on non-nullable reference in Packages table)
        if (dto.versionSpec == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "versionSpec"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}