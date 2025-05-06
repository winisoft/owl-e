package com.wini.owle.validators.transforms


import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = AppliedTransformArg::class)
class AppliedTransformArgValidator(
    private val appliedTransformValidator: AppliedTransformValidator,
    private val transformationArgValidator: TransformationArgValidator
) : BaseValidator<AppliedTransformArg>() {


    override fun fieldValidations(dto: AppliedTransformArg):List<FieldConstraintError> {

        return listOf()
    }}