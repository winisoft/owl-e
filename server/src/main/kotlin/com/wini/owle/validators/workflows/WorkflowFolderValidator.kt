package com.wini.owle.validators.workflows


import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = WorkflowFolder::class)
class WorkflowFolderValidator: BaseValidator<WorkflowFolder>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
    }

    override fun fieldValidations(dto: WorkflowFolder): List<FieldConstraintError> {

        return listOf()
    }

}