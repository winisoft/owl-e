package com.wini.owle.validators.typing

import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


class TypeUnionMemberValidator : BaseValidator<TypeUnionMemberDto>() {

    override fun validate(dto: TypeUnionMemberDto): ValidationResult {

        return ValidationResult.valid()
    }
}