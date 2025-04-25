package com.wini.owle.validators.environs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class PackageRepoValidator: BaseValidator<PyPackageRepo>() {
    override fun validate(dto: PyPackageRepo): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // sourceUrl is optional but if present should be a valid URL
        dto.sourceUrl?.let { url ->
            if (!isValidUrl(url)) {
                violations.add(TypeValidation.InvalidUrl(dto.typeName, dto.idToString(), "sourceUrl"))
            }
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            java.net.URL(url).toURI()
            true
        } catch (e: Exception) {
            false
        }
    }
}