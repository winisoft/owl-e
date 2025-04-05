package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.CommercialUseDto
import com.wini.owle.entity.workflows.enums.CommercialUse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class CommercialUseMapperTest {

    private val mapper = CommercialUseMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(CommercialUse.PERSONAL_NON_PROFIT, mapper.toEntity(CommercialUseDto.PersonalNonProfit))
        assertEquals(CommercialUse.PERSONAL_PROFIT, mapper.toEntity(CommercialUseDto.PersonalProfit))
        assertEquals(CommercialUse.CORPORATION, mapper.toEntity(CommercialUseDto.Corporation))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(CommercialUseDto.PersonalNonProfit, mapper.toDto(CommercialUse.PERSONAL_NON_PROFIT))
        assertEquals(CommercialUseDto.PersonalProfit, mapper.toDto(CommercialUse.PERSONAL_PROFIT))
        assertEquals(CommercialUseDto.Corporation, mapper.toDto(CommercialUse.CORPORATION))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            CommercialUseDto.PersonalNonProfit,
            CommercialUseDto.PersonalProfit,
            CommercialUseDto.Corporation,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = CommercialUse.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}