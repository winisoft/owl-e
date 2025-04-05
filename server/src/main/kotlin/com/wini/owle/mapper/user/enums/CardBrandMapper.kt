package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.CardBrandDto
import com.wini.owle.entity.user.enums.CardBrand

class CardBrandMapper {

    fun toEntity(dto: CardBrandDto): CardBrand {
        return when (dto) {
            CardBrandDto.AmericanExpress -> CardBrand.AMERICAN_EXPRESS
            CardBrandDto.DinersClub -> CardBrand.DINERS_CLUB
            CardBrandDto.Discover -> CardBrand.DISCOVER
            CardBrandDto.EftposAustralia -> CardBrand.EFTPOS_AUSTRALIA
            CardBrandDto.Girocard -> CardBrand.GIROCARD
            CardBrandDto.Jcb -> CardBrand.JCB
            CardBrandDto.Mastercard -> CardBrand.MASTERCARD
            CardBrandDto.UnionPay -> CardBrand.UNIONPAY
            CardBrandDto.Visa -> CardBrand.VISA
            CardBrandDto.Unknown -> CardBrand.UNKNOWN
        }
    }

    fun toDto(entity: CardBrand): CardBrandDto {
        return when (entity) {
            CardBrand.AMERICAN_EXPRESS -> CardBrandDto.AmericanExpress
            CardBrand.DINERS_CLUB -> CardBrandDto.DinersClub
            CardBrand.DISCOVER -> CardBrandDto.Discover
            CardBrand.EFTPOS_AUSTRALIA -> CardBrandDto.EftposAustralia
            CardBrand.GIROCARD -> CardBrandDto.Girocard
            CardBrand.JCB -> CardBrandDto.Jcb
            CardBrand.MASTERCARD -> CardBrandDto.Mastercard
            CardBrand.UNIONPAY -> CardBrandDto.UnionPay
            CardBrand.VISA -> CardBrandDto.Visa
            CardBrand.UNKNOWN -> CardBrandDto.Unknown
        }
    }
}