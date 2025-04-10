package com.wini.owle.validators.user

import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


class TagRelationValidator : BaseValidator<TagRelationDto>() {

    override fun validate(dto: TagRelationDto): ValidationResult {

        return ValidationResult.valid()
    }
}