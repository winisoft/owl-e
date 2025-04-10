package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator

class TransformationValidator(
    private val returnsTypeValidator: TypeDeclarationValidator
) : BaseValidator<TransformationDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TransformationDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is required (non-nullable text)
        if (dto.description.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "description"))
        }

        // Validate optional returnsType if present
        dto.returnsType?.let { returnsType ->
            violations.addAll(returnsTypeValidator.validate(returnsType).violations)
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}