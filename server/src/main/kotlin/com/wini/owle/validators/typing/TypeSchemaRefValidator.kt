package com.wini.owle.validators.typing


import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = TypeSchemaRef::class)
class TypeSchemaRefValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeSchemaRef>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    //TODO: val parentRef: TypeSchemaRef?, val childRefs: List<TypeSchemaRef>
    override fun fieldValidations(dto: TypeSchemaRef): List<FieldConstraintError> {
        return listOf()
    }
}
//            FieldValidation.LongValueMustNotBeNegative(TypeSchemaRef::id, dto),
//            TextInputValidation(
//                TypeSchemaRef::name,
//                dto,
//                WorkflowValidator.Companion.MAX_NAME_LENGTH
//            ),
//            TextInputValidation(
//                TypeSchemaRef::description,
//                dto,
//                MAX_DESC_LENGTH
//            ),
//        ).plus(
//            typeDeclarationValidator.fieldValidations(dto.typeVarNode)
//        )
 //   }}