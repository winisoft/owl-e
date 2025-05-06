package com.wini.owle.validators.typing

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeParameter::class)
class TypeParameterValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeParameter>() {

    override fun fieldValidations(dto: TypeParameter):List<FieldConstraintError> {

        return listOf()
    }}