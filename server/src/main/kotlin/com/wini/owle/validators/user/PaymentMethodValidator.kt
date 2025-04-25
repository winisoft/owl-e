package com.wini.owle.validators.user

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class PaymentMethodValidator(
    private val userValidator: UserValidator
) : BaseValidator<PaymentMethod>() {
    companion object {
        const val LAST_FOUR_LENGTH = 4
    }

    override fun validate(dto: PaymentMethod): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // user is required (non-nullable reference)
        violations.addAll(userValidator.validate(dto.user).violations)

        // lastFour is optional but must be exactly 4 characters if present
        if (dto.lastFour != null && dto.lastFour!!.length != LAST_FOUR_LENGTH) {
            violations.add(TypeValidation.StringLengthExact(
                dto.typeName,
                dto.idToString(),
                "lastFour",
                LAST_FOUR_LENGTH
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}