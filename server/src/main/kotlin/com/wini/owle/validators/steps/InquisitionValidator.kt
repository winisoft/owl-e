package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.DataPointValidator


@OwleValidator(model = Inquisition::class)
class InquisitionValidator(
    private val stepValidator: StepValidator,
    private val dataPointValidator: DataPointValidator
) : BaseValidator<Inquisition>() {


    override fun fieldValidations(dto: Inquisition): List<FieldConstraintError> {
        return listOf()
    }
}



