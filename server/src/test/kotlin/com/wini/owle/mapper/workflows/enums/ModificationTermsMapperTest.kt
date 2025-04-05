package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.ModificationTermsDto
import com.wini.owle.entity.workflows.enums.ModificationTerms
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class ModificationTermsMapperTest {

    private val mapper = ModificationTermsMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(ModificationTerms.PROHIBITED, mapper.toEntity(ModificationTermsDto.Prohibited))
        assertEquals(ModificationTerms.ALLOW_MODIFICATION, mapper.toEntity(ModificationTermsDto.AllowModification))
        assertEquals(ModificationTerms.ALLOW_MODIFICATION_REDISTRIBUTION, mapper.toEntity(ModificationTermsDto.AllowModificationDistribution))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(ModificationTermsDto.Prohibited, mapper.toDto(ModificationTerms.PROHIBITED))
        assertEquals(ModificationTermsDto.AllowModification, mapper.toDto(ModificationTerms.ALLOW_MODIFICATION))
        assertEquals(ModificationTermsDto.AllowModificationDistribution, mapper.toDto(ModificationTerms.ALLOW_MODIFICATION_REDISTRIBUTION))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            ModificationTermsDto.Prohibited,
            ModificationTermsDto.AllowModification,
            ModificationTermsDto.AllowModificationDistribution
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = ModificationTerms.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}