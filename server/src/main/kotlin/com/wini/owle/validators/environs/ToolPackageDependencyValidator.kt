package com.wini.owle.validators.environs


import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = ToolPyPackDep::class)
class ToolPackageDependencyValidator : BaseValidator<ToolPyPackDep>() {

    override fun fieldValidations(dto: ToolPyPackDep): List<FieldConstraintError> {
        return listOf(

        )
    }
}


