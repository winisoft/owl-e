package com.wini.owle.validators.typing

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeBound
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeBound::class)
class TypeBoundsValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeBound>() {

    override fun fieldValidations(dto: TypeBound):List<FieldConstraintError> {
        return listOf()
    }}