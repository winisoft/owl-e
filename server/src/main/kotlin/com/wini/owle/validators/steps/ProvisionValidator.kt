package com.wini.owle.validators.steps

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.steps.Provision
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.DataPointValidator


@OwleValidator(model = Provision::class)
class ProvisionValidator(
    private val stepValidator: StepValidator,
    private val toolParamValidator: ToolParamValidator,
    private val dataPointsValidator: DataPointValidator
) : BaseValidator<Provision>() {

    override fun fieldValidations(dto: Provision):List<FieldValidation> {
        return listOf()
    }

}