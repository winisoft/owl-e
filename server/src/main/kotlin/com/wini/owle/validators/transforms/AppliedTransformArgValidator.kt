package com.wini.owle.validators.transforms

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class AppliedTransformArgValidator : BaseValidator<AppliedTransformArgDto>() {
    override fun validate(dto: AppliedTransformArgDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // appliedTransform is required (non-nullable reference)
        if (dto.appliedTransform == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "appliedTransform"))
        }

        // transformationArg is required (non-nullable reference)
        if (dto.transformationArg == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "transformationArg"))
        }

        // value is required (non-nullable text)
        if (dto.value.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "value"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}