package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator

class TransformationArgValidator(
    private val transformationValidator: TransformationValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TransformArg>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TransformArg): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested transformation
        violations.addAll(transformationValidator.validate(dto.transformation).violations)

        // Validate nested typeDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.typeDeclaration).violations)

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}