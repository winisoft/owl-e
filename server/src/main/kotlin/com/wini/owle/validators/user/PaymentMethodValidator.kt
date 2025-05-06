package com.wini.owle.validators.user



import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = PaymentMethod::class)
class PaymentMethodValidator(
    private val userValidator: UserValidator
) : BaseValidator<PaymentMethod>() {

    companion object {
        const val LAST_FOUR_LENGTH = 4
    }

    override fun fieldValidations(dto: PaymentMethod):List<FieldConstraintError> {

        return listOf()
    }
}