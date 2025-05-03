package com.wini.owle.validators.steps

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.steps.DataPointSave
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.DataPointValidator


@OwleValidator(model = DataPointSave::class)
class RevisionValidator(
    private val dataPointValidator: DataPointValidator,
    private val stepValidator: StepValidator
) : BaseValidator<DataPointSave>() {

    override fun fieldValidations(dto: DataPointSave):List<FieldValidation> {
        return listOf()
    }}