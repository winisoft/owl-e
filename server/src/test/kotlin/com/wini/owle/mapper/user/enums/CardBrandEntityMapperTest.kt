package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.entity.user.enums.CardBrandEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class CardBrandEntityMapperTest {

    private val mapper = CardBrandMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(CardBrandEntity.AMERICAN_EXPRESS, mapper.toEntity(CardBrand.AmericanExpress))
        assertEquals(CardBrandEntity.DINERS_CLUB, mapper.toEntity(CardBrand.DinersClub))
        assertEquals(CardBrandEntity.DISCOVER, mapper.toEntity(CardBrand.Discover))
        assertEquals(CardBrandEntity.EFTPOS_AUSTRALIA, mapper.toEntity(CardBrand.EftposAustralia))
        assertEquals(CardBrandEntity.GIROCARD, mapper.toEntity(CardBrand.Girocard))
        assertEquals(CardBrandEntity.JCB, mapper.toEntity(CardBrand.Jcb))
        assertEquals(CardBrandEntity.MASTERCARD, mapper.toEntity(CardBrand.Mastercard))
        assertEquals(CardBrandEntity.UNIONPAY, mapper.toEntity(CardBrand.UnionPay))
        assertEquals(CardBrandEntity.VISA, mapper.toEntity(CardBrand.Visa))
        assertEquals(CardBrandEntity.UNKNOWN, mapper.toEntity(CardBrand.Unknown))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(CardBrand.AmericanExpress, mapper.toDto(CardBrandEntity.AMERICAN_EXPRESS))
        assertEquals(CardBrand.DinersClub, mapper.toDto(CardBrandEntity.DINERS_CLUB))
        assertEquals(CardBrand.Discover, mapper.toDto(CardBrandEntity.DISCOVER))
        assertEquals(CardBrand.EftposAustralia, mapper.toDto(CardBrandEntity.EFTPOS_AUSTRALIA))
        assertEquals(CardBrand.Girocard, mapper.toDto(CardBrandEntity.GIROCARD))
        assertEquals(CardBrand.Jcb, mapper.toDto(CardBrandEntity.JCB))
        assertEquals(CardBrand.Mastercard, mapper.toDto(CardBrandEntity.MASTERCARD))
        assertEquals(CardBrand.UnionPay, mapper.toDto(CardBrandEntity.UNIONPAY))
        assertEquals(CardBrand.Visa, mapper.toDto(CardBrandEntity.VISA))
        assertEquals(CardBrand.Unknown, mapper.toDto(CardBrandEntity.UNKNOWN))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            CardBrand.AmericanExpress,
            CardBrand.DinersClub,
            CardBrand.Discover,
            CardBrand.EftposAustralia,
            CardBrand.Girocard,
            CardBrand.Jcb,
            CardBrand.Mastercard,
            CardBrand.UnionPay,
            CardBrand.Visa,
            CardBrand.Unknown
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = CardBrandEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}