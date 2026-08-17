package com.wini.owle.entity.user

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
class PaymentMethodEntity(id: EntityID<ULong>) : ULongEntity(id) {
    
    companion object : ULongEntityClass<PaymentMethodEntity>(PaymentMethods)

    /**
     * Unique identifier for the payment method, sourced from Stripe.
     */
    var stripePaymentMethodId by PaymentMethods.stripeId

    /**
     * The user associated with this payment method.
     */
    var user by UserEntity referencedOn PaymentMethods.user

    /**
     * Brand of the card (e.g., Visa, MasterCard) used in this payment method.
     */
    var cardBrand by PaymentMethods.cardBrand

    /**
     * Last four digits of the card associated with this payment method.
     */
    var lastFour by PaymentMethods.lastFour

    /**
     * Indicates if this payment method is the default method for the user.
     */
    var isDefault by PaymentMethods.isDefault

    /**
     * Timestamp for when the payment method was created.
     */
    var createdAt by PaymentMethods.createdAt

    /**
     * Timestamp for when the payment method was last updated.
     */
    var updatedAt by PaymentMethods.updatedAt
}