package com.wini.owle.validators.user

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TagRelation::class)
class TagRelationValidator : BaseValidator<TagRelation>() {

    override fun fieldValidations(dto: TagRelation): List<FieldConstraintError> {
        return emptyList()
    }

}