package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.AuthorshipCreditDto
import com.wini.owle.entity.workflows.enums.AuthorshipCreditEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class AuthorshipCreditEntityMapperTest {

    private val mapper = AuthorshipCreditMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(AuthorshipCreditEntity.REQUIRED, mapper.toEntity(AuthorshipCreditDto.Required))
        assertEquals(AuthorshipCreditEntity.UNNECESSARY, mapper.toEntity(AuthorshipCreditDto.Unnecessary))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(AuthorshipCreditDto.Required, mapper.toDto(AuthorshipCreditEntity.REQUIRED))
        assertEquals(AuthorshipCreditDto.Unnecessary, mapper.toDto(AuthorshipCreditEntity.UNNECESSARY))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            AuthorshipCreditDto.Required,
            AuthorshipCreditDto.Unnecessary
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = AuthorshipCreditEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}