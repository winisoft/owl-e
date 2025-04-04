package com.wini.owle.entity.user

import com.wini.owle.dto.user.CardBrandDto
import com.wini.owle.entity.PaymentMethods
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


enum class CardBrand {
    AMERICAN_EXPRESS,
    DINERS_CLUB,
    DISCOVER,
    EFTPOS_AUSTRALIA,
    GIROCARD,
    JCB,
    MASTERCARD,
    UNIONPAY,
    VISA,
    UNKNOWN;

    companion object {

        fun fromDto(dto: CardBrandDto): CardBrand {
            return when (dto) {
                CardBrandDto.AmericanExpress -> AMERICAN_EXPRESS
                CardBrandDto.DinersClub -> DINERS_CLUB
                CardBrandDto.Discover -> DISCOVER
                CardBrandDto.EftposAustralia -> EFTPOS_AUSTRALIA
                CardBrandDto.Girocard -> GIROCARD
                CardBrandDto.Jcb -> JCB
                CardBrandDto.Mastercard -> MASTERCARD
                CardBrandDto.UnionPay -> UNIONPAY
                CardBrandDto.Visa -> VISA
                CardBrandDto.Unknown -> UNKNOWN
            }
        }

        fun CardBrand.toDto(): CardBrandDto {
            return when (this) {
                AMERICAN_EXPRESS -> CardBrandDto.AmericanExpress
                DINERS_CLUB -> CardBrandDto.DinersClub
                DISCOVER -> CardBrandDto.Discover
                EFTPOS_AUSTRALIA -> CardBrandDto.EftposAustralia
                GIROCARD -> CardBrandDto.Girocard
                JCB -> CardBrandDto.Jcb
                MASTERCARD -> CardBrandDto.Mastercard
                UNIONPAY -> CardBrandDto.UnionPay
                VISA -> CardBrandDto.Visa
                UNKNOWN -> CardBrandDto.Unknown
            }
        }
    }
}

/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
class PaymentMethodDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PaymentMethodDao>(PaymentMethods)

    /**
     * Unique identifier for the payment method, sourced from Stripe.
     */
    var stripePaymentMethodId by PaymentMethods.stripePaymentMethodId

    /**
     * The user associated with this payment method.
     */
    var user by UserDao referencedOn PaymentMethods.user

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