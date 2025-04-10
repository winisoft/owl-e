package com.wini.owle.validators.user

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.user.TagDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TagValidator : BaseValidator<TagDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TagDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is optional (nullable text)

        // user is required (non-nullable reference)
        if (dto.user == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "user"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}