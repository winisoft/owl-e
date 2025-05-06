package com.wini.owle.validators.transforms


import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator

@OwleValidator(model = Transform::class)
class TransformationValidator(
    private val returnsTypeValidator: TypeDeclarationValidator
) : BaseValidator<Transform>() {

    override fun fieldValidations(dto: Transform):List<FieldConstraintError> {

        return listOf()
    }}