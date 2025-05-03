package com.wini.owle.validators.user

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult


@OwleValidator(model = TagRelation::class)
class TagRelationValidator : BaseValidator<TagRelation>() {

    override fun fieldValidations(dto: TagRelation): List<FieldValidation> {
        return emptyList()
    }

}