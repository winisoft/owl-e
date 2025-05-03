package com.wini.owle.validators.typing

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeVariance::class)
class TypeVarianceValidator(
    private val typeVariableDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeVariance>() {

    override fun fieldValidations(dto: TypeVariance):List<FieldValidation> {
        return listOf()
    }
}