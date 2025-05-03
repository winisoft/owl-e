package com.wini.owle.validators.steps

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.steps.InitialDataPointInjector
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.DataPointValidator
import com.wini.owle.validators.workflows.WorkflowFolderValidator.Companion.MAX_DESC_LENGTH
import com.wini.owle.validators.workflows.WorkflowValidator


@OwleValidator(model = InitialDataPointInjector::class)
class PreReqInjectionValidator(
    private val junctionValidator: WorkflowJunctionValidator,
    private val dataPointValidator: DataPointValidator,
) : BaseValidator<InitialDataPointInjector>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun fieldValidations(dto: InitialDataPointInjector):List<FieldValidation> {

        return listOf()
    }}