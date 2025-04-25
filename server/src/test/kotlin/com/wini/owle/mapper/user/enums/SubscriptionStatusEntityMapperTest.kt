package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.enums.SubscriptionStatus
import com.wini.owle.entity.user.enums.SubscriptionStatusEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class SubscriptionStatusEntityMapperTest {

    private val mapper = SubscriptionStatusMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(SubscriptionStatusEntity.REGISTERED, mapper.toEntity(SubscriptionStatus.Registered))
        assertEquals(SubscriptionStatusEntity.ACTIVE, mapper.toEntity(SubscriptionStatus.Active))
        assertEquals(SubscriptionStatusEntity.CANCELLED, mapper.toEntity(SubscriptionStatus.Cancelled))
        assertEquals(SubscriptionStatusEntity.ON_HOLD, mapper.toEntity(SubscriptionStatus.OnHold))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(SubscriptionStatus.Registered, mapper.toDto(SubscriptionStatusEntity.REGISTERED))
        assertEquals(SubscriptionStatus.Active, mapper.toDto(SubscriptionStatusEntity.ACTIVE))
        assertEquals(SubscriptionStatus.Cancelled, mapper.toDto(SubscriptionStatusEntity.CANCELLED))
        assertEquals(SubscriptionStatus.OnHold, mapper.toDto(SubscriptionStatusEntity.ON_HOLD))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            SubscriptionStatus.Registered,
            SubscriptionStatus.Active,
            SubscriptionStatus.Cancelled,
            SubscriptionStatus.OnHold,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = SubscriptionStatusEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}