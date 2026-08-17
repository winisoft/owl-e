package com.wini.owle.validators.workflows

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = Grouper::class)
class PhaseValidator(
    private val workflowValidator: WorkflowValidator
) : BaseValidator<Grouper>() {

    companion object {
        const val MAX_NAME_LENGTH = 512
        const val MAX_DESC_LENGTH = 16_000
    }

    override fun fieldValidations(dto: Grouper): List<FieldConstraintError> {

        return listOf()
    }
}