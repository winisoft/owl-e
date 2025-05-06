package com.wini.owle.validators.transforms

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator


@OwleValidator(model = TransformArg::class)
class TransformationArgValidator(
    private val transformationValidator: TransformationValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TransformArg>() {



    override fun fieldValidations(dto: TransformArg):List<FieldConstraintError> {

        return listOf()
    }}