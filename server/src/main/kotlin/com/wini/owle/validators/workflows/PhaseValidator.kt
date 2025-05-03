package com.wini.owle.validators.workflows

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.workflows.Phase
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = Phase::class)
class PhaseValidator(
    private val workflowValidator: WorkflowValidator
) : BaseValidator<Phase>() {

    companion object {
        const val MAX_NAME_LENGTH = 512
        const val MAX_DESC_LENGTH = 16_000
    }

    override fun fieldValidations(dto: Phase): List<FieldValidation> {

        return listOf()
    }
}