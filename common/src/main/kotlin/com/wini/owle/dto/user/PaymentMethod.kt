package com.wini.owle.dto.user

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenExact
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.MustContainOnlyNumerals
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import com.wini.owle.schema.Schema
import com.wini.owle.schema.StringLengthExact
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
@ModelDefinition
@ApiEndpointDefinition("api.user.paymentMethod", ["user"], "/payment-method")
@Serializable
data class PaymentMethod(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,
    /**
     * Unique identifier for the payment method, sourced from Stripe.
     */
    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.PaymentMethod.STRIPE_ID_MAX_LENGTH)
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
    @ConstraintStringLenExact(StringLengthExact::class, 4)
    val lastFour: String?,

    /**
     * Indicates if this payment method is the default method for the user.
     */
    val isDefault: Boolean,

    /**
     * Timestamp for when the payment method was created.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val createdAt: String,

    /**
     * Timestamp for when the payment method was last updated.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val updatedAt: String
) : ULongDto