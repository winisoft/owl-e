package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.steps.InitialDataPointInjector
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.DataPointValidator


@OwleValidator(model = InitialDataPointInjector::class)
class PreReqInjectionValidator(
    private val junctionValidator: WorkflowJunctionValidator,
    private val dataPointValidator: DataPointValidator,
) : BaseValidator<InitialDataPointInjector>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun fieldValidations(dto: InitialDataPointInjector):List<FieldConstraintError> {

        return listOf()
    }}