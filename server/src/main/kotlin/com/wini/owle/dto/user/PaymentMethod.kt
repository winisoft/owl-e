package com.wini.owle.dto.user

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.CardBrandDto
import kotlinx.serialization.Serializable

/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
@Serializable
data class PaymentMethod(
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
    val cardBrand: CardBrandDto?,
    /**
     * Last four digits of the card associated with this payment method.
     */
    val lastFour: String?,
    /**
     * Indicates if this payment method is the default method for the user.
     */
    val isDefault: Boolean,
    /**
     * Timestamp for when the payment method was created.
     */
    val createdAt: String,
    /**
     * Timestamp for when the payment method was last updated.
     */
    val updatedAt: String
) : StandardDto