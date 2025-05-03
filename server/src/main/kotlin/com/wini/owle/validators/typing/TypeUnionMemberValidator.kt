package com.wini.owle.validators.typing

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


@OwleValidator(model = TypeUnionMember::class)
class TypeUnionMemberValidator : BaseValidator<TypeUnionMember>() {

    override fun fieldValidations(dto: TypeUnionMember):List<FieldValidation> {
        return listOf()
    }}