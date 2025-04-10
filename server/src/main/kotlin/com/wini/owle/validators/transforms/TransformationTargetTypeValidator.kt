package com.wini.owle.validators.transforms

import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


class TransformationTargetTypeValidator : BaseValidator<TransformationTargetTypeDto>() {

    override fun validate(dto: TransformationTargetTypeDto): ValidationResult {

        return ValidationResult.valid()
    }
}