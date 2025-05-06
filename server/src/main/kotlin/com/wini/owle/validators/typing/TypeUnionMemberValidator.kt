package com.wini.owle.validators.typing

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeUnionMember::class)
class TypeUnionMemberValidator : BaseValidator<TypeUnionMember>() {

    override fun fieldValidations(dto: TypeUnionMember):List<FieldConstraintError> {
        return listOf()
    }}