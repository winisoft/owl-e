package com.wini.owle.dto.user

import com.wini.owle.schema.error.NumeralsOnly
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthExact
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.schema.error.TimestampCoherent
import com.wini.owle.schema.error.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.MustContainOnlyNumerals
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringLengthExactError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.error.PositiveValueLong
import kotlinx.serialization.Serializable

/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
@OwleModel
@Serializable
data class PaymentMethod(

    @LongValueMin(0)
    override val id: Long,
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
    @StringMustNotBeBlank
    @StringLengthExact(4)
    @NumeralsOnly
    val lastFour: String?,

    /**
     * Indicates if this payment method is the default method for the user.
     */
    val isDefault: Boolean,

    /**
     * Timestamp for when the payment method was created.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val createdAt: String,

    /**
     * Timestamp for when the payment method was last updated.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val updatedAt: String
) : StandardDto