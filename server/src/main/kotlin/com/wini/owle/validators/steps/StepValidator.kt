package com.wini.owle.validators.steps

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.workflows.Step
import com.wini.owle.validators.DtoValidator
import com.wini.owle.validators.workflows.PhaseValidator
import com.wini.owle.validators.workflows.WorkflowValidator


@OwleValidator(model = Step::class)
class StepValidator(
    private val workflowValidator: WorkflowValidator,
    private val toolValidator: ToolValidator,
    private val parentPhaseValidator: PhaseValidator
): DtoValidator<Step> {

    override fun fieldValidations(dto: Step):List<FieldConstraintError> {
        return listOf(
//            FieldValidation.LongValueMustNotBeNegative(
//                Step::id,
//                dto
//            ),
//            TextInputValidation(
//                Step::name,
//                dto,
//                MAX_NAME_LENGTH
//            ),
//            TextInputValidation(
//                Step::description,
//                dto,
//                MAX_DESC_LENGTH
//            ),
//            FieldValidation.IntValueMustNotBeNegative(
//                Step::displayOrder,
//                dto
//            )
//        ).plus(
//            workflowValidator.fieldValidations(dto.workflow)
//        ).plus(
//            toolValidator.fieldValidations(dto.tool)
//        ).plus(
//            dto.parentPhase?.let { parentPhaseValidator.fieldValidations(it) } ?: emptyList()
//        ).plus(
//            dto.nextStep?.let { fieldValidations(it) } ?: emptyList()
        )
    }

}
