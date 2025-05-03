package com.wini.owle.validators.workflows


import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.validators.DtoValidator


@OwleValidator(model = Workflow::class)
class WorkflowValidator : DtoValidator<Workflow> {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
        const val MIN_PYTHON_VERSION_LENGTH = 3
    }

    override fun fieldValidations(dto: Workflow):List<FieldValidation> {

        return listOf()
    }
}