package com.wini.owle.validators.transforms

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator
import com.wini.owle.validators.workflows.WorkflowFolderValidator.Companion.MAX_DESC_LENGTH
import com.wini.owle.validators.workflows.WorkflowValidator


@OwleValidator(model = TransformArg::class)
class TransformationArgValidator(
    private val transformationValidator: TransformationValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TransformArg>() {



    override fun fieldValidations(dto: TransformArg):List<FieldValidation> {

        return listOf()
    }}