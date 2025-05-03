package com.wini.owle.validators.transforms

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


@OwleValidator(model = TransformTargetType::class)
class TransformationTargetTypeValidator : BaseValidator<TransformTargetType>() {

    override fun fieldValidations(dto: TransformTargetType):List<FieldValidation> {
        return listOf()
    }}