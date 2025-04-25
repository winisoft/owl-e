package com.wini.owle.validators.typing

import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


class TypeUnionMemberValidator : BaseValidator<TypeUnionMember>() {

    override fun validate(dto: TypeUnionMember): ValidationResult {

        return ValidationResult.valid()
    }
}