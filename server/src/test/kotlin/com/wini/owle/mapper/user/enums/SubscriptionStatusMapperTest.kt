package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.SubscriptionStatusDto
import com.wini.owle.entity.user.enums.CardBrand
import com.wini.owle.entity.user.enums.SubscriptionStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class SubscriptionStatusMapperTest {

    private val mapper = SubscriptionStatusMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(SubscriptionStatus.REGISTERED, mapper.toEntity(SubscriptionStatusDto.Registered))
        assertEquals(SubscriptionStatus.ACTIVE, mapper.toEntity(SubscriptionStatusDto.Active))
        assertEquals(SubscriptionStatus.CANCELLED, mapper.toEntity(SubscriptionStatusDto.Cancelled))
        assertEquals(SubscriptionStatus.ON_HOLD, mapper.toEntity(SubscriptionStatusDto.OnHold))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(SubscriptionStatusDto.Registered, mapper.toDto(SubscriptionStatus.REGISTERED))
        assertEquals(SubscriptionStatusDto.Active, mapper.toDto(SubscriptionStatus.ACTIVE))
        assertEquals(SubscriptionStatusDto.Cancelled, mapper.toDto(SubscriptionStatus.CANCELLED))
        assertEquals(SubscriptionStatusDto.OnHold, mapper.toDto(SubscriptionStatus.ON_HOLD))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            SubscriptionStatusDto.Registered,
            SubscriptionStatusDto.Active,
            SubscriptionStatusDto.Cancelled,
            SubscriptionStatusDto.OnHold,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = SubscriptionStatus.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}