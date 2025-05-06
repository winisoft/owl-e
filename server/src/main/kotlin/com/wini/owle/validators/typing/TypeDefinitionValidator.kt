package com.wini.owle.validators.typing

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.error.FieldConstraintError
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.environs.VersionSpecValidator


@OwleValidator(model = TypeDefinition::class)
class TypeDefinitionValidator(
    private val versionSpecValidator: VersionSpecValidator
) : BaseValidator<TypeDefinition>() {

    override fun fieldValidations(dto: TypeDefinition):List<FieldConstraintError> {

        return listOf()
    }
}