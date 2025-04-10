package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator


class ToolParamValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<ToolParamDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: ToolParamDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // tool is required (non-nullable reference)
        if (dto.tool == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "tool"))
        }

        // typeDeclaration is required (non-nullable reference)
        if (dto.typeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeDeclaration"))
        }

        // hasDefault is required (non-nullable boolean)
        // No validation needed as it's a primitive boolean

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}