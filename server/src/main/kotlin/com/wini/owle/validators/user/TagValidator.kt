package com.wini.owle.validators.user

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = Tag::class)
class TagValidator(
    private val userValidator: UserValidator
) : BaseValidator<Tag>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
    }

    override fun fieldValidations(dto: Tag):List<FieldConstraintError> {

        return listOf()
    }}