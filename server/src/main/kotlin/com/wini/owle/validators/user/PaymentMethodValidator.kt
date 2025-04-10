package com.wini.owle.validators.user

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class PaymentMethodValidator : BaseValidator<PaymentMethodDto>() {
    companion object {
        const val LAST_FOUR_LENGTH = 4
    }

    override fun validate(dto: PaymentMethodDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // stripePaymentMethodId is optional (nullable string)

        // user is required (non-nullable reference)
        if (dto.user == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "user"))
        }

        // cardBrand is optional (nullable enum)

        // lastFour is optional but must be exactly 4 characters if present
        if (dto.lastFour != null && dto.lastFour!!.length != LAST_FOUR_LENGTH) {
            violations.add(TypeValidation.Generic(
                dto.idToString(),
                "lastFour",
                "Must be exactly $LAST_FOUR_LENGTH characters long"
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}