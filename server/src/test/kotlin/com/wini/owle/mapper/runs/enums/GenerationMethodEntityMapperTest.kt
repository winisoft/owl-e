package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.entity.runs.enums.GenerationMethodEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class GenerationMethodEntityMapperTest {

    private val mapper = GenerationMethodMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(GenerationMethodEntity.MANUAL, mapper.toEntity(GenerationMethod.Manual))
        assertEquals(GenerationMethodEntity.AI_GENERATED, mapper.toEntity(GenerationMethod.AiGenerated))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(GenerationMethod.Manual, mapper.toDto(GenerationMethodEntity.MANUAL))
        assertEquals(GenerationMethod.AiGenerated, mapper.toDto(GenerationMethodEntity.AI_GENERATED))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            GenerationMethod.Manual,
            GenerationMethod.AiGenerated,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = GenerationMethodEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}