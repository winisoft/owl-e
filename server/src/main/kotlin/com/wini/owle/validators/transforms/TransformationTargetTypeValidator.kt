package com.wini.owle.validators.transforms

import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


class TransformationTargetTypeValidator : BaseValidator<TransformTargetType>() {

    override fun validate(dto: TransformTargetType): ValidationResult {

        return ValidationResult.valid()
    }
}