package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.ModificationTermsDto
import com.wini.owle.entity.workflows.enums.ModificationTermsEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class ModificationTermsEntityMapperTest {

    private val mapper = ModificationTermsMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(ModificationTermsEntity.PROHIBITED, mapper.toEntity(ModificationTermsDto.Prohibited))
        assertEquals(ModificationTermsEntity.ALLOW_MODIFICATION, mapper.toEntity(ModificationTermsDto.AllowModification))
        assertEquals(ModificationTermsEntity.ALLOW_MODIFICATION_REDISTRIBUTION, mapper.toEntity(ModificationTermsDto.AllowModificationDistribution))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(ModificationTermsDto.Prohibited, mapper.toDto(ModificationTermsEntity.PROHIBITED))
        assertEquals(ModificationTermsDto.AllowModification, mapper.toDto(ModificationTermsEntity.ALLOW_MODIFICATION))
        assertEquals(ModificationTermsDto.AllowModificationDistribution, mapper.toDto(ModificationTermsEntity.ALLOW_MODIFICATION_REDISTRIBUTION))
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
        val allEntities = ModificationTermsEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}