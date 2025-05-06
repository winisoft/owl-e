package com.wini.owle.validators.typing

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeVariance::class)
class TypeVarianceValidator(
    private val typeVariableDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeVariance>() {

    override fun fieldValidations(dto: TypeVariance):List<FieldConstraintError> {
        return listOf()
    }
}