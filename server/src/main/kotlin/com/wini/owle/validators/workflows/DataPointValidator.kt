package com.wini.owle.validators.workflows


import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator


@OwleValidator(model = DataPoint::class)
class DataPointValidator(
    private val workflowValidator: WorkflowValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<DataPoint>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
        const val MAX_DATAPOINT_LENGTH = 16_000_000
    }

    override fun fieldValidations(dto: DataPoint): List<FieldValidation> {

        return listOf()
    }

}