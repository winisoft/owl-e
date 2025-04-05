package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.GenerationMethodDto
import com.wini.owle.entity.runs.enums.GenerationMethod
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class GenerationMethodMapperTest {

    private val mapper = GenerationMethodMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(GenerationMethod.MANUAL, mapper.toEntity(GenerationMethodDto.Manual))
        assertEquals(GenerationMethod.AI_GENERATED, mapper.toEntity(GenerationMethodDto.AiGenerated))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(GenerationMethodDto.Manual, mapper.toDto(GenerationMethod.MANUAL))
        assertEquals(GenerationMethodDto.AiGenerated, mapper.toDto(GenerationMethod.AI_GENERATED))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            GenerationMethodDto.Manual,
            GenerationMethodDto.AiGenerated,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = GenerationMethod.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}