package com.wini.owle.validators.user

import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


class TagRelationValidator : BaseValidator<TagRelation>() {

    override fun validate(dto: TagRelation): ValidationResult {

        return ValidationResult.valid()
    }
}