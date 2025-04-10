package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.entity.workflows.enums.StoreMethod
import com.wini.owle.validators.typing.TypeDeclarationValidator

class DataPointValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<DataPointDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: DataPointDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // workflow is required (non-nullable reference)
        if (dto.workflow == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "workflow"))
        }

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // presetValue is optional (nullable text)

        // typeDeclaration is required (non-nullable reference)
        if (dto.typeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "typeDeclaration"))
        }

        // description is optional (nullable text)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}