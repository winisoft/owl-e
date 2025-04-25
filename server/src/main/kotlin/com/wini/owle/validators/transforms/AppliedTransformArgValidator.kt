package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class AppliedTransformArgValidator(
    private val appliedTransformValidator: AppliedTransformValidator,
    private val transformationArgValidator: TransformationArgValidator
) : BaseValidator<AppliedTransformArg>() {
    override fun validate(dto: AppliedTransformArg): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested appliedTransform
        violations.addAll(appliedTransformValidator.validate(dto.appliedTransform).violations)

        // Validate nested transformationArg
        violations.addAll(transformationArgValidator.validate(dto.transformationArg).violations)

        // value is required (non-nullable text)
        if (dto.value.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "value"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}