package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator


@OwleValidator(model = Tool::class)
class ToolValidator(
    private val returnTypeDeclarationValidator: TypeDeclarationValidator
) : BaseValidator<Tool>() {

    companion object {
        const val MAX_NAME_LENGTH = 512
        const val MAX_DESC_LENGTH = 1024
        const val MAX_BODY_LENGTH = 15_000
    }

    override fun fieldValidations(dto: Tool):List<FieldConstraintError> {

        return listOf()
    }
}