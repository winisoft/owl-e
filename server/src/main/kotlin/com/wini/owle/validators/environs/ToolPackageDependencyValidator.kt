package com.wini.owle.validators.environs


import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = ToolPyPackDep::class)
class ToolPackageDependencyValidator : BaseValidator<ToolPyPackDep>() {

    override fun fieldValidations(dto: ToolPyPackDep): List<FieldValidation> {
        return listOf(

        )
    }
}


