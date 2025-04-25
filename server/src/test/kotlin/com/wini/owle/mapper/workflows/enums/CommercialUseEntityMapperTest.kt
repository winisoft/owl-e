package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.entity.workflows.enums.CommercialUseEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class CommercialUseEntityMapperTest {

    private val mapper = CommercialUseMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(CommercialUseEntity.PERSONAL_NON_PROFIT, mapper.toEntity(CommercialUse.PersonalNonProfit))
        assertEquals(CommercialUseEntity.PERSONAL_PROFIT, mapper.toEntity(CommercialUse.PersonalProfit))
        assertEquals(CommercialUseEntity.CORPORATION, mapper.toEntity(CommercialUse.Corporation))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(CommercialUse.PersonalNonProfit, mapper.toDto(CommercialUseEntity.PERSONAL_NON_PROFIT))
        assertEquals(CommercialUse.PersonalProfit, mapper.toDto(CommercialUseEntity.PERSONAL_PROFIT))
        assertEquals(CommercialUse.Corporation, mapper.toDto(CommercialUseEntity.CORPORATION))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            CommercialUse.PersonalNonProfit,
            CommercialUse.PersonalProfit,
            CommercialUse.Corporation,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = CommercialUseEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}