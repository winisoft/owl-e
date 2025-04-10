package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeDeclarationValidator(
    private val typeDefinitionValidator: TypeDefinitionValidator
) : BaseValidator<TypeDeclarationDto>() {
    companion object {
        const val MAX_ALIAS_LENGTH = 255
    }

    override fun validate(dto: TypeDeclarationDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // typeDefinition is required (non-nullable reference)
        if (dto.typeDefinition == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeDefinition"))
        }

        // alias is optional but has max length if present
        if (dto.alias != null && dto.alias!!.length > MAX_ALIAS_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "alias", MAX_ALIAS_LENGTH))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}