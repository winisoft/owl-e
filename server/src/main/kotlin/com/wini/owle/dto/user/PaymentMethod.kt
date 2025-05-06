package com.wini.owle.dto.user

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.NumeralsOnly
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthExact
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.schema.TimestampCoherent
import com.wini.owle.schema.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.enums.CardBrand
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