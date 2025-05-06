package com.wini.owle.validators.transforms


import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.transforms.AppliedTransform
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.typing.TypeDeclarationValidator
import com.wini.owle.validators.workflows.DataPointValidator

@OwleValidator(model = AppliedTransform::class)
class AppliedTransformValidator(
    private val transformationValidator: TransformationValidator,
    private val resultTypeValidator: TypeDeclarationValidator,
    private val dataPointValidator: DataPointValidator
) : BaseValidator<AppliedTransform>() {

    override fun fieldValidations(dto: AppliedTransform):List<FieldConstraintError> {
        return listOf()
    }
}