package com.wini.owle.entity.user

import com.wini.owle.dto.user.CardBrandDto

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