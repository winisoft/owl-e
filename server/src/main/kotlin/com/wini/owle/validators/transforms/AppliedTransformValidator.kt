package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.AppliedTransform
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.typing.TypeDeclarationValidator
import com.wini.owle.validators.workflows.DataPointValidator

class AppliedTransformValidator(
    private val transformationValidator: TransformationValidator,
    private val resultTypeValidator: TypeDeclarationValidator,
    private val dataPointValidator: DataPointValidator
) : BaseValidator<AppliedTransform>() {
    override fun validate(dto: AppliedTransform): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested transformation
        violations.addAll(transformationValidator.validate(dto.transformation).violations)

        // Validate nested resultType
        violations.addAll(resultTypeValidator.validate(dto.resultType).violations)

        // Validate optional dataPoint if present
        dto.dataPoint?.let { dataPoint ->
            violations.addAll(dataPointValidator.validate(dataPoint).violations)
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}