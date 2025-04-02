package com.wini.owle.dto.user

import kotlinx.serialization.Serializable

@Serializable
sealed class CardBrandDto(val id: String) {

    @Serializable
    data object AmericanExpress : CardBrandDto("AMERICAN_EXPRESS")

    @Serializable
    data object DinersClub : CardBrandDto("DINERS_CLUB")

    @Serializable
    data object Discover : CardBrandDto("DISCOVER")

    @Serializable
    data object EftposAustralia : CardBrandDto("EFTPOS_AUSTRALIA")

    @Serializable
    data object Girocard : CardBrandDto("GIROCARD")

    @Serializable
    data object Jcb : CardBrandDto("JCB")

    @Serializable
    data object Mastercard : CardBrandDto("MASTERCARD")

    @Serializable
    data object UnionPay : CardBrandDto("UNIONPAY")

    @Serializable
    data object Visa : CardBrandDto("VISA")

    @Serializable
    data object Unknown : CardBrandDto("UNKNOWN")
}