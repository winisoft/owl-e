package com.wini.owle.dto.user

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenExact
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.MustContainOnlyNumerals
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringLengthExactError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.TimestampFormatError
import kotlinx.serialization.Serializable

/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
@ModelDefinition
@Serializable
data class PaymentMethod(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,
    /**
     * Unique identifier for the payment method, sourced from Stripe.
     */
    val stripePaymentMethodId: String?,

    /**
     * The user associated with this payment method.
     */
    val user: User,

    /**
     * Brand of the card (e.g., Visa, MasterCard) used in this payment method.
     */
    val cardBrand: CardBrand?,

    /**
     * Last four digits of the card associated with this payment method.
     */
    @ConstraintSimple([MustContainOnlyNumerals::class, StringNotBlankError::class])
    @ConstraintStringLenExact(StringLengthExactError::class, 4)
    val lastFour: String?,

    /**
     * Indicates if this payment method is the default method for the user.
     */
    val isDefault: Boolean,

    /**
     * Timestamp for when the payment method was created.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val createdAt: String,

    /**
     * Timestamp for when the payment method was last updated.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val updatedAt: String
) : ULongDto