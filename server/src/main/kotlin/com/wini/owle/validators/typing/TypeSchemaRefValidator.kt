package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeSchemaRefDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeSchemaRefValidator : BaseValidator<TypeSchemaRefDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TypeSchemaRefDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // parentRef is optional (nullable reference)

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // typeDeclaration is required (non-nullable reference)
        if (dto.typeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeDeclaration"))
        }

        // description is optional (nullable text)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}