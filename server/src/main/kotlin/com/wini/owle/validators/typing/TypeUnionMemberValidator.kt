package com.wini.owle.validators.typing

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TypeUnionMemberValidator : BaseValidator<TypeUnionMemberDto>() {
    override fun validate(dto: TypeUnionMemberDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // unionTypeDeclaration is required (non-nullable reference)
        if (dto.unionTypeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "unionTypeDeclaration"))
        }

        // memberTypeDeclaration is required (non-nullable reference)
        if (dto.memberTypeDeclaration == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "memberTypeDeclaration"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}