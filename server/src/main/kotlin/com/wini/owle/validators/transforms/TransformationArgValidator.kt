package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator

class TransformationArgValidator(
    private val transformationValidator: TransformationValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TransformationArgDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TransformationArgDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // transformation is required (non-nullable reference)
        if (dto.transformation == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "transformation"))
        }

        // typeDeclaration is required (non-nullable reference)
        if (dto.typeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeDeclaration"))
        }

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is optional (nullable text)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}