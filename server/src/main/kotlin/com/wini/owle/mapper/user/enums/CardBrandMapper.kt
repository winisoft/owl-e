package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.entity.user.enums.CardBrandEntity

class CardBrandMapper {

    fun toEntity(dto: CardBrand): CardBrandEntity {
        return when (dto) {
            CardBrand.AMERICAN_EXPRESS -> CardBrandEntity.AMERICAN_EXPRESS
            CardBrand.DINERS_CLUB -> CardBrandEntity.DINERS_CLUB
            CardBrand.DISCOVER -> CardBrandEntity.DISCOVER
            CardBrand.EFTPOS_AUSTRALIA -> CardBrandEntity.EFTPOS_AUSTRALIA
            CardBrand.GIROCARD -> CardBrandEntity.GIROCARD
            CardBrand.JCB -> CardBrandEntity.JCB
            CardBrand.MASTERCARD -> CardBrandEntity.MASTERCARD
            CardBrand.UNIONPAY -> CardBrandEntity.UNIONPAY
            CardBrand.VISA -> CardBrandEntity.VISA
            CardBrand.UNKNOWN -> CardBrandEntity.UNKNOWN
        }
    }

    fun toDto(entity: CardBrandEntity): CardBrand {
        return when (entity) {
            CardBrandEntity.AMERICAN_EXPRESS -> CardBrand.AMERICAN_EXPRESS
            CardBrandEntity.DINERS_CLUB -> CardBrand.DINERS_CLUB
            CardBrandEntity.DISCOVER -> CardBrand.DISCOVER
            CardBrandEntity.EFTPOS_AUSTRALIA -> CardBrand.EFTPOS_AUSTRALIA
            CardBrandEntity.GIROCARD -> CardBrand.GIROCARD
            CardBrandEntity.JCB -> CardBrand.JCB
            CardBrandEntity.MASTERCARD -> CardBrand.MASTERCARD
            CardBrandEntity.UNIONPAY -> CardBrand.UNIONPAY
            CardBrandEntity.VISA -> CardBrand.VISA
            CardBrandEntity.UNKNOWN -> CardBrand.UNKNOWN
        }
    }
}