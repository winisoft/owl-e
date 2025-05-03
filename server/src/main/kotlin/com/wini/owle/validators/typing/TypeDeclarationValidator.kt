package com.wini.owle.validators.typing


import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TypeDeclaration::class)
class TypeDeclarationValidator(
    private val typeDefinitionValidator: TypeDefinitionValidator
) : BaseValidator<TypeDeclaration>() {



    override fun fieldValidations(dto: TypeDeclaration):List<FieldValidation> {

        return listOf()
    }}