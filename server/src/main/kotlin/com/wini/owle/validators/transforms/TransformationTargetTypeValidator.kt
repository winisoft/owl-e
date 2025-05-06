package com.wini.owle.validators.transforms

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TransformTargetType::class)
class TransformationTargetTypeValidator : BaseValidator<TransformTargetType>() {

    override fun fieldValidations(dto: TransformTargetType):List<FieldConstraintError> {
        return listOf()
    }}