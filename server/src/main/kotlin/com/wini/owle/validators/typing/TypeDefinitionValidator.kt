package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeDefinitionDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.environs.VersionSpecValidator

class TypeDefinitionValidator(
    private val versionSpecValidator: VersionSpecValidator
) : BaseValidator<TypeDefinitionDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_NAMESPACE_LENGTH = 512
    }

    override fun validate(dto: TypeDefinitionDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // namespace is required and has max length
        if (dto.namespace.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "namespace"))
        }
        if (dto.namespace.length > MAX_NAMESPACE_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "namespace", MAX_NAMESPACE_LENGTH))
        }

        // versionSpec is optional (nullable reference)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}