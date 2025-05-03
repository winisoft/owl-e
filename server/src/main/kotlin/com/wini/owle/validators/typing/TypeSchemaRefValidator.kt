package com.wini.owle.validators.typing


import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.WorkflowFolderValidator.Companion.MAX_DESC_LENGTH
import com.wini.owle.validators.workflows.WorkflowValidator

@OwleValidator(model = TypeSchemaRef::class)
class TypeSchemaRefValidator(
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<TypeSchemaRef>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    //TODO: val parentRef: TypeSchemaRef?, val childRefs: List<TypeSchemaRef>
    override fun fieldValidations(dto: TypeSchemaRef): List<FieldValidation> {
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
//            typeDeclarationValidator.fieldValidations(dto.typeDeclaration)
//        )
 //   }}