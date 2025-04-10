package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator
import com.wini.owle.validators.workflows.DataPointValidator

class AppliedTransformValidator(
    private val transformationValidator: TransformationValidator,
    private val resultTypeValidator: TypeDeclarationValidator,
    private val dataPointValidator: DataPointValidator
) : BaseValidator<AppliedTransformDto>() {
    override fun validate(dto: AppliedTransformDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // transformation is required (non-nullable reference)
        if (dto.transformation == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "transformation"))
        }

        // occurrence is required (non-nullable enum)
        if (dto.occurrence == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "occurrence"))
        }

        // previousTransform is optional (nullable reference)

        // resultTypeDeclaration is required (non-nullable reference)
        if (dto.resultType == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "resultTypeDeclaration"))
        }

        // dataPoint is optional (nullable reference)

        // toolResultStep is optional (nullable reference)

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}