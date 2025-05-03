package com.wini.owle.validators.transforms


import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator
import com.wini.owle.validators.workflows.WorkflowFolderValidator.Companion.MAX_DESC_LENGTH
import com.wini.owle.validators.workflows.WorkflowValidator

@OwleValidator(model = Transform::class)
class TransformationValidator(
    private val returnsTypeValidator: TypeDeclarationValidator
) : BaseValidator<Transform>() {

    override fun fieldValidations(dto: Transform):List<FieldValidation> {

        return listOf()
    }}