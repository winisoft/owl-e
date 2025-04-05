package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.CardBrandDto
import com.wini.owle.entity.user.enums.CardBrand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class CardBrandMapperTest {

    private val mapper = CardBrandMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(CardBrand.AMERICAN_EXPRESS, mapper.toEntity(CardBrandDto.AmericanExpress))
        assertEquals(CardBrand.DINERS_CLUB, mapper.toEntity(CardBrandDto.DinersClub))
        assertEquals(CardBrand.DISCOVER, mapper.toEntity(CardBrandDto.Discover))
        assertEquals(CardBrand.EFTPOS_AUSTRALIA, mapper.toEntity(CardBrandDto.EftposAustralia))
        assertEquals(CardBrand.GIROCARD, mapper.toEntity(CardBrandDto.Girocard))
        assertEquals(CardBrand.JCB, mapper.toEntity(CardBrandDto.Jcb))
        assertEquals(CardBrand.MASTERCARD, mapper.toEntity(CardBrandDto.Mastercard))
        assertEquals(CardBrand.UNIONPAY, mapper.toEntity(CardBrandDto.UnionPay))
        assertEquals(CardBrand.VISA, mapper.toEntity(CardBrandDto.Visa))
        assertEquals(CardBrand.UNKNOWN, mapper.toEntity(CardBrandDto.Unknown))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(CardBrandDto.AmericanExpress, mapper.toDto(CardBrand.AMERICAN_EXPRESS))
        assertEquals(CardBrandDto.DinersClub, mapper.toDto(CardBrand.DINERS_CLUB))
        assertEquals(CardBrandDto.Discover, mapper.toDto(CardBrand.DISCOVER))
        assertEquals(CardBrandDto.EftposAustralia, mapper.toDto(CardBrand.EFTPOS_AUSTRALIA))
        assertEquals(CardBrandDto.Girocard, mapper.toDto(CardBrand.GIROCARD))
        assertEquals(CardBrandDto.Jcb, mapper.toDto(CardBrand.JCB))
        assertEquals(CardBrandDto.Mastercard, mapper.toDto(CardBrand.MASTERCARD))
        assertEquals(CardBrandDto.UnionPay, mapper.toDto(CardBrand.UNIONPAY))
        assertEquals(CardBrandDto.Visa, mapper.toDto(CardBrand.VISA))
        assertEquals(CardBrandDto.Unknown, mapper.toDto(CardBrand.UNKNOWN))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            CardBrandDto.AmericanExpress,
            CardBrandDto.DinersClub,
            CardBrandDto.Discover,
            CardBrandDto.EftposAustralia,
            CardBrandDto.Girocard,
            CardBrandDto.Jcb,
            CardBrandDto.Mastercard,
            CardBrandDto.UnionPay,
            CardBrandDto.Visa,
            CardBrandDto.Unknown
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = CardBrand.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}