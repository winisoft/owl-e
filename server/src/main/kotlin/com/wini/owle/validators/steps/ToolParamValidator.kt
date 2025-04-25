package com.wini.owle.validators.steps

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.ToolParam
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator


class ToolParamValidator(
    private val toolValidator: ToolValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<ToolParam>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: ToolParam): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Validate nested tool
        violations.addAll(toolValidator.validate(dto.tool).violations)

        // Validate nested typeDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.typeDeclaration).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}