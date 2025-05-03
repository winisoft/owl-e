package com.wini.owle.validators.transforms


import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = AppliedTransformArg::class)
class AppliedTransformArgValidator(
    private val appliedTransformValidator: AppliedTransformValidator,
    private val transformationArgValidator: TransformationArgValidator
) : BaseValidator<AppliedTransformArg>() {


    override fun fieldValidations(dto: AppliedTransformArg):List<FieldValidation> {

        return listOf()
    }}