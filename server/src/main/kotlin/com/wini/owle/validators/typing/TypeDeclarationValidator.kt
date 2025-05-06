package com.wini.owle.validators.typing


import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeDeclaration::class)
class TypeDeclarationValidator(
    private val typeDefinitionValidator: TypeDefinitionValidator
) : BaseValidator<TypeDeclaration>() {



    override fun fieldValidations(dto: TypeDeclaration):List<FieldConstraintError> {

        return listOf()
    }}