package com.wini.owle.validators.user



import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.workflows.WorkflowFolderValidator.Companion.MAX_DESC_LENGTH
import com.wini.owle.validators.workflows.WorkflowValidator

@OwleValidator(model = PaymentMethod::class)
class PaymentMethodValidator(
    private val userValidator: UserValidator
) : BaseValidator<PaymentMethod>() {

    companion object {
        const val LAST_FOUR_LENGTH = 4
    }

    override fun fieldValidations(dto: PaymentMethod):List<FieldValidation> {

        return listOf()
    }
}