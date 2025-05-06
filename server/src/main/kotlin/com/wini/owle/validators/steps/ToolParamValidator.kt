package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.workflows.ToolParam
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator


@OwleValidator(model = ToolParam::class)
class ToolParamValidator(
    private val toolValidator: ToolValidator,
    private val typeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<ToolParam>() {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun fieldValidations(dto: ToolParam):List<FieldConstraintError> {

        return listOf()
    }
}