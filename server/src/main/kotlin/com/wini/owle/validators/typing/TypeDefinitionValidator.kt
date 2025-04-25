package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.environs.VersionSpecValidator

class TypeDefinitionValidator(
    private val versionSpecValidator: VersionSpecValidator
) : BaseValidator<TypeDefinition>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_NAMESPACE_LENGTH = 512
    }

    override fun validate(dto: TypeDefinition): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // namespace is required and has max length
        if (dto.namespace.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "namespace"))
        }
        if (dto.namespace.length > MAX_NAMESPACE_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "namespace", MAX_NAMESPACE_LENGTH))
        }

        // Validate optional versionSpec if present
        dto.versionSpec?.let { versionSpec ->
            violations.addAll(versionSpecValidator.validate(versionSpec).violations)
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}