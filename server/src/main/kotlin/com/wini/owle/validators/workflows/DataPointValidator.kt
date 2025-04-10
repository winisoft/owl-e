package com.wini.owle.validators.workflows

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator

class DataPointValidator(
    private val workflowValidator: WorkflowValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<DataPointDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: DataPointDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested workflow
        violations.addAll(workflowValidator.validate(dto.workflow).violations)

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Validate nested typeDeclaration
        violations.addAll(typeDeclarationValidator.validate(dto.typeDeclaration).violations)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}