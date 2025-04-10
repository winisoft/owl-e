package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeSchemaRefDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeSchemaRefValidator(
    private val parentRefValidator: TypeSchemaRefValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeSchemaRefDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TypeSchemaRefDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate optional parentRef if present
        dto.parentRef?.let { parentRef ->
            violations.addAll(parentRefValidator.validate(parentRef).violations)
        }

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Validate nested typeDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.typeDeclaration).violations)

        // description is optional (nullable text)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}